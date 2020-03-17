package zhangdahu.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import zhangdahu.community.dto.CommentDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.enums.CommentTypeEnum;
import zhangdahu.community.model.Question;
import zhangdahu.community.service.CommentService;
import zhangdahu.community.service.QuestionService;
import zhangdahu.community.model.User;
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
			   HttpServletRequest request){
        QuestionDto questionDto = questionService.getDtoById(id);
        List<CommentDto> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<Question> relatedQuestions=questionService.selectRelated(questionDto);
        User user=(User)request.getSession().getAttribute("user");
	if(user!=null){
	   if(!questionDto.getCreator().equals(user.getId())){
		questionService.incView(id);
	   }      
	}
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedquestions",relatedQuestions);
        return "question";
    }
}
