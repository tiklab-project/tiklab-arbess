package net.tiklab.matflow.orther.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.orther.entity.PipelineThirdAddressEntity;
import net.tiklab.matflow.orther.model.PipelineThirdAddress;
import net.tiklab.matflow.orther.dao.PipelineThirdAddressDao;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineThirdAddressServiceImpl implements PipelineThirdAddressService {

    @Autowired
    PipelineThirdAddressDao authorizeDao;

    /**
     * 创建流水线授权
     * @param pipelineThirdAddress 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthorize(PipelineThirdAddress pipelineThirdAddress) {
        PipelineThirdAddressEntity pipelineThirdAuthEntity = BeanMapper.map(pipelineThirdAddress, PipelineThirdAddressEntity.class);
        return authorizeDao.createAuthorize(pipelineThirdAuthEntity);
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
     * @param pipelineThirdAddress 信息
     */
    @Override
    public void updateAuthorize(PipelineThirdAddress pipelineThirdAddress) {
        PipelineThirdAddressEntity authorizeEntity = BeanMapper.map(pipelineThirdAddress, PipelineThirdAddressEntity.class);
        authorizeDao.updateAuthorize(authorizeEntity);
    }

    /**
     * 查询授权信息
     * @param authorizeId id
     * @return 信息集合
     */
    @Override
    public PipelineThirdAddress findOneAuthorize(String authorizeId) {
        PipelineThirdAddressEntity oneAuthorize = authorizeDao.findOneAuthorize(authorizeId);
        return BeanMapper.map(oneAuthorize, PipelineThirdAddress.class);
    }

    /**
     * 根据类型查询授权信息
     * @param type 类型
     * @return 授权
     */
    @Override
    public PipelineThirdAddress findOneAuthorize(int type){
        List<PipelineThirdAddress> allAuthorize = findAllAuthorize();
        if (allAuthorize == null){
            return null;
        }
        for (PipelineThirdAddress pipelineThirdAddress : allAuthorize) {
            if (pipelineThirdAddress.getAuthType().equals(type)){
                return pipelineThirdAddress;
            }
        }
        return null;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineThirdAddress> findAllAuthorize() {
        List<PipelineThirdAddressEntity> allAuthorize = authorizeDao.findAllAuthorize();
        return BeanMapper.mapList(allAuthorize, PipelineThirdAddress.class);
    }

    @Override
    public List<PipelineThirdAddress> findAllAuthorizeList(List<String> idList) {
        List<PipelineThirdAddressEntity> allAuthorizeList = authorizeDao.findAllAuthorizeList(idList);
        return  BeanMapper.mapList(allAuthorizeList, PipelineThirdAddress.class);
    }


}







































