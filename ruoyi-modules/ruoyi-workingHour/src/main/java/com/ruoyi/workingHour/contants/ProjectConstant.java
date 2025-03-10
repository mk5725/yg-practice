package com.ruoyi.workingHour.contants;

import java.math.BigDecimal;

/**
 * @author l
 */
public interface ProjectConstant {

    /**
     * 删除标识 - 未删除
     */
    String NOT_DELETE = "0";

    /**
     * 删除标识 - 已删除
     */
    String DELETE = "0";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 用户角色
     */
    String USER_ROLE = "user";


    String DEFAULT_PASSWORD = "admin123";

    String DEFAULT_NAME = "无名";

    String DEFAULT_PHONE = "无";

    // 项目编号长度
    Integer PROJECT_NO_LENGTH = 12;

    // 项目编号前缀
    String PROJECT_NO_PREFIX = "PJ";

    /**
     * 单日工时上限（所有项目累计不可超过该值）
     */
    BigDecimal MAX_HOUR_DAY = new BigDecimal("16.0");


}
