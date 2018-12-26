package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IgnoreUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.LevelUtils;
import com.pavis.upmsservice.common.utils.AuthUtils;
import com.pavis.upmsservice.form.AclModuleForm;
import com.pavis.upmsservice.mapper.SysAclModuleMapper;
import com.pavis.upmsservice.model.SysAclModule;
import com.pavis.upmsservice.service.SysAclModuleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysAclModuleServiceImpl extends ServiceImpl<SysAclModuleMapper, SysAclModule> implements SysAclModuleService {


    @Override
    public SysAclModule add(AclModuleForm form, HttpServletRequest request) {
        if (checkExist(form.getParentId(), form.getName())) {
            throw new ParamException("同层级下存在相同名称的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder()
                .parentId(form.getParentId())
                .name(form.getName())
                .seq(form.getSeq())
                .status(form.getStatus())
                .remark(form.getRemark())
                .build();
        aclModule.setLevel(LevelUtils.calculateLevel(getLevel(form.getParentId()), form.getParentId()));
        aclModule.setOperator(AuthUtils.getCurrentUsername());
        aclModule.setOperateIp(IpUtils.getIpAddr(request));
        aclModule.setOperateTime(new Date());
        baseMapper.insert(aclModule);
        return aclModule;
    }

    @Override
    public SysAclModule update(AclModuleForm form, HttpServletRequest request) {
        if (checkExist(form.getParentId(), form.getName())) {
            throw new ParamException("同层级下存在相同名称的权限模块");
        }
        SysAclModule before = baseMapper.selectById(form.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");
        SysAclModule after = new SysAclModule();
        org.springframework.beans.BeanUtils.copyProperties(form, after, IgnoreUtils.getNullPropertyNames(form));
        after.setLevel(LevelUtils.calculateLevel(getLevel(form.getParentId()), form.getParentId()));
        after.setOperator(AuthUtils.getCurrentUsername());
        after.setOperateIp(IpUtils.getIpAddr(request));
        after.setOperateTime(new Date());

        return updateWithChild(before, after);
    }

    private SysAclModule updateWithChild(SysAclModule before, SysAclModule after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();
        if (!StringUtils.equals(oldLevelPrefix, newLevelPrefix)) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysAclModule> aclModuleList = baseMapper.selectChildAclModuleByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // selectChildAclModuleByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                updateBatchById(aclModuleList);
            }
        }
        baseMapper.updateById(after);
        return baseMapper.selectById(after.getId());
    }

    private boolean checkExist(Integer parentId, String name) {
        QueryWrapper<SysAclModule> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("parent_id", parentId);
        map.put("name", name);
        wrapper.allEq(map);
        List<SysAclModule> aclModuleList = baseMapper.selectList(wrapper);
        return null != aclModuleList && aclModuleList.size() > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = baseMapper.selectById(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
}
