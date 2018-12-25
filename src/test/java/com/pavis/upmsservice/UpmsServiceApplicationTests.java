package com.pavis.upmsservice;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pavis.upmsservice.mapper.SysUserMapper;
import com.pavis.upmsservice.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpmsServiceApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void initAdmin() {
        SysUser admin = SysUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .telephone("18082315083")
                .email("admin@s-privacy.com")
                .remark("超级管理员")
                .deptId(1)
                .status(1)
                .operator("admin")
                .operateIp("0:0:0:0:0:0:0:1")
                .operateTime(new Date())
                .build();
        userMapper.insert(admin);


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

    @Test
    public void initClient() {
        // 初始化client
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("test");
        clientDetails.setClientSecret(passwordEncoder.encode("123456"));
        clientDetails.setScope(Lists.newArrayList("test"));
        clientDetails.setResourceIds(null);
        clientDetails.setAuthorizedGrantTypes(Lists.newArrayList("password", "refresh_token"));
        clientDetails.setRegisteredRedirectUri(null);
        clientDetails.setAutoApproveScopes(Lists.newArrayList());
        clientDetails.setAuthorities(Lists.newArrayList());
        clientDetails.setAccessTokenValiditySeconds(60 * 60 * 12);
        clientDetails.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        clientDetails.setAdditionalInformation(Maps.newHashMap());
        clientDetailsService.addClientDetails(clientDetails);
    }

}

