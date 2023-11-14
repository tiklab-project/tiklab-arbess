package io.tiklab.matflow.setting.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * @author zcamy
 */

@Entity
@Table(name="pip_auth_host_group")
public class AuthHostGroupEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

    //创建人
    @Column(name = "user_id")
    private String userId ;

    //是否公开 1：公开， 2：不公开
    @Column(name = "auth_public")
    private int authPublic;


}
