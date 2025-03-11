package com.ruoyi.exam.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;


/**
 * 题目类型视图对象
 *
 * @author zkm
 * @date 2025-03-11
 */
@Data
@ExcelIgnoreUnannotated
public class QuestionTypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目类型ID
     */
    @ExcelProperty(value = "题目类型ID")
    private Long id;

    /**
     * 类型编码（（1-单选, 2-多选, 3-判断, 4-填空, 5-简答 ...）
     */
    @ExcelProperty(value = "类型编码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "（=1-单选,,2=-多选,,3=-判断,,4=-填空,,5=-简答,.=..")
    private Long typeCode;

    /**
     * 类型名称（单选题、多选题、...）
     */
    @ExcelProperty(value = "类型名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "单=选题、多选题、...")
    private String typeName;

}
