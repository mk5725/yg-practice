package com.ruoyi.system.dubbo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.exception.user.UserException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.api.model.RoleDTO;
import com.ruoyi.system.api.model.XcxLoginUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysPermissionService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户服务
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteUserServiceImpl implements RemoteUserService {

    private final ISysUserService userService;
    private final ISysPermissionService permissionService;
    private final ISysConfigService configService;
    private final SysUserMapper userMapper;

    @Override
    public LoginUser getUserInfo(String username) throws UserException {
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserName, SysUser::getStatus)
                .eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(sysUser)) {
            throw new UserException("user.not.exists", username);
        }
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            throw new UserException("user.blocked", username);
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        return buildLoginUser(userMapper.selectUserByUserName(username));
    }

    @Override
    public LoginUser getUserInfoByPhonenumber(String phonenumber) throws UserException {
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getPhonenumber, SysUser::getStatus)
                .eq(SysUser::getPhonenumber, phonenumber));
        if (ObjectUtil.isNull(sysUser)) {
            throw new UserException("user.not.exists", phonenumber);
        }
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            throw new UserException("user.blocked", phonenumber);
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        return buildLoginUser(userMapper.selectUserByPhonenumber(phonenumber));
    }

    @Override
    public LoginUser getUserInfoByEmail(String email) throws UserException {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getPhonenumber, SysUser::getStatus)
            .eq(SysUser::getEmail, email));
        if (ObjectUtil.isNull(user)) {
            throw new UserException("user.not.exists", email);
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            throw new UserException("user.blocked", email);
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        return buildLoginUser(userMapper.selectUserByEmail(email));
    }

    @Override
    public XcxLoginUser getUserInfoByOpenid(String openid) throws UserException {
        // todo 自行实现 userService.selectUserByOpenid(openid);
        SysUser sysUser = new SysUser();
        if (ObjectUtil.isNull(sysUser)) {
            // todo 用户不存在 业务逻辑自行实现
        }
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            // todo 用户已被停用 业务逻辑自行实现
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        XcxLoginUser loginUser = new XcxLoginUser();
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setUsername(sysUser.getUserName());
        loginUser.setUserType(sysUser.getUserType());
        loginUser.setOpenid(openid);
        return loginUser;
    }

    @Override
    public Boolean registerUserInfo(SysUser sysUser) throws UserException, ServiceException {
        String username = sysUser.getUserName();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            throw new ServiceException("当前系统没有开启注册功能");
        }
        if (!userService.checkUserNameUnique(sysUser)) {
            throw new UserException("user.register.save.error", username);
        }
        return userService.registerUser(sysUser);
    }

    @Override
    public String selectUserNameById(Long userId) {
        return userService.selectUserNameById(userId);
    }

    @Override
    public List<SysUserVo> selectUserVoAll() {
        return userMapper.selectVoList(null, SysUserVo.class);
    }

    @Override
    public List<SysUserVo> getUserVoExcludeIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return this.selectUserVoAll();
        }
        return userMapper.selectVoList(
            Wrappers.lambdaQuery(SysUser.class)
                .notIn(SysUser::getUserId, ids),
            SysUserVo.class
        );
    }

    /**
     * 根据条件查询用户
     * @return 用户信息
     */
    @Override
    public List<SysUserVo> getUserVoList(SysUserVo sysUserVo){
        if (ObjUtil.isNull(sysUserVo)){
            return this.selectUserVoAll();
        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserVo, sysUser);
        return userMapper.selectVoList(buildQueryWrapper(sysUser), SysUserVo.class);
    }
    /**
     * 根据Ids查询用户信息
     * @param ids 用户ids
     * @return
     */
    @Override
    public List<SysUserVo> selectUserVoByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectVoBatchIds(ids, SysUserVo.class);
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setPassword(user.getPassword());
        loginUser.setUserType(user.getUserType());
        loginUser.setMenuPermission(permissionService.getMenuPermission(user));
        loginUser.setRolePermission(permissionService.getRolePermission(user));
        loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 构造查询wrapper
     *
     */
    private LambdaQueryWrapper<SysUser> buildQueryWrapper(SysUser e) {
        String userName = StrUtil.isBlank(e.getUserName()) ? null : e.getUserName().trim();
        String phonenumber = StrUtil.isBlank(e.getPhonenumber()) ? null : e.getPhonenumber().trim();
        LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(userName), SysUser::getUserName, userName)
        .like(StringUtils.isNotBlank(phonenumber), SysUser::getPhonenumber, phonenumber);
        // 其他条件 ...

        return lqw;
    }


}
