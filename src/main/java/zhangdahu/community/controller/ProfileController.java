package zhangdahu.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zhangdahu.community.dto.NotificationDto;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.model.Notification;
import zhangdahu.community.model.User;
import zhangdahu.community.service.NotificationService;
import zhangdahu.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue ="1")Integer page,
                          @RequestParam(name="size",defaultValue = "5")Integer size)
    {
        User user=(User)request.getSession().getAttribute("user");
        if(user==null)
        {
            return "redirect:/";
        }
        if("question".equals(action)) {
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");
            PaginationDto Pagination = questionService.listByid(user.getId(), page, size);
            model.addAttribute("pagination",Pagination);
        }else if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            PaginationDto Pagination = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination", Pagination);
        }
        return "profile";
    }
}
