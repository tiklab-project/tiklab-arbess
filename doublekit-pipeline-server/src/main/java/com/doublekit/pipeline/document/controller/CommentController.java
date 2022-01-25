package com.doublekit.pipeline.document.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.document.model.Comment;
import com.doublekit.pipeline.document.model.CommentQuery;
import com.doublekit.pipeline.document.service.CommentService;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.Result;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CommentController
 */
@RestController
@RequestMapping("/comment")
@Api(name = "CommentController",desc = "文档评论管理")
public class CommentController {

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @RequestMapping(path="/createComment",method = RequestMethod.POST)
    @ApiMethod(name = "createComment",desc = "创建评论")
    @ApiParam(name = "comment",desc = "comment",required = true)
    public Result<String> createComment(@RequestBody @NotNull @Valid Comment comment){
        String id = commentService.createComment(comment);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateComment",method = RequestMethod.POST)
    @ApiMethod(name = "updateComment",desc = "更新评论")
    @ApiParam(name = "comment",desc = "comment",required = true)
    public Result<Void> updateComment(@RequestBody @NotNull @Valid Comment comment){
        commentService.updateComment(comment);

        return Result.ok();
    }

    @RequestMapping(path="/deleteComment",method = RequestMethod.POST)
    @ApiMethod(name = "deleteComment",desc = "删除评论")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteComment(@NotNull String id){
        commentService.deleteComment(id);

        return Result.ok();
    }

    @RequestMapping(path="/findComment",method = RequestMethod.POST)
    @ApiMethod(name = "findComment",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Comment> findComment(@NotNull String id){
        Comment comment = commentService.findComment(id);

        return Result.ok(comment);
    }

    @RequestMapping(path="/findAllComment",method = RequestMethod.POST)
    @ApiMethod(name = "findAllComment",desc = "查询所有评论")
    public Result<List<Comment>> findAllComment(){
        List<Comment> commentList = commentService.findAllComment();

        return Result.ok(commentList);
    }

    @RequestMapping(path = "/findCommentList",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentList",desc = "根据查询对象查询评论")
    @ApiParam(name = "commentQuery",desc = "commentQuery",required = true)
    public Result<List<Comment>> findCommentList(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        List<Comment> commentList = commentService.findCommentList(commentQuery,null);

        return Result.ok(commentList);
    }

    @RequestMapping(path = "/findCommentPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCommentPage",desc = "根据查询对象分页查询评论")
    @ApiParam(name = "commentQuery",desc = "commentQuery",required = true)
    public Result<Pagination<Comment>> findCommentPage(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        Pagination<Comment> pagination = commentService.findCommentPage(commentQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/view",method = RequestMethod.POST)
    @ApiMethod(name = "view",desc = "根据查询对象查询评论（转发后查看）")
    @ApiParam(name = "commentQuery",desc = "commentQuery",required = true)
    public Result<List<Comment>> view(@RequestBody @Valid @NotNull CommentQuery commentQuery){
        String type="view";
        List<Comment> commentList = commentService.findCommentList(commentQuery,type);

        return Result.ok(commentList);
    }

}
