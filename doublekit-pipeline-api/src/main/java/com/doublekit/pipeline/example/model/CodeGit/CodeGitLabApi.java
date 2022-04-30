package com.doublekit.pipeline.example.model.CodeGit;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class CodeGitLabApi {

    private final String ApplicationID = "093a1b99b73d464b8894a5902d5fb500b2d98fcf82f7efcadde98a51e24e3a02";

    private final String Secre = "4be0fb2ec47d0fb471e185b1d7ffad333bc6ee9dd7f3537dff5412f4f32b1640";
    private final String redirect_uri = "https://www.baidu.com/";

    private final String state = "zhangcheng";

    private final String scope = "api";


    public String getCode(){
        return "https://gitlab.example.com/oauth/authorize?" +
                "client_id=" + ApplicationID+
                "&redirect_uri=" +redirect_uri+
                "&response_type=code" ;
    }
}
