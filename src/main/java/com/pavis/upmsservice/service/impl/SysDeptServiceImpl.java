package com.pavis.upmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.pavis.upmsservice.common.exception.ParamException;
import com.pavis.upmsservice.common.utils.IgnoreUtils;
import com.pavis.upmsservice.common.utils.IpUtils;
import com.pavis.upmsservice.common.utils.LevelUtils;
import com.pavis.upmsservice.common.utils.AuthUtils;
import com.pavis.upmsservice.form.DeptForm;
import com.pavis.upmsservice.mapper.SysDeptMapper;
import com.pavis.upmsservice.model.SysDept;
import com.pavis.upmsservice.service.SysDeptService;
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
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public SysDept add(DeptForm form, HttpServletRequest request) {
        if (checkExist(form.getParentId(), form.getName())) {
            throw new ParamException("同层级下存在相同名称的部门");
        }
        SysDept sysDept = SysDept.builder()
                .parentId(form.getParentId())
                .name(form.getName())
                .seq(form.getSeq())
                .remark(form.getRemark())
                .build();
        sysDept.setLevel(LevelUtils.calculateLevel(getLevel(form.getParentId()), form.getParentId()));
        sysDept.setOperator(AuthUtils.getCurrentUsername());
        sysDept.setOperateIp(IpUtils.getIpAddr(request));
        sysDept.setOperateTime(new Date());
        baseMapper.insert(sysDept);
        return sysDept;
    }

    @Override
    public SysDept update(DeptForm form, HttpServletRequest request) {
        if (checkExist(form.getParentId(), form.getName())) {
            throw new ParamException("同层级下存在相同名称的部门");
        }
        SysDept before = baseMapper.selectById(form.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        SysDept after = new SysDept();
        org.springframework.beans.BeanUtils.copyProperties(form, after, IgnoreUtils.getNullPropertyNames(form));
        after.setLevel(LevelUtils.calculateLevel(getLevel(form.getParentId()), form.getParentId()));
        after.setOperator(AuthUtils.getCurrentUsername());
        after.setOperateIp(IpUtils.getIpAddr(request));
        after.setOperateTime(new Date());

        return updateWithChild(before, after);
    }

    private SysDept updateWithChild(SysDept before, SysDept after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();
        if (!StringUtils.equals(oldLevelPrefix, newLevelPrefix)) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysDept> deptList = baseMapper.selectChildDeptByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                updateBatchById(deptList);
            }
        }
        baseMapper.updateById(after);
        return baseMapper.selectById(after.getId());
    }

    private boolean checkExist(Integer parentId, String name) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("parent_id", parentId);
        map.put("name", name);
        wrapper.allEq(map);
        List<SysDept> sysDeptList = baseMapper.selectList(wrapper);
        return null != sysDeptList && sysDeptList.size() > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept sysDept = baseMapper.selectById(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }
}
