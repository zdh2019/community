package zhangdahu.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import zhangdahu.community.dto.CommentDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.enums.CommentTypeEnum;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.User;
import zhangdahu.community.service.CommentService;
import zhangdahu.community.service.QuestionService;
<<<<<<< HEAD
import zhangdahu.community.model.User;
=======

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
>>>>>>> e074fcb6ff5274c1d9e78c38709bf255c5e3337f
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name="id")Long id,
                           Model model,
<<<<<<< HEAD
			   HttpServletRequest request){
=======
                           HttpServletRequest request){
>>>>>>> e074fcb6ff5274c1d9e78c38709bf255c5e3337f
        QuestionDto questionDto = questionService.getDtoById(id);
        List<CommentDto> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<Question> relatedQuestions=questionService.selectRelated(questionDto);
        User user=(User)request.getSession().getAttribute("user");
<<<<<<< HEAD
	if(user!=null){
	   if(!questionDto.getCreator().equals(user.getId())){
		questionService.incView(id);
	   }      
	}
=======
        if(user!=null)
        {
            if(!questionDto.getCreator().equals(user.getId()))
            {
                questionService.incView(id);
            }
        }
>>>>>>> e074fcb6ff5274c1d9e78c38709bf255c5e3337f
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedquestions",relatedQuestions);
        return "question";
    }
}
