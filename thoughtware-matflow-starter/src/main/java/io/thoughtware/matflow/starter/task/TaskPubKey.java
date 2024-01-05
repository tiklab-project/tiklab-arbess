package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.setting.dao.AuthDao;
import io.thoughtware.matflow.setting.entity.AuthEntity;
import io.thoughtware.matflow.setting.model.Auth;
import io.thoughtware.matflow.setting.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class TaskPubKey implements ApplicationRunner {

    @Autowired
    AuthService authService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        updatePviKey();
    }


    public void updatePviKey(){

        Auth auth = authService.findOneAuth("f812ab93d8ff");
        if (Objects.isNull(auth)){
            return;
        }
        if (pvi_key.equals(auth.getPrivateKey())){
            return;
        }

        auth.setPrivateKey(pvi_key);
        authService.updateAuth(auth);
    }



    public String pub_key = """
                                 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDnqLwl4hKCUDh2l1AuO/CA2NADlHtEinPWf5muyAFpq2Ryoi3HR9VUYAMkdqz+6ARpYXvwYnTZfmPQ/p+Yspjf0xRj6frO+EEBJKmr2tUPFsiSd3dsMSOihyKoAbwSwLgvJ5ar3xVOKLZNS5vkK4Wp6ohGrZo5g9MPsppTsink5a8Z07cBcNIev8m4Z0XurnC6xI5MaYC5cZkV2RgJ532bSlqLwdraHQ8wjWkm9YEncENxu5dsKj9jftcEtgKE7tJn+X/JXxBQbApS53Qs4wx3pEljwy9cSiphtKSqmW2JypKaia7sCCKOUodx7Twy61JpsfQcPyT35v/ZwiYO69mLKpNid4MSuIV/TxWwauqGPQ52EBGno7Qpl1fB6oaaQsSM2tIwmdFqGVL61wtjKEfIyulkKbTrk8neO4Idcl4mYXi6pUhsTDJit9Uw0wdOqA29M1Frcwd2xJr5hkUR/nzcJIckgQeBAnhNZ60BWN+0lgS4VHr8QI2vrr4nQSluNO0= zcamy@zhangcheng.local
                                 """;
    public String pvi_key = """ 
                                 -----BEGIN OPENSSH PRIVATE KEY-----
                                 b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAABlwAAAAdzc2gtcn
                                 NhAAAAAwEAAQAAAYEA56i8JeISglA4dpdQLjvwgNjQA5R7RIpz1n+ZrsgBaatkcqItx0fV
                                 VGADJHas/ugEaWF78GJ02X5j0P6fmLKY39MUY+n6zvhBASSpq9rVDxbIknd3bDEjoociqA
                                 G8EsC4LyeWq98VTii2TUub5CuFqeqIRq2aOYPTD7KaU7Ip5OWvGdO3AXDSHr/JuGdF7q5w
                                 usSOTGmAuXGZFdkYCed9m0pai8Ha2h0PMI1pJvWBJ3BDcbuXbCo/Y37XBLYChO7SZ/l/yV
                                 8QUGwKUud0LOMMd6RJY8MvXEoqYbSkqplticqSmomu7AgijlKHce08MutSabH0HD8k9+b/
                                 2cImDuvZiyqTYneDEriFf08VsGrqhj0OdhARp6O0KZdXweqGmkLEjNrSMJnRahlS+tcLYy
                                 hHyMrpZCm065PJ3juCHXJeJmF4uqVIbEwyYrfVMNMHTqgNvTNRa3MHdsSa+YZFEf583CSH
                                 JIEHgQJ4TWetAVjftJYEuFR6/ECNr66+J0EpbjTtAAAFkA4TRccOE0XHAAAAB3NzaC1yc2
                                 EAAAGBAOeovCXiEoJQOHaXUC478IDY0AOUe0SKc9Z/ma7IAWmrZHKiLcdH1VRgAyR2rP7o
                                 BGlhe/BidNl+Y9D+n5iymN/TFGPp+s74QQEkqava1Q8WyJJ3d2wxI6KHIqgBvBLAuC8nlq
                                 vfFU4otk1Lm+QrhanqiEatmjmD0w+ymlOyKeTlrxnTtwFw0h6/ybhnRe6ucLrEjkxpgLlx
                                 mRXZGAnnfZtKWovB2todDzCNaSb1gSdwQ3G7l2wqP2N+1wS2AoTu0mf5f8lfEFBsClLndC
                                 zjDHekSWPDL1xKKmG0pKqZbYnKkpqJruwIIo5Sh3HtPDLrUmmx9Bw/JPfm/9nCJg7r2Ysq
                                 k2J3gxK4hX9PFbBq6oY9DnYQEaejtCmXV8HqhppCxIza0jCZ0WoZUvrXC2MoR8jK6WQptO
                                 uTyd47gh1yXiZheLqlSGxMMmK31TDTB06oDb0zUWtzB3bEmvmGRRH+fNwkhySBB4ECeE1n
                                 rQFY37SWBLhUevxAja+uvidBKW407QAAAAMBAAEAAAGBAKxAALZCZPTUZUd4tzvJgcisbe
                                 9bhlSuhfdmJYWaXcKOmD/MazGULgFw6qVAQN3A8xbQT3GsI6pcmieaTF0j3xv+PVkVb1JZ
                                 sSwCcM7CBk4T6MtPXVLrLs34oUI/F/LxrYISc6wUUiktiG/s2ltMKTTwmKfFCbneVR+E88
                                 9r2E7EofAYRikiFHqcrIca8xrIRjX5VL1obTh2SRe/tmoW4ISJQs5MNEid9t9pJli7j28d
                                 wGhezfa1azC17XcpRrJFSG+j8KrP9sluFtnBw1e6wxA0KYV8AIRm8LKTvb963ywshf6rab
                                 3Uv4w3f1NS88tnlzIPtlkwyHJWQwoY7EQ7QLAR6m8E2E9Pd9Ojq/3ZTVJeoYFDLHoqh+dx
                                 vzyopv0o9pGrZxNSsuD/FyFlOSliLCaUeyp1XLIyHLRIKzGeQFYuWuH33YbJYhlkI40PwE
                                 CZ+BkQK5OZ8xkHEkH+QT7+VvAEUqp8A0YBiwadHPQAt8xTgj4nf96wa8QaViASTEJHmQAA
                                 AMEAqFTSLtu9495Q77g6OLdsB60+RvzR8QugvvXoTgFv+wAEUU0UCfx175R6DAfQa7moAy
                                 nhftZLjhdDah9z1Ll3dKPD6ryOOPgKhG1HmywPQyrPSlTru8M1o1d+4O7EL6BEnXVmXhIJ
                                 2IPmOfU0XvoRVg6SkuV0CjTX3fAkavfwPBME/eZRVZoH/7gMRcDyHe3UbwgYRNt0dJ/8cT
                                 +As6D/pOv9zZc74VMUoCITbcq31AZHbin0AUho9C3BJ7YgXkSrAAAAwQD4JZyN+j3zYlJ4
                                 r1s+SEd9BeZV2atmA0TiTq1EeATvGAQvDCZI4cc7rTezj539BgfJpgDPZ81rg6EZt6k45d
                                 +vNUXsXdH7RiObvExjaFSxMvVFFRTNNBztyCuDg1ummzbuqiFad1DySFVA/Xr5nuqKL57G
                                 X7UDCgxYDKJesPAE/MG3Xbv+IehFvpCsXKwGXjyZoUaZzmK6nYu6oQiQ0hgDqcC/RV0pZG
                                 IvmtcFmeKdmEX/eRpGDszTD1nktr33AWsAAADBAO79i7dfI74LF9Vh+4amJOw1E/Xf/FkA
                                 nDb43H6z7G21ac7DLoC3WthPCA0Heg3zprhCoEJ0Vm3EdPhmaQ5XvFWima8NKRxdfR3xE7
                                 PYNyVdYGWgdjtuGTF7m6rCz4LtLwVuT0a89ltVF+gUCC1LLS6iAbb4pOO+H3TnvBX/QL0M
                                 k+BJpF9AS/gFD9nFdLe5HNM2Zd9o3cAqUghEfDOCPvdqbDXwJKx3fT2VLDfLaKdZSdW7L0
                                 E9pQ7QPKWY6DNBBwAAABZ6Y2FteUB6aGFuZ2NoZW5nLmxvY2FsAQID
                                 -----END OPENSSH PRIVATE KEY-----
                                 """;



}
