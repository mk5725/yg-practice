package com.ruoyi.exam.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkm
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
public class ExamClientController extends BaseController {

    @GetMapping("/test")
    public R<String> list() {

        return R.ok("测试成功");
    }
}
