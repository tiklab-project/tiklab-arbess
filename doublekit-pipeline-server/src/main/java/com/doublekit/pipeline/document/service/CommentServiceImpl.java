package com.doublekit.pipeline.document.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.page.PaginationBuilder;
import com.doublekit.eam.common.Ticket;
import com.doublekit.eam.common.TicketContext;
import com.doublekit.eam.common.TicketHolder;
import com.doublekit.join.JoinTemplate;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.User;
import com.doublekit.pipeline.document.dao.CommentDao;
import com.doublekit.pipeline.document.dao.LikeDao;
import com.doublekit.pipeline.document.entity.CommentEntity;
import com.doublekit.pipeline.document.entity.LikeEntity;
import com.doublekit.pipeline.document.model.Comment;
import com.doublekit.pipeline.document.model.CommentQuery;
import com.doublekit.pipeline.document.model.Like;
import com.doublekit.pipeline.document.model.LikeQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* CommentServiceImpl
*/
@Service
@Exporter
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LikeDao likeDao;

    @Override
    public String createComment(@NotNull @Valid Comment comment) {
        CommentEntity commentEntity = BeanMapper.map(comment, CommentEntity.class);
        //添加评论人
        commentEntity.setUser(findCreatUser());
        commentEntity.setCreateTime(new Date());
        if (!ObjectUtils.isEmpty(commentEntity.getParentCommentId())){
            CommentEntity parentComment = commentDao.findComment(commentEntity.getParentCommentId());
            //被回复的人
            commentEntity.setAimAtUser(parentComment.getUser());
        }
        return commentDao.createComment(commentEntity);
    }

    @Override
    public void updateComment(@NotNull @Valid Comment comment) {
        CommentEntity commentEntity = BeanMapper.map(comment, CommentEntity.class);
        commentEntity.setUpdateTime(new Date());
        commentDao.updateComment(commentEntity);
    }

    @Override
    public void deleteComment(@NotNull String id) {
        commentDao.deleteComment(id);
    }

    @Override
    public Comment findOne(String id) {
        CommentEntity commentEntity = commentDao.findComment(id);

        Comment comment = BeanMapper.map(commentEntity, Comment.class);
        return comment;
    }

    @Override
    public List<Comment> findList(List<String> idList) {
        List<CommentEntity> commentEntityList =  commentDao.findCommentList(idList);

        List<Comment> commentList =  BeanMapper.mapList(commentEntityList,Comment.class);
        return commentList;
    }

    @Override
    public Comment findComment(@NotNull String id) {
        Comment comment = findOne(id);

        joinTemplate.joinQuery(comment);
        return comment;
    }

    @Override
    public List<Comment> findAllComment() {
        List<CommentEntity> commentEntityList =  commentDao.findAllComment();

        List<Comment> commentList =  BeanMapper.mapList(commentEntityList,Comment.class);

        joinTemplate.joinQuery(commentList);
        return commentList;
    }

    @Override
    public List<Comment> findCommentList(CommentQuery commentQuery,String type) {
        List<CommentEntity> commentEntityList = commentDao.findCommentList(commentQuery);


        List<Comment> commentList = BeanMapper.mapList(commentEntityList,Comment.class);

        joinTemplate.joinQuery(commentList);

        findLike(commentList,type);
        List<Comment> fistOneComment = findComment(commentList);

        return fistOneComment;
    }

    @Override
    public Pagination<Comment> findCommentPage(CommentQuery commentQuery) {

        Pagination<CommentEntity>  pagination = commentDao.findCommentPage(commentQuery);

        List<Comment> commentList = BeanMapper.mapList(pagination.getDataList(),Comment.class);

        joinTemplate.joinQuery(commentList);

        List<Comment> fistOneComment = findComment(commentList);

        return PaginationBuilder.build(pagination,fistOneComment);
    }


    /*
     * 查询用户（创建人）id
     * @param*/

    public String findCreatUser(){
        String ticketId = TicketHolder.get();
        Ticket ticket = TicketContext.get(ticketId);
        return ticket.getUserId();
    }

    /**
     * 第一级以及下面的评论
     * @param
     */
    public List<Comment> findComment(List<Comment> commentList){
        List<Comment> collect = commentList.stream().filter(
                a -> ObjectUtils.isEmpty(a.getParentCommentId())).collect(Collectors.toList());
        for (Comment comment:collect){
            //第一层评论下面的所有评论
            List<Comment> collect1 = commentList.stream().filter(b -> comment.getId().equals(b.getFirstOneCommentId())).collect(Collectors.toList());
            comment.setCommentList(collect1);
            }
        return collect;
        }


    /**
     *查询点赞
     * @param
     */
    public void findLike(List<Comment> commentList,String type){
        for (Comment comment:commentList){
            LikeQuery likeQuery = new LikeQuery();
            likeQuery.setToWhomId(comment.getId());
            likeQuery.setLikeType("com");
            //查询点赞数
            List<LikeEntity> likeList = likeDao.findLikeList(likeQuery);
            if (CollectionUtils.isNotEmpty(likeList)){
                if ("view".equals(type)){
                    comment.setIsLike("false");
                }else {
                    //根据用户id判断该用户是否点赞了
                    List<LikeEntity> collect1 = likeList.stream().filter(a -> findCreatUser().equals(a.getLikeUser())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect1)){
                        comment.setIsLike("true");
                    }else {
                        comment.setIsLike("false");
                    }
                }
                List<Like> likes = BeanMapper.mapList(likeList, Like.class);
                joinTemplate.joinQuery(likes);
                List<User> userList = likes.stream().map(Like::getLikeUser).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(userList)){
                    //取点赞人名字
                    List<String> collect = userList.stream().map(User::getName).collect(Collectors.toList());
                    //点赞数
                    comment.setLikenumInt(likeList.size());
                    //点赞人
                    comment.setLikeUserList(collect);
                }
            }else {
                comment.setIsLike("false");
            }
        }
    }

}