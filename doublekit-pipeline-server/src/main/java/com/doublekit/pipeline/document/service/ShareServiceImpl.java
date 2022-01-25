package com.doublekit.pipeline.document.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.page.PaginationBuilder;
import com.doublekit.join.JoinTemplate;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.pipeline.document.dao.ShareDao;
import com.doublekit.pipeline.document.entity.ShareEntity;
import com.doublekit.pipeline.document.model.Share;
import com.doublekit.pipeline.document.model.ShareQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
* ShareServiceImpl
*/
@Service
@Exporter
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareDao shareDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createShare(@NotNull @Valid Share share) {
        ShareEntity shareEntity = BeanMapper.map(share, ShareEntity.class);

        return shareDao.createShare(shareEntity);
    }

    @Override
    public void updateShare(@NotNull @Valid Share share) {
        ShareEntity shareEntity = BeanMapper.map(share, ShareEntity.class);

        shareDao.updateShare(shareEntity);
    }

    @Override
    public void deleteShare(@NotNull String id) {
        shareDao.deleteShare(id);
    }

    @Override
    public Share findOne(String id) {
        ShareEntity shareEntity = shareDao.findShare(id);

        Share share = BeanMapper.map(shareEntity, Share.class);
        return share;
    }

    @Override
    public List<Share> findList(List<String> idList) {
        List<ShareEntity> shareEntityList = shareDao.findShareList(idList);

        List<Share> shareList = BeanMapper.mapList(shareEntityList, Share.class);
        return shareList;
    }

    @Override
    public Share findShare(@NotNull String id) {
        Share share = findOne(id);

        joinTemplate.joinQuery(share);
        return share;
    }

    @Override
    public List<Share> findAllShare() {
        List<ShareEntity> shareEntityList = shareDao.findAllShare();

        List<Share> shareList = BeanMapper.mapList(shareEntityList, Share.class);

        joinTemplate.joinQuery(shareList);
        return shareList;
    }

    @Override
    public List<Share> findShareList(ShareQuery shareQuery) {
        List<ShareEntity> shareEntityList = shareDao.findShareList(shareQuery);

        List<Share> shareList = BeanMapper.mapList(shareEntityList, Share.class);

        joinTemplate.joinQuery(shareList);

        return shareList;
    }

    @Override
    public Pagination<Share> findSharePage(ShareQuery shareQuery) {

        Pagination<ShareEntity> pagination = shareDao.findSharePage(shareQuery);

        List<Share> shareList = BeanMapper.mapList(pagination.getDataList(), Share.class);

        joinTemplate.joinQuery(shareList);

        return PaginationBuilder.build(pagination,shareList);
    }

    @Override
    public Share addShare(Share share) {
        if (share.getWhetherAuthCode()) {
            //随机生成验证码
            int authCode = ThreadLocalRandom.current().nextInt(1000,10000);
            share.setAuthCode(String.valueOf(authCode));
        }
        //根据时间搓和文档id生成分享id
        long l = System.currentTimeMillis();
        String time = String.valueOf(l);
        String shareLink = time + "?xt:" + share.getDocumentId();
        share.setShareLink(shareLink);
        share.setCreateTime(new Date());
        ShareEntity shareEntity = BeanMapper.map(share, ShareEntity.class);

        shareDao.createShare(shareEntity);
        return share;
    }

    @Override
    public Share cutHaveOrNotAuthCode(ShareQuery shareQuery) {
        List<ShareEntity> shareList = shareDao.findShareList(new ShareQuery().setShareLink(shareQuery.getShareLink()));
        //切换验证码到有
        if (shareQuery.getWhetherAuthCode()) {
            //验证码
            String authCode = shareList.get(0).getAuthCode();
            if (StringUtils.isEmpty(authCode)) {
                //随机生成验证码
                int newAuthCode = ThreadLocalRandom.current().nextInt(1000,10000);
                shareList.get(0).setAuthCode(String.valueOf(newAuthCode));
                shareDao.updateShare(shareList.get(0));
                Share share = BeanMapper.map(shareList.get(0), Share.class);
                return share;
            } else {
                return null;
            }
        } else {
            String authCode = shareList.get(0).getAuthCode();
            if (!StringUtils.isEmpty(authCode)) {
                shareList.get(0).setAuthCode("");
                shareDao.updateShare(shareList.get(0));
                Share share = BeanMapper.map(shareList.get(0), Share.class);
                return share;
            }else {
                return null;
            }
        }
    }

    @Override
    public String verifyAuthCode(ShareQuery shareQuery) {
        List<ShareEntity> shareList = shareDao.findShareList(new ShareQuery().setShareLink(shareQuery.getShareLink()));
        if (CollectionUtils.isNotEmpty(shareList)){
            ShareEntity shareEntity = shareList.get(0);
            if (shareQuery.getAuthCode().equals(shareEntity.getAuthCode())){
                return "true";
            }else {
                return "请输入正确的验证码";
            }
        }else {
            return "你来晚了,该链接内容已被删除";
        }
    }

    @Override
    public String judgeAuthCode(String shareLink) {
        List<ShareEntity> shareList = shareDao.findShareList(new ShareQuery().setShareLink(shareLink));
        if (CollectionUtils.isNotEmpty(shareList)){
            ShareEntity shareEntity = shareList.get(0);
            if (StringUtils.isEmpty(shareEntity.getAuthCode())){
                return "false";
            }else {
                return "true";
            }
        }else {
            return "你来晚了,该链接内容已被删除";
        }
    }
}