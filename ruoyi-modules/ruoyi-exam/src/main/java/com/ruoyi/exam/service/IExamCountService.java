package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamCount;
import com.ruoyi.exam.domain.vo.ExamCountVo;
import com.ruoyi.exam.domain.bo.ExamCountBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 成绩统计Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IExamCountService {

    /**
     * 查询成绩统计
     */
    ExamCountVo queryById(Long id);

    /**
     * 查询成绩统计列表
     */
    TableDataInfo<ExamCountVo> queryPageList(ExamCountBo bo, PageQuery pageQuery);

    /**
     * 查询成绩统计列表
     */
    List<ExamCountVo> queryList(ExamCountBo bo);

    /**
     * 修改成绩统计
     */
    Boolean insertByBo(ExamCountBo bo);

    /**
     * 修改成绩统计
     */
    Boolean updateByBo(ExamCountBo bo);

    /**
     * 校验并批量删除成绩统计信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
