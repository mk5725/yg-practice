package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.domain.bo.ExamSendBo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 试卷发放记录Mapper接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface ExamSendMapper extends BaseMapperPlus<ExamSendMapper, ExamSend, ExamSendVo> {

    /**
     * 查询试卷发放记录
     */
    Page<ExamSendVo> selectExamSendPage(@Param("page") Page page, @Param("bo") ExamSendBo bo);

    /**
     * 查询试卷是否被发放
     */
    @Select("select * from exam_send where paper_id = #{paperId} and del_flag = '0'")
    List<ExamSendVo> selectSendByPaperId(@Param("paperId") Long paperID);
}
