package com.ruoyi.exam.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import com.ruoyi.exam.domain.dto.ExamPaperData;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;


/**
 * 试卷视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class ExamPaperVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * -- 查询试卷信息 根据记录ID
     *  记录ID、试卷ID、
     *  试卷名称、
     *  试卷类型
     *  试卷总分
     *  题目数量、对题数量
     *  考试开始时间
     *  考试结束时间
     *  考试时长 （依据发放时长send_duration）
     *   试卷标题：
     *      标题ID,
     *      标题显示排序
     *      标题名称
     *      题目信息：
     *        题目ID、题目类型、题干、选项、分值
     *
     * -- 提交答案
     *  记录ID、试卷ID、题目ID、用户答案、该题提交时间，（自动填充）批改人ID, 批改时间
     *  是否正确、单题得分、
     *
     * -- 提交试卷 （更新考试记录）
     *  记录ID、试卷提交时间、总得分、更新记录状态
     *
     *  更新发放表试卷状态
     *  撒放ID、状态、
     *
     */

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 记录ID
     */
    @ExcelProperty(value = "记录ID")
    private Long recordId;

    /**
     * 试卷Id
     */
    @ExcelProperty(value = "试卷Id")
    private Long paperId;

    /**
     * 试卷名称
     */
    @ExcelProperty(value = "试卷名称")
    private String paperName;

    /**
     * 考试类型
     */
    @ExcelProperty(value = "考试类型")
    private String paperType;

    /**
     * 考试时长（分钟）
     */
    @ExcelProperty(value = "考试时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "分=钟")
    private Integer duration;

    /**
     * 要求考试时长 - 发放试卷时限定
     */
    @ExcelProperty(value = "考试时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "考试时长")
    private Integer sendDuration;

    /**
     * 试卷总分
     */
    @ExcelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    /**
     * 题目数量
     */
    @ExcelProperty(value = "题目数量")
    private Integer totalQuestion;

    /**
     * 答对题目数量
     */
    @ExcelProperty(value = "答对题目数量")
    private Integer totalOkQuestion;

    /**
     * 考试开始时间
     */
    @ExcelProperty(value = "考试开始时间")
    private Date startTime;

    /**
     * 考试结束时间
     */
    @ExcelProperty(value = "考试结束时间")
    private Date endTime;

    /**
     * 及格分数
     */
    @ExcelProperty(value = "及格分数")
    private BigDecimal passScore;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    private ExamRecordVo record;
    /**
     * 试卷标题 - 题目列表数据
     */
    private List<ExamPaperTitleVo> paperTitleList = new ArrayList<>();;

}
