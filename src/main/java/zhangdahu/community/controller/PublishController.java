package zhangdahu.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = true) String title,
                            @RequestParam(value = "description",required = true) String description,
                            @RequestParam(value = "tag",required = true) String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title=="")
        {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description=="")
        {
            model.addAttribute("error","问题补充不能未空");
            return "publish";
        }
        if(tag==null||tag=="")
        {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user=(User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登入哦");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
