package com.ruoyi.common.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.core.enums.UserType;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.common.core.web.domain.LsBaseEntity;
import com.ruoyi.common.core.web.domain.WhBaseEntity;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                Date current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                    ? baseEntity.getCreateTime() : new Date();
                baseEntity.setCreateTime(current);
                baseEntity.setUpdateTime(current);
                String username = StringUtils.isNotBlank(baseEntity.getCreateBy())
                    ? baseEntity.getCreateBy() : getLoginUsername();
                // 当前已登录 且 创建人为空 则填充
                baseEntity.setCreateBy(username);
                // 当前已登录 且 更新人为空 则填充
                baseEntity.setUpdateBy(username);
            }
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof LsBaseEntity) {
                LsBaseEntity baseEntity = (LsBaseEntity) metaObject.getOriginalObject();
                Date current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                    ? baseEntity.getCreateTime() : new Date();
                baseEntity.setCreateTime(current);
                baseEntity.setUpdateTime(current);
                Long userId = ObjectUtil.isNotNull(baseEntity.getCreateUserId())
                    ? baseEntity.getCreateUserId() : getLoginUserLongId();
                baseEntity.setCreateUserId(userId);
                baseEntity.setUpdateUserId(userId);
            }
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof WhBaseEntity) {
                WhBaseEntity whBaseEntity = (WhBaseEntity) metaObject.getOriginalObject();
                Date current = ObjectUtil.isNotNull(whBaseEntity.getCreateTime())
                    ? whBaseEntity.getCreateTime() : new Date();
                whBaseEntity.setCreateTime(current);
                whBaseEntity.setUpdateTime(current);
                String username = StringUtils.isNotBlank(whBaseEntity.getCreateUserName())
                    ? whBaseEntity.getCreateUserName() : getLoginUsername();
                Long userId = ObjectUtil.isNotNull(whBaseEntity.getCreateUserId())
                    ? whBaseEntity.getCreateUserId() : Long.parseLong(Objects.requireNonNull(getLoginUserId()));
                // 当前已登录 且 创建人为空 则填充
                whBaseEntity.setCreateUserId(userId);
                whBaseEntity.setCreateUserName(username);
                // 当前已登录 且 更新人为空 则填充
                whBaseEntity.setUpdateUserId(userId);
                whBaseEntity.setUpdateUserName(username);
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                Date current = new Date();
                // 更新时间填充(不管为不为空)
                baseEntity.setUpdateTime(current);
                String username = getLoginUsername();
                // 当前已登录 更新人填充(不管为不为空)
                if (StringUtils.isNotBlank(username)) {
                    baseEntity.setUpdateBy(username);
                }
            }
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof LsBaseEntity) {
                LsBaseEntity baseEntity = (LsBaseEntity) metaObject.getOriginalObject();
                Date current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                    ? baseEntity.getCreateTime() : new Date();
                baseEntity.setUpdateTime(current);
                Long userId = ObjectUtil.isNotNull(baseEntity.getCreateUserId())
                    ? baseEntity.getCreateUserId() : getLoginUserLongId();
                baseEntity.setUpdateUserId(userId);
            }
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof WhBaseEntity) {
                WhBaseEntity whBaseEntity = (WhBaseEntity) metaObject.getOriginalObject();
                Date current = new Date();
                // 更新时间填充(不管为不为空)
                whBaseEntity.setUpdateTime(current);
                String username = getLoginUsername();
                // 当前已登录 更新人填充(不管为不为空)
                if (StringUtils.isNotBlank(username)) {
                    whBaseEntity.setUpdateUserName(username);
                }
                long userID = Long.parseLong(Objects.requireNonNull(getLoginUserId()));
                // 当前已登录 更新人ID填充(不管为不为空)
                if (ObjectUtil.isNotNull(userID)) {
                    whBaseEntity.setUpdateUserId(userID);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    /**
     * 获取登录用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
    }

    /**
     * 获取登录用户id
     */
    private String getLoginUserId() {
        LoginUser loginUser;
        try {
            /*if (UserType.SYS_SUPPLIER == LoginHelper.getUserType()) {
                //供应商门户端-自动注入新建人和更新人
                loginUser = LoginHelper.getLoginSupplier();
            } else {*/
                loginUser = LoginHelper.getLoginUser();
            //}
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? String.valueOf(loginUser.getUserId()) : null;
    }

    /**
     * 获取登录用户id
     */
    private Long getLoginUserLongId() {
        LoginUser loginUser;
        try {
            /*if (UserType.SYS_SUPPLIER == LoginHelper.getUserType()) {
                //供应商门户端-自动注入新建人和更新人
                loginUser = LoginHelper.getLoginSupplier();
            } else {*/
            loginUser = LoginHelper.getLoginUser();
            //}
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUserId() : null;
    }

}
