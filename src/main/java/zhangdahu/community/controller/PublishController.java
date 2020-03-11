package zhangdahu.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhangdahu.community.cache.TagCache;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.User;
import zhangdahu.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("taglist", TagCache.getTags());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    private String edit(@PathVariable(name="id") Long id,
                        Model model)
    {
        Question question=questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("taglist", TagCache.getTags());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false)  Long id,
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
        String invalid=TagCache.filterInvalid(tag);
        if(StringUtils.isBlank(invalid))
        {
            return "publish";
        }
        User user=(User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登入哦");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(invalid);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
