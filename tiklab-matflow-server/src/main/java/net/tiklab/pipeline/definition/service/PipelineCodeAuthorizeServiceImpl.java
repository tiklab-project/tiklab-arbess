package net.tiklab.pipeline.definition.service;

import net.tiklab.pipeline.definition.model.PipelineCode;
import net.tiklab.pipeline.execute.service.CodeGitHubService;
import net.tiklab.pipeline.execute.service.CodeGiteeApiService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class PipelineCodeAuthorizeServiceImpl implements PipelineCodeAuthorizeService {


    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;


    //通过授权信息获取仓库url
    @Override
    public PipelineCode getAuthorizeUrl(PipelineCode pipelineCode){
        if (pipelineCode.getProof() == null){
            return null;
        }
        if (pipelineCode.getType() == 2 ){
            String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }else if (pipelineCode.getType() == 3){
            String cloneUrl = codeGitHubService.getOneHouse(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
        }else {
            pipelineCode.setCodeAddress(pipelineCode.getCodeName());
        }
        return pipelineCode;
    }

}
