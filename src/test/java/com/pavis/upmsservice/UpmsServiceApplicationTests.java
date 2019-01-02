package com.pavis.upmsservice;

import com.pavis.upmsservice.mapper.SysUserMapper;
import com.pavis.upmsservice.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpmsServiceApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void initAdmin() {
        SysUser admin = userMapper.selectById(1);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        userMapper.updateById(admin);

    }

    @Test
    public void initUser() {
        SysUser semi = SysUser.builder()
                .username("semi")
                .password(passwordEncoder.encode("123456"))
                .telephone("18082315084")
                .email("semi@s-privacy.com")
                .remark("普通用户")
                .deptId(2)
                .status(1)
                .operator("admin")
                .operateIp("0:0:0:0:0:0:0:1")
                .operateTime(new Date())
                .build();
        userMapper.insert(semi);

        SysUser alibct = SysUser.builder()
                .username("alibct")
                .password(passwordEncoder.encode("123456"))
                .telephone("18082315085")
                .email("alibct@s-privacy.com")
                .remark("普通用户")
                .deptId(3)
                .status(1)
                .operator("admin")
                .operateIp("0:0:0:0:0:0:0:1")
                .operateTime(new Date())
                .build();
        userMapper.insert(alibct);
    }

}

