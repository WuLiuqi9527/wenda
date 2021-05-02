package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hc
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                vo.set("user", userService.getUser(targetId));
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }
            User user = userService.selectByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message msg = new Message();
            msg.setCreatedDate(new Date());
            msg.setFromId(hostHolder.getUser().getId());
            msg.setToId(user.getId());
            msg.setContent(content);
            messageService.addMessage(msg);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "发信失败");
        }
    }


    @RequestMapping(path = {"/msg/jsonAddMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message msg = new Message();
            msg.setContent(content);
            msg.setFromId(fromId);
            msg.setToId(toId);
            msg.setCreatedDate(new Date());
            messageService.addMessage(msg);
            return WendaUtil.getJSONString(msg.getId());
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "插入评论失败");
        }
    }
}
