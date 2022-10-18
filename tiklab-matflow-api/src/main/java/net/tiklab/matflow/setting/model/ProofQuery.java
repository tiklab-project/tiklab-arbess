package net.tiklab.matflow.setting.model;

import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;

/**
 * 凭证模糊查询
 */

@ApiModel
@Join
public class ProofQuery {

    //用户id
    private String userId;

    //凭证类型
    private int type;

    //模糊查询
    private String name;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
