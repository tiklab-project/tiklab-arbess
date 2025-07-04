package io.tiklab.arbess.setting.k8s.service;

import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.setting.k8s.config.KubectlConfig;
import io.tiklab.arbess.setting.k8s.dao.KubectlDao;
import io.tiklab.arbess.setting.k8s.entity.KubectlEntity;
import io.tiklab.arbess.setting.k8s.model.Kubectl;
import io.tiklab.arbess.setting.k8s.model.KubectlNode;
import io.tiklab.arbess.setting.k8s.model.KubectlQuery;
import io.tiklab.arbess.setting.k8s.model.KubectlVersion;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class KubectlServiceImpl implements KubectlService {

    @Autowired
    KubectlDao kubectlDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineUtilService utilService;


    @Override
    @Transactional
    public String createKubectl(Kubectl kubectl) {
        KubectlEntity kubectlEntity = BeanMapper.map(kubectl, KubectlEntity.class);
        kubectlEntity.setCreateTime(PipelineUtil.date(1));
        String address = utilService.instanceAddress(2);
        String kubectlId = kubectlDao.createKubectl(kubectlEntity);

        String fileName = kubectlId +"_admin.conf";
        String filePath = address+"/k8sConf/"+fileName;

        // 写入文件
        PipelineFileUtil.createFile(filePath);
        PipelineFileUtil.logWriteFile(kubectlEntity.getKubeConfig(),filePath);

        kubectlEntity.setKubeAddress(filePath);
        kubectlEntity.setId(kubectlId);
        kubectlDao.updateKubectl(kubectlEntity);
        return kubectlId;
    }

    @Override
    public void deleteKubectl(String KubectlId) {
        Kubectl oneKubectl = findOneKubectl(KubectlId);
        if (StringUtils.isEmpty(oneKubectl.getKubeAddress())){
            PipelineFileUtil.deleteFile(new File(oneKubectl.getKubeAddress()));
        }
        kubectlDao.deleteKubectl(KubectlId);
    }


    @Override
    @Transactional
    public void updateKubectl(Kubectl kubectl) {
        Kubectl oneKubectl = findOneKubectl(kubectl.getId());
        if (!oneKubectl.getKubeConfig().equals(kubectl.getKubeConfig())){
            String kubeAddress = oneKubectl.getKubeAddress();
            PipelineFileUtil.deleteFile(new File(kubeAddress));

            PipelineFileUtil.createFile(kubeAddress);
            PipelineFileUtil.logWriteFile(kubectl.getKubeConfig(),kubeAddress);
        }

        KubectlEntity kubectlEntity = BeanMapper.map(kubectl, KubectlEntity.class);
        kubectlDao.updateKubectl(kubectlEntity);
    }


    @Override
    public Kubectl findOneKubectl(String KubectlId) {
        KubectlEntity oneKubectl = kubectlDao.findOneKubectl(KubectlId);
        Kubectl kubectl = BeanMapper.map(oneKubectl, Kubectl.class);
        findKubectlDetails(kubectl);
        return kubectl;
    }

    @Override
    public List<Kubectl> findAllKubectl() {
        List<KubectlEntity> kubectlEntityList = kubectlDao.findAllKubectl();
        if (Objects.isNull(kubectlEntityList)){
            return new ArrayList<>();
        }
        List<Kubectl> kubectlList = BeanMapper.mapList(kubectlEntityList, Kubectl.class);
        for (Kubectl kubectl : kubectlList) {
            findKubectlDetails(kubectl);
        }
        return kubectlList;
    }

    @Override
    public List<Kubectl> findAllKubectlList(List<String> idList) {
        List<KubectlEntity> allKubectlList = kubectlDao.findAllKubectlList(idList);
        return BeanMapper.mapList(allKubectlList, Kubectl.class);
    }

    @Override
    public List<Kubectl> findKubectlList(KubectlQuery hostQuery) {
        List<KubectlEntity> kubectlEntityList = kubectlDao.findKubectlList(hostQuery);
        if (Objects.isNull(kubectlEntityList) || kubectlEntityList.isEmpty()){
            return new ArrayList<>();
        }
        List<Kubectl> kubectlList = BeanMapper.mapList(kubectlEntityList, Kubectl.class);
        for (Kubectl kubectl : kubectlList) {
            findKubectlDetails(kubectl);
        }
        return kubectlList;
    }

    @Override
    public Pagination<Kubectl> findKubectlPage(KubectlQuery hostQuery){

        Pagination<KubectlEntity> allKubectlPage = kubectlDao.findKubectlPage(hostQuery);

        List<KubectlEntity> dataList = allKubectlPage.getDataList();

        List<Kubectl> kubectlList = BeanMapper.mapList(dataList, Kubectl.class);
        for (Kubectl kubectl : kubectlList) {
            findKubectlDetails(kubectl);
        }

        return PaginationBuilder.build(allKubectlPage, kubectlList);
    }


    public void findKubectlDetails(Kubectl kubectl) {

        joinTemplate.joinQuery(kubectl,new String[]{"toolKubectl","user"});

        String kubeConfig = kubectl.getKubeAddress();
        String scmAddress = kubectl.getToolKubectl().getScmAddress();

        try {
            KubectlConfig kubectlConfig = KubectlConfig.instance( kubeConfig,scmAddress);

            KubectlVersion k8sVersion = kubectlConfig.findK8sVersion();
            List<KubectlNode> allNodes = kubectlConfig.findAllNodes();

            kubectl.setK8sVersion(k8sVersion);
            kubectl.setAllNodes(allNodes);
            kubectl.setConnect(true);
        }catch (Exception e){
            e.printStackTrace();
            kubectl.setConnect(false);
        }

    }
    
}
