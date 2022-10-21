package net.tiklab.matflow.definition.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.PipelineCode;
import net.tiklab.matflow.execute.service.CodeGitHubService;
import net.tiklab.matflow.execute.service.CodeGiteeApiService;
import net.tiklab.matflow.orther.service.ValidationAddress;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Exporter
public class PipelineCodeAuthorizeServiceImpl implements PipelineCodeAuthorizeService {


    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    CodeGitHubService codeGitHubService;


    //通过授权信息获取仓库url
    @Override
    public PipelineCode getAuthorizeUrl(PipelineCode pipelineCode) {
        int type = pipelineCode.getType();
        if (type == 2) {
            if (pipelineCode.getProof() == null) {
                return null;
            }
            String cloneUrl = codeGiteeApiService.getCloneUrl(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
            return pipelineCode;
        }
        if (type == 3) {
            if (pipelineCode.getProof() == null) {
                return null;
            }
            String cloneUrl = codeGitHubService.getOneHouse(pipelineCode.getProof(), pipelineCode.getCodeName());
            pipelineCode.setCodeAddress(cloneUrl);
            return pipelineCode;
        }

        String codeName = pipelineCode.getCodeName();
        if (codeName != null) {
            boolean b = ValidationAddress.validCode(codeName);
            if (!b) throw new ApplicationException(50002, "请输入正确的地址");
        }
        pipelineCode.setCodeAddress(codeName);
        return pipelineCode;
    }

}
