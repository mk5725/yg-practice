package com.ruoyi.exam.contants;

import java.math.BigDecimal;

/**
 * @author l
 */
public interface ExamConstants {

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

    Long DEFAULT_REVIEWER = 1L;

    String DEFAULT_PHONE = "无";

    // 项目编号长度
    Integer PROJECT_NO_LENGTH = 12;

    // 项目编号前缀
    String PROJECT_NO_PREFIX = "PJ";

    // 答案分隔符常量
    String ANSWER_SEPARATOR = "|";

    Integer ANSWER_RIGHT = 1;

    Integer ANSWER_ERROR = 0;

    // 待批改
    Integer ANSWER_WILL_REVIEWER = 2;


}
