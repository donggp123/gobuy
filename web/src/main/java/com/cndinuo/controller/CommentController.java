package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.MberComment;
import com.cndinuo.domain.MberCommentReply;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberCommentReplyService;
import com.cndinuo.service.MberCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private MberCommentService commentService;
    @Autowired
    private MberCommentReplyService commentReplyService;
    @RequestMapping("list")
    public String list(@RequestParam Map params, Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<MberComment> page = commentService.getByPage(params);
        model.addAttribute("page", page);
        model.addAttribute("user", user);
        return "comment/list";
    }

    @RequestMapping("view")
    public String view(Integer id,Integer mrhtId,Model model){
        model.addAttribute("id", id);
        model.addAttribute("mrhtId", mrhtId);
        return "comment/view";
    }

    @RequestMapping("save")
    public @ResponseBody RespData save(MberCommentReply commentReply){
        return commentReplyService.save(commentReply);
    }

    @RequestMapping("read")
    public String read(@RequestParam Map params,Model model){
        List<MberCommentReply> replyList = commentReplyService.getByMap(params);
        model.addAttribute("reply", replyList);
        return "comment/read";
    }
}
