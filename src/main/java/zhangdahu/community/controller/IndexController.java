package zhangdahu.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.mapper.UserMapper;
import zhangdahu.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page",defaultValue ="1")Integer page,
                        @RequestParam(name="size",defaultValue = "5")Integer size) {
        PaginationDto Pagination = questionService.list(page,size);
        model.addAttribute("pagination",Pagination);
        return "index";
    }
}
