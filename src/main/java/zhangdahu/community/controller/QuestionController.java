package zhangdahu.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name="id")Integer id,
                           Model model){
        QuestionDto questionDto = questionService.getDtoById(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
