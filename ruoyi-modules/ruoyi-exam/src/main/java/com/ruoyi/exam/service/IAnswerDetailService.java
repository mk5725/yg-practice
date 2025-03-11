package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.AnswerDetail;
import com.ruoyi.exam.domain.vo.AnswerDetailVo;
import com.ruoyi.exam.domain.bo.AnswerDetailBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 答题详情Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IAnswerDetailService {

    /**
     * 查询答题详情
     */
    AnswerDetailVo queryById(Long id);

    /**
     * 查询答题详情列表
     */
    TableDataInfo<AnswerDetailVo> queryPageList(AnswerDetailBo bo, PageQuery pageQuery);

    /**
     * 查询答题详情列表
     */
    List<AnswerDetailVo> queryList(AnswerDetailBo bo);

    /**
     * 修改答题详情
     */
    Boolean insertByBo(AnswerDetailBo bo);

    /**
     * 修改答题详情
     */
    Boolean updateByBo(AnswerDetailBo bo);

    /**
     * 校验并批量删除答题详情信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
