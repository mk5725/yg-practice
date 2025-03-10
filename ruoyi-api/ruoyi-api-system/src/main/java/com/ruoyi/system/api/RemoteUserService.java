package com.ruoyi.system.api;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.exception.user.UserException;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.api.model.XcxLoginUser;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务
 *
 * @author Lion Li
 */
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    LoginUser getUserInfo(String username) throws UserException;

    /**
     * 通过手机号查询用户信息
     *
     * @param phonenumber 手机号
     * @return 结果
     */
    LoginUser getUserInfoByPhonenumber(String phonenumber) throws UserException;

    /**
     * 通过邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 结果
     */
    LoginUser getUserInfoByEmail(String email) throws UserException;

    /**
     * 通过openid查询用户信息
     *
     * @param openid openid
     * @return 结果
     */
    XcxLoginUser getUserInfoByOpenid(String openid) throws UserException;

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @return 结果
     */
    Boolean registerUserInfo(SysUser sysUser) throws UserException, ServiceException;

    /**
     * 通过userId查询用户账户
     *
     * @param userId 用户id
     * @return 结果
     */
    String selectUserNameById(Long userId);


    /**
     * 查询所有用户信息
     * @return 用户信息
     */
    List<SysUserVo> selectUserVoAll();


    /**
     * 查询用户
     * @return 用户信息
     */
    List<SysUserVo> getUserVoExcludeIds(Collection<Long> ids);

    /**
     * 根据条件查询用户
     * @return 用户信息
     */
    List<SysUserVo> getUserVoList(SysUserVo sysUserVo);

    /**
     * 根据用户集合查询用户信息
     * @param ids 用户集合
     * @return 用户信息
     */
    List<SysUserVo> selectUserVoByIds(Collection<Long> ids);
}
