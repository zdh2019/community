package zhangdahu.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zhangdahu.community.dto.NotificationDto;
import zhangdahu.community.mapper.NotificationMapper;
import zhangdahu.community.model.Notification;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.User;
import zhangdahu.community.service.NotificationService;
import zhangdahu.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification")
    public String profile(HttpServletRequest request,
                          @RequestParam(name = "id")Long id,
                          @RequestParam(name="parentId")Long parentId)
    {
        User user=(User)request.getSession().getAttribute("user");
        if(user==null)
        {
            return "redirect:/";
        }

        notificationService.read(id,parentId,user);
        return "redirect:/question/"+parentId;
    }
}
