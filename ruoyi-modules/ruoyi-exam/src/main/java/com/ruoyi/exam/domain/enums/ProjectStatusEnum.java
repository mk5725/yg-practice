package com.ruoyi.exam.domain.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 项目状态枚举类
 * @author z
 */
@Getter
public enum ProjectStatusEnum {

    NEW("新建", "0"),
    IN_PROGRESS("进行中", "1"),
    COMPLETED("完成", "2");

    private final String text;

    private final String value;

    ProjectStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的 value
     * @return 枚举值
     */
    public static ProjectStatusEnum getEnumByValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        for (ProjectStatusEnum statusEnum : ProjectStatusEnum.values()) {
            if (statusEnum.value.equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
