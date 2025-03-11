package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.QuestionType;
import com.ruoyi.exam.domain.vo.QuestionTypeVo;
import com.ruoyi.exam.domain.bo.QuestionTypeBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 题目类型Service接口
 *
 * @author zkm
 * @date 2025-03-11
 */
public interface IQuestionTypeService {

    /**
     * 查询题目类型
     */
    QuestionTypeVo queryById(Long id);

    /**
     * 查询题目类型列表
     */
    TableDataInfo<QuestionTypeVo> queryPageList(QuestionTypeBo bo, PageQuery pageQuery);

    /**
     * 查询全部题目类型列表
     */
    List<QuestionTypeVo> queryListAll(QuestionTypeBo bo);

    /**
     * 查询题目类型列表
     */
    List<QuestionTypeVo> queryList(QuestionTypeBo bo);

    /**
     * 修改题目类型
     */
    Boolean insertByBo(QuestionTypeBo bo);

    /**
     * 修改题目类型
     */
    Boolean updateByBo(QuestionTypeBo bo);

    /**
     * 校验并批量删除题目类型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
