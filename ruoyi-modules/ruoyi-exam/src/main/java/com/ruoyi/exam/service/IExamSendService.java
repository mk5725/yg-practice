package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.domain.bo.ExamSendBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 试卷发放记录Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IExamSendService {

    /**
     * 查询试卷发放记录
     */
    ExamSendVo queryById(Long id);

    /**
     * 查询试卷发放记录列表
     */
    TableDataInfo<ExamSendVo> queryPageList(ExamSendBo bo, PageQuery pageQuery);

    /**
     * 查询试卷发放记录列表
     */
    List<ExamSendVo> queryList(ExamSendBo bo);

    /**
     * 修改试卷发放记录
     */
    Boolean insertByBo(ExamSendBo bo);

    /**
     * 修改试卷发放记录
     */
    Boolean updateByBo(ExamSendBo bo);

    /**
     * 校验并批量删除试卷发放记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
