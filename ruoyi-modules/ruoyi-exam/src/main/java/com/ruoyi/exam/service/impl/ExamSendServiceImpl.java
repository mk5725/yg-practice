package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.exam.domain.ExamRecord;
import com.ruoyi.exam.domain.vo.ExamRecordVo;
import com.ruoyi.exam.exception.BusinessException;
import com.ruoyi.exam.exception.ErrorCode;
import com.ruoyi.exam.mapper.ExamRecordMapper;
import com.ruoyi.exam.service.IExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.bo.ExamSendBo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.mapper.ExamSendMapper;
import com.ruoyi.exam.service.IExamSendService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.exam.contants.ExamStatusConstants.STATUS_PENDING;


/**
 * 试卷发放记录Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamSendServiceImpl implements IExamSendService {

    private final ExamSendMapper baseMapper;

    private final IExamPaperService examPaperService;

    private final ExamRecordMapper recordMapper;

    /**
     * 查询试卷发放记录详情
     */
    @Override
    public ExamSendVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询试卷发放记录列表
     */
    @Override
    public TableDataInfo<ExamSendVo> queryPageList(ExamSendBo bo, PageQuery pageQuery) {

        Page<ExamSendVo> result = baseMapper.selectExamSendPage(pageQuery.build(), bo);

        return TableDataInfo.build(result);
    }

    /**
     * 查询试卷发放记录列表
     */
    @Override
    public List<ExamSendVo> queryList(ExamSendBo bo) {
        LambdaQueryWrapper<ExamSend> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamSend> buildQueryWrapper(ExamSendBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamSend> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPaperId() != null, ExamSend::getPaperId, bo.getPaperId());
        lqw.eq(bo.getStartTime() != null, ExamSend::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, ExamSend::getEndTime, bo.getEndTime());
        lqw.eq(bo.getStatus() != null, ExamSend::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增试卷发放记录
     */
    @Override
    @Transactional
    public Boolean insertByBo(ExamSendBo bo) {
        // 判断试卷是否存在
        if (!examPaperService.existsWithLock(bo.getPaperId())){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "试卷不存在");
        }

        ExamSend add = BeanUtil.toBean(bo, ExamSend.class);
        baseMapper.insert(add);

        // 构造考试记录
        Set<Long> userIdSet = new HashSet<>(bo.getUserIds());
        List<ExamRecord> recordList = userIdSet.stream().map(id ->
            ExamRecord.builder()
                .sendId(add.getId())
                .userId(id)
                .paperId(add.getPaperId())
                .build()).collect(Collectors.toList());

        // 批量添加
        return recordMapper.insertBatch(recordList);

        // TODO 考生考试时间冲突、考生不存在、考试区间与考试时长
    }

    /**
     * 修改试卷发放记录
     */
    @Override
    public Boolean updateByBo(ExamSendBo bo) {
        ExamSend update = BeanUtil.toBean(bo, ExamSend.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 批量删除试卷发放记录
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        // 查询考试记录
        List<ExamRecordVo> recordVoList = recordMapper.selectVoList(Wrappers.<ExamRecord>lambdaQuery()
            .in(ExamRecord::getSendId, ids));
        if (ObjectUtil.isNotEmpty(recordVoList)){
            // 判断考试状态
            boolean hasExam = recordVoList.stream().anyMatch(record -> record.getRecordStatus() > STATUS_PENDING);
            if (hasExam){
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "考试已开始，删除失败!");
            }

            // 删除发放的考试记录
            recordMapper.deleteBatchIds(recordVoList.stream()
                .map(ExamRecordVo::getId).collect(Collectors.toSet()));
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
