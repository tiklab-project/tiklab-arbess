package com.doublekit.pipeline.document.service;

import com.doublekit.common.page.Pagination;

import com.doublekit.pipeline.document.model.Share;
import com.doublekit.pipeline.document.model.ShareQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ShareService
*/
public interface ShareService {

    /**
    * 创建
    * @param share
    * @return
    */
    String createShare(@NotNull @Valid Share share);

    /**
    * 更新
    * @param share
    */
    void updateShare(@NotNull @Valid Share share);

    /**
    * 删除
    * @param id
    */
    void deleteShare(@NotNull String id);

    Share findOne(@NotNull String id);

    List<Share> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    Share findShare(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<Share> findAllShare();

    /**
    * 查询列表
    * @param shareQuery
    * @return
    */
    List<Share> findShareList(ShareQuery shareQuery);

    /**
    * 按分页查询
    * @param shareQuery
    * @return
    */
    Pagination<Share> findSharePage(ShareQuery shareQuery);

    /**
     * 创建分享并返回链接和验证码
     * @param share
     * @return
     */
    Share addShare(Share share);

    /**
     * 切换是否需要验证码
     * @param shareQuery
     * @return
     */
    Share cutHaveOrNotAuthCode(ShareQuery shareQuery);

    /**
     * 验证验证码是否正确
     * @param shareQuery
     * @return
     */
    String verifyAuthCode(ShareQuery shareQuery);

    /**
     * 判断是否需要验证码
     * @param shareLink
     * @return
     */
    String judgeAuthCode(String shareLink);
}