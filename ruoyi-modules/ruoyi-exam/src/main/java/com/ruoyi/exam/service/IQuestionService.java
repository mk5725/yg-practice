package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.domain.vo.QuestionVo;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 题目Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IQuestionService {

    /**
     * 查询题目
     */
    QuestionVo queryById(Long id);

    /**
     * 查询题目列表
     */
    TableDataInfo<QuestionVo> queryPageList(QuestionBo bo, PageQuery pageQuery);

    /**
     * 查询题目列表
     */
    List<QuestionVo> queryList(QuestionBo bo);

    /**
     * 修改题目
     */
    Boolean insertByBo(QuestionBo bo);

    /**
     * 修改题目
     */
    Boolean updateByBo(QuestionBo bo);

    /**
     * 校验并批量删除题目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
