package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.exam.contants.ExamConstants;
import com.ruoyi.exam.contants.ExamStatusConstants;
import com.ruoyi.exam.contants.QuestionTypeConstants;
import com.ruoyi.exam.domain.AnswerDetail;
import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.domain.bo.*;
import com.ruoyi.exam.domain.enums.ExamStatusEnum;
import com.ruoyi.exam.domain.enums.QuestionTypeEnum;
import com.ruoyi.exam.domain.vo.*;
import com.ruoyi.exam.exception.BusinessException;
import com.ruoyi.exam.exception.ErrorCode;
import com.ruoyi.exam.mapper.AnswerDetailMapper;
import com.ruoyi.exam.mapper.ExamSendMapper;
import com.ruoyi.exam.mapper.QuestionMapper;
import com.ruoyi.exam.service.IExamPaperQuestionService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.ExamRecord;
import com.ruoyi.exam.mapper.ExamRecordMapper;
import com.ruoyi.exam.service.IExamRecordService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ruoyi.exam.contants.ExamConstants.*;
import static com.ruoyi.exam.contants.ExamStatusConstants.*;

/**
 * 考试记录信息Service业务层处理
 *
 * @author zkm
 * @date 2025-03-15
 */
@RequiredArgsConstructor
@Service
public class ExamRecordServiceImpl implements IExamRecordService {

    private final ExamRecordMapper recordMapper;

    private final IExamPaperQuestionService paperQuestionService;

    private final ExamSendMapper sendMapper;

    private final QuestionMapper questionMapper;

    private final AnswerDetailMapper answerDetailMapper;

    @DubboReference
    private RemoteUserService userService;

    /**
     * 根据记录ID 查询试卷详情 -- 考生考试
     */
    @Override
    public ExamPaperVo queryById(Long id) {
        // TODO 判断是否到考试时间

        /*        // 获取考试时间
        ExamSend examSend = examSendMapper.selectById(examRecord.getSendId());
        if (examSend == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "考试信息不存在");
        }

        // 判断考试时间是否有效
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(examSend.getStartTime()) || now.isAfter(examSend.getEndTime())) {
            throw new BusinessException(ErrorCode.EXAM_TIME_ERROR, "不在考试时间范围内");
        }*/

        // 更新考试记录为 - 考试中
        ExamRecord record = ExamRecord.builder().id(id).recordStatus(ExamStatusConstants.STATUS_IN_PROGRESS).build();
        if (recordMapper.updateById(record) != 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "考试记录不存在");
        }

        // 查询试卷信息
        ExamPaperVo examPaperVo = recordMapper.selectExamPaperById(id);

        // 填充题目数量
        Integer total = paperQuestionService.countQuestionByPaperId(examPaperVo.getPaperId());
        examPaperVo.setTotalQuestion(total);

        return examPaperVo;
    }


    /**
     * 根据考试记录ID 获取试卷全部信息（标答、解析、考生答案） - 批改
     */
    @Override
    public ExamPaperVo queryPaperReviewer(Long id) {
        ExamRecordVo record = recordMapper.selectVoById(id);
        if (record == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "考试记录不存在");
        }
        if (record.getRecordStatus() == STATUS_PENDING){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "考试暂未开始，请考试结束后查看！");
        }

        // 查询试卷信息
        ExamPaperVo examPaperVo = recordMapper.selectExamReviewerById(id);
        if (examPaperVo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "试卷信息不存在");
        }

        // 填充考生、批改人名称
        record.setUserName(userService.selectUserNameById(record.getUserId()));
        if (record.getReviewerUserId() != null) {
            record.setReviewerUserName(userService.selectUserNameById(record.getReviewerUserId()));
        }

        examPaperVo.setRecord(record);

        return examPaperVo;
    }

    /**
     * 查询考试记录信息列表
     */
    @Override
    public TableDataInfo<ExamRecordVo> queryPageList(ExamRecordBo bo, PageQuery pageQuery) {
        Page<ExamRecordVo> result = recordMapper.selectRecordVoPage(pageQuery.build(), bo);

        // 获取记录信息
        List<ExamRecordVo> records = result.getRecords();
        if (records.isEmpty()) {
            return TableDataInfo.build();
        }

        // 统计试卷题目数量
        List<ExamPaperQuestionVo> list = paperQuestionService.countPaperWithQuestion();
        Map<Long, Integer> paperMap = list.stream()
            .collect(Collectors.toMap(ExamPaperQuestionVo::getPaperId, ExamPaperQuestionVo::getTotalQuestion));

        // 获取批改人、考生信息
        Set<Long> userIds = new HashSet<>();
        for (ExamRecordVo record : records) {
            userIds.add(record.getUserId());
            if (ObjUtil.isNotEmpty(record.getReviewerUserId())) {
                userIds.add(record.getReviewerUserId());
            }
        }
        Map<Long, String> userMap = userService.selectUserVoByIds(userIds)
            .stream().collect(Collectors.toMap(SysUserVo::getUserId, SysUserVo::getUserName));

        // 填充用户名、题目数量、答对题目数量
        for (ExamRecordVo record : records) {
            String name = userMap.get(record.getUserId());
            record.setUserName(StrUtil.isNotBlank(name) ? name : ExamConstants.DEFAULT_NAME);
            record.setTotalQuestion(paperMap.get(record.getPaperId()));
            if (ExamStatusConstants.STATUS_PENDING != record.getRecordStatus()) {
                int count = answerDetailMapper.countCorrectAnswers(record.getId());
                record.setTotalOkQuestion(count);
                String reviewer = userMap.get(record.getReviewerUserId());
                record.setReviewerUserName(StrUtil.isNotBlank(reviewer) ? reviewer : ExamConstants.DEFAULT_NAME);
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询考试记录信息列表
     */
    @Override
    public List<ExamRecordVo> queryList(ExamRecordBo bo) {
        LambdaQueryWrapper<ExamRecord> lqw = buildQueryWrapper(bo);
        return recordMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamRecord> buildQueryWrapper(ExamRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, ExamRecord::getId, bo.getId());
        lqw.eq(bo.getSendId() != null, ExamRecord::getSendId, bo.getSendId());
        lqw.eq(bo.getUserId() != null, ExamRecord::getUserId, bo.getUserId());
        lqw.eq(bo.getRecordStatus() != null, ExamRecord::getRecordStatus, bo.getRecordStatus());
        lqw.eq(bo.getScore() != null, ExamRecord::getScore, bo.getScore());
        lqw.eq(bo.getSubmitTime() != null, ExamRecord::getSubmitTime, bo.getSubmitTime());
        lqw.orderBy(true, false, ExamRecord::getCreateTime);
        return lqw;
    }

    /**
     * 新增考试记录信息
     */
    @Override
    public Boolean insertByBo(ExamRecordBo bo) {
        ExamRecord add = BeanUtil.toBean(bo, ExamRecord.class);
        boolean flag = recordMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改考试记录信息
     */
    @Override
    public Boolean updateByBo(ExamRecordBo bo) {
        ExamRecord update = BeanUtil.toBean(bo, ExamRecord.class);
        return recordMapper.updateById(update) > 0;
    }

    /**
     * 批量删除考试记录信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return recordMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 交卷
     */
    @Override
    @Transactional
    public boolean submitExamPaper(ExamPaperBo bo) {

        ExamRecordVo recordVo = recordMapper.selectVoById(bo.getRecordId());
        if (ObjUtil.isEmpty(recordVo)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "考试记录不存在!");
        }

        // 只能作答当前登录用户的试卷
        Long loginId = LoginHelper.getUserId();
        if (!ObjUtil.equal(recordVo.getUserId(), loginId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 判断是否超时作答
        ExamSendVo sendVo = sendMapper.selectVoById(recordVo.getSendId());
        if (sendVo != null && sendVo.getStatus() > ExamStatusConstants.STATUS_IN_PROGRESS) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "考试已结束!");
        }

        // TODO: 判断是否在允许的考试时间内

        // 设置考试记录状态为 "待批改"、提交时间
        ExamRecord record = ExamRecord.builder().id(bo.getRecordId())
            .recordStatus(STATUS_PENDING_REVIEW)
            .submitTime(new Date())
            .build();

        List<ExamPaperTitleBo> paperTitleList = bo.getPaperTitleList();

        // 提取所有题目，去除未作答的题目
        Map<Long, QuestionBo> questionMap = new HashMap<>();
        for (ExamPaperTitleBo paperTitle : paperTitleList) {
            List<QuestionBo> questionList = paperTitle.getQuestionList();
            Map<Long, QuestionBo> map = questionList.stream()
                .filter(q -> StrUtil.isNotBlank(q.getAnswer()))
                .collect(Collectors.toMap(QuestionBo::getId, Function.identity()));
            questionMap.putAll(map);
        }

        Set<Long> questionIds = questionMap.keySet();

        //  一题都没写, 设置考试状态为 "已完结"
        if (questionIds.isEmpty()) {
            record.setRecordStatus(STATUS_COMPLETED);
            recordMapper.updateById(record);
            return true;
        }

        // 查询当前试卷的所有答案（ Map<questionId, answer>）
        Map<Long, String> answerMap = questionMapper.selectList(
            Wrappers.<Question>lambdaQuery()
                .select(Question::getId, Question::getAnswer)
                .in(Question::getId, questionIds)
        ).stream().collect(Collectors.toMap(Question::getId, Question::getAnswer));

        // 生成答题详情
        List<AnswerDetail> detailList = questionIds.stream().map(questionId -> {
            QuestionBo question = questionMap.get(questionId);

            // 系统判分 （选择、多选、判断；部分填空、简答）
            int isCorrect = isCorrectAnswer(answerMap.get(questionId), question);
            // 计算该题得分
            BigDecimal score = isCorrect == ANSWER_RIGHT ? question.getScore() : BigDecimal.ZERO;

            // 构造答题详情
            assert sendVo != null;
            return AnswerDetail.builder()
                .questionId(questionId)
                .userAnswer(question.getAnswer())
                .paperId(bo.getId())
                .recordId(record.getId())
                .userId(loginId)
                .examSendId(sendVo.getId())
                .okFlag(isCorrect)
                .score(score)
                .build();
        }).collect(Collectors.toList());

        // 计算考试总分
        BigDecimal totalScore = detailList.stream()
            .map(AnswerDetail::getScore)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        record.setScore(totalScore);


        //  若系统已全部判分，无需批改；设置考试记录状态为 "已完结"
        List<AnswerDetail> list = detailList.stream()
            .filter(detail -> detail.getOkFlag() == STATUS_PENDING_REVIEW).collect(Collectors.toList());
        if (list.isEmpty()) {
            record.setRecordStatus(STATUS_COMPLETED);
            record.setReviewerUserId(DEFAULT_REVIEWER);
        }

        // 更新考试记录
        recordMapper.updateById(record);

        // 插入答题详情
        return answerDetailMapper.insertBatch(detailList);
    }

    /**
     * 提交批改
     */
    @Override
    @Transactional
    public boolean submitReviewPaper(ExamRecordBo bo) {
        ExamRecordVo recordVo = recordMapper.selectVoById(bo.getId());
        if (recordVo == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "考试记录不存在");
        }

        // 获取答题批改详情
        List<AnswerDetailBo> detailList = bo.getAnswerDetailList();
        if (detailList.isEmpty()) {
            return true;
        }

        // 合计分数
        BigDecimal add = detailList.stream().map(AnswerDetailBo::getScore)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 更新考试记录
        ExamRecord record = ExamRecord.builder()
            .id(bo.getId())
            .score(add.add(recordVo.getScore()))
            // 设置状态为已完成
            .recordStatus(STATUS_COMPLETED)
            // 设置批改人 ID
            .reviewerUserId(LoginHelper.getUserId())
            .reviewerTime(new Date()).build();
        recordMapper.updateById(record);

        // 更新答题记录
        List<AnswerDetail> answerDetails = detailList.stream()
            .map(detail -> AnswerDetail.builder()
                .id(detail.getId())
                .score(detail.getScore())
                .okFlag(detail.getOkFlag()).build()).collect(Collectors.toList());
        answerDetailMapper.updateBatchById(answerDetails);

        return true;
    }


    /**
     * 判断答题是否正确，设置状态
     *
     * @param answer   正确答案
     * @param question 用户作答信息
     * @return 0：错误，1：正确，2：待批改
     */
    private int isCorrectAnswer(String answer, QuestionBo question) {
        // 待批改
        if (ObjUtil.hasEmpty(question, question.getAnswer())) {
            return ANSWER_WILL_REVIEWER;
        }
        int code = QuestionTypeEnum.fromCode(question.getQuestionTypeCode()).getCode();

        // 单选题、判断题、其他等值类型题目
        if (isSingleAnswer(code)) {
            return isSingleAnswerCorrect(answer, question.getAnswer()) ? ANSWER_RIGHT : ANSWER_ERROR;
        }

        // 判断多选题
        if (code == QuestionTypeConstants.MULTIPLE) {
            // 部分答对由人工批改
            return isMultipleAnswersCorrect(answer, question.getAnswer()) ? ANSWER_RIGHT : ANSWER_WILL_REVIEWER;
        }

        // 填空题和简答题，返回：正确 / 待批改
        if (code == QuestionTypeConstants.FILL_BLANK || isShortAnswer(code)) {
            return isMultipleAnswersCorrect(answer, question.getAnswer()) ? ANSWER_RIGHT : ANSWER_WILL_REVIEWER;
        }

        // 其他题型默认待批改
        return ANSWER_WILL_REVIEWER;
    }

    /**
     * 判断是否为单选题、判断题
     */
    private boolean isSingleAnswer(int code) {
        return code == QuestionTypeConstants.RADIO || code == QuestionTypeConstants.TRUE_FALSE;
    }

    /**
     * 判断是否为简答题
     */
    private boolean isShortAnswer(int code) {
        return code == QuestionTypeConstants.SHORT_ANSWER;
    }

    /**
     * 判断答案是否正确
     *
     */
    private boolean isSingleAnswerCorrect(String userAnswer, String correctAnswer) {
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }

    /**
     * 判断多选题的答案是否正确
     */
    private boolean isMultipleAnswersCorrect(String userAnswer, String correctAnswer) {
        Set<String> correctAnswers = new HashSet<>(Arrays.asList(correctAnswer.split(ANSWER_SEPARATOR)));
        Set<String> userAnswers = new HashSet<>(Arrays.asList(userAnswer.split(ANSWER_SEPARATOR)));
        return correctAnswers.equals(userAnswers);
    }

}
