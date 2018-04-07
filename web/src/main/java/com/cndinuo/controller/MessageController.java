package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.Message;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m){

        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("from", user.getId());
        params.put("to", user.getId());
        Page<Message> page = messageService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params",params);
        return "message/list";
    }

    @RequestMapping("view")
    public String view(Message message, Model m) {
        if (message.getId() != null && message.getId() > 0) {
            message.setStatus((byte) 1);
            messageService.updateById(message);
            message = messageService.getById(message.getId().intValue());
        }
        m.addAttribute("message", message);
        return "message/view";
    }

    @RequestMapping("count")
    public @ResponseBody RespData count(@RequestParam Map params) {
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("to", user.getId());
        int count = messageService.getCountByMap(params);
        return RespData.successMsg("", count);
    }
}
