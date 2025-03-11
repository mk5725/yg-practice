package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 题目类型业务对象
 *
 * @author zkm
 * @date 2025-03-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionTypeBo extends LsBaseEntity {

    /**
     * 题目类型ID
     */
    @NotNull(message = "题目类型ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 类型编码（（1-单选, 2-多选, 3-判断, 4-填空, 5-简答 ...）
     */
    @NotNull(message = "类型编码（（1-单选, 2-多选, 3-判断, 4-填空, 5-简答 ...）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer typeCode;

    /**
     * 类型名称（单选题、多选题、...）
     */
    @NotBlank(message = "类型名称（单选题、多选题、...）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String typeName;


}
