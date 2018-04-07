package com.cndinuo.controller;


import com.cndinuo.domain.MberOpinion;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("opinion")
public class OpinionController {

    @Autowired
    private MberOpinionService opinionService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m) {
        Page<MberOpinion> page = opinionService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "opinion/list";
    }
}
