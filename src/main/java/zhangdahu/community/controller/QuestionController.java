package zhangdahu.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import zhangdahu.community.dto.CommentCreateDto;
import zhangdahu.community.dto.CommentDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.service.CommentService;
import zhangdahu.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name="id")Long id,
                           Model model){
        QuestionDto questionDto = questionService.getDtoById(id);
        List<CommentDto> comments=commentService.listByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }
}
