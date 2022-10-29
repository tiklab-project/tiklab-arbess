package net.tiklab.matflow.orther.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.orther.model.PipelineAuthorize;
import net.tiklab.matflow.orther.dao.PipelineAuthorizeDao;
import net.tiklab.matflow.orther.entity.PipelineAuthorizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PipelineAuthorizeServiceImpl implements PipelineAuthorizeService {


    @Autowired
    PipelineAuthorizeDao authorizeDao;

    /**
     * 创建流水线授权
     * @param pipelineAuthorize 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthorize(PipelineAuthorize pipelineAuthorize) {
        PipelineAuthorizeEntity pipelineAuthorizeEntity = BeanMapper.map(pipelineAuthorize, PipelineAuthorizeEntity.class);
        return authorizeDao.createAuthorize(pipelineAuthorizeEntity);
    }

    /**
     * 删除流水线授权
     * @param authorizeId 流水线授权id
     */
    @Override
    public void deleteAuthorize(String authorizeId) {
        authorizeDao.deleteAuthorize(authorizeId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthorize 信息
     */
    @Override
    public void updateAuthorize(PipelineAuthorize pipelineAuthorize) {
        PipelineAuthorizeEntity authorizeEntity = BeanMapper.map(pipelineAuthorize, PipelineAuthorizeEntity.class);
        authorizeDao.updateAuthorize(authorizeEntity);
    }

    /**
     * 查询授权信息
     * @param authorizeId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthorize findOneAuthorize(String authorizeId) {
        PipelineAuthorizeEntity oneAuthorize = authorizeDao.findOneAuthorize(authorizeId);
        return BeanMapper.map(oneAuthorize, PipelineAuthorize.class);
    }

    /**
     * 根据类型查询授权信息
     * @param type 类型
     * @return 授权
     */
    @Override
    public PipelineAuthorize findOneAuthorize(int type){
        List<PipelineAuthorize> allAuthorize = findAllAuthorize();
        if (allAuthorize == null){
            return null;
        }
        for (PipelineAuthorize pipelineAuthorize : allAuthorize) {
            if (pipelineAuthorize.getType().equals(type)){
                return pipelineAuthorize;
            }
        }
        return null;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthorize> findAllAuthorize() {
        List<PipelineAuthorizeEntity> allAuthorize = authorizeDao.findAllAuthorize();
        return BeanMapper.mapList(allAuthorize, PipelineAuthorize.class);
    }

    @Override
    public List<PipelineAuthorize> findAllAuthorizeList(List<String> idList) {
        List<PipelineAuthorizeEntity> allAuthorizeList = authorizeDao.findAllAuthorizeList(idList);
        return BeanMapper.mapList(allAuthorizeList, PipelineAuthorize.class);
    }


}







































