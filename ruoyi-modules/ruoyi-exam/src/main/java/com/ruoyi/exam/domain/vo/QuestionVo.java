package com.ruoyi.exam.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import com.ruoyi.exam.domain.AnswerDetail;
import com.ruoyi.exam.domain.dto.QuestionFillBlank;
import com.ruoyi.exam.domain.dto.QuestionOptions;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.util.List;


/**
 * 题目视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class QuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 试卷题目关联主键ID
     */
    private Long examPaperQuestionId;

    /**
     * 题目排序
     */
    private Integer questionOrder;

    /**
     * 题目类型ID
     */
    @ExcelProperty(value = "题目类型ID")
    private Long questionTypeId;

    /**
     * 题目类型编码（1-单选, 2-多选, 3-判断, 4-填空, 5-简答, 6-其他）
     */
    @ExcelProperty(value = "题目类型编码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=-单选,,2=-多选,,3=-判断,,4=-填空,,5=-简答,,6=-其他")
    private Integer questionTypeCode;

    /**
     * 题干内容
     */
    @ExcelProperty(value = "题干内容")
    private String questionText;

    /**
     * 选项（JSON格式）
     */
    @ExcelProperty(value = "选项", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "选项")
    private String options;

    /**
     * 选项列表对象
     */
    private List<QuestionOptions> optionList;

    /**
     * 答案（填空/简答存文本，选择题存索引）
     */
    @ExcelProperty(value = "答案", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "答案")
    private String answer;

    /**
     * 填空数量
     */
    private Integer answerCount;

    /**
     * 解析
     */
    @ExcelProperty(value = "解析")
    private String analysis;

    /**
     * 题目分值
     */
    @ExcelProperty(value = "题目分值")
    private BigDecimal score;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     *  考生作答详情
     */
    private AnswerDetailVo answerDetail;


}
