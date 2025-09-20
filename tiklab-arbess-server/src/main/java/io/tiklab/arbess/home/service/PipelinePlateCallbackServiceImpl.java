package io.tiklab.arbess.home.service;

import io.tiklab.privilege.vRole.model.VRole;
import io.tiklab.privilege.vRole.model.VRoleDomain;
import io.tiklab.privilege.vRole.service.VRoleUserService;
import io.tiklab.user.dmUser.model.DmUser;
import io.tiklab.user.dmUser.service.DmUserCallbackService;
import io.tiklab.user.user.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class PipelinePlateCallbackServiceImpl implements VRoleUserService, DmUserCallbackService {

    @Override
    public List<User> findDmVRoleUser(VRoleDomain vRoleDomain) {
        return List.of();
    }

    @Override
    public List<User> findVRoleUser(List<VRole> vRoleList,String domainId) {
        return List.of();
    }

    @Override
    public void dmUserCallback(DmUser dmUser) {

    }


}
