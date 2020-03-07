package zhangdahu.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhangdahu.community.dto.CommentCreateDto;
import zhangdahu.community.dto.ResultDto;
import zhangdahu.community.exception.CustomizeErrorCode;
import zhangdahu.community.model.Comment;
import zhangdahu.community.model.User;
import zhangdahu.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request)
    {
        User user= (User)request.getSession().getAttribute("user");
        if(user==null)
        {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDto==null|| StringUtils.isBlank(commentCreateDto.getContent()))
        {
            return ResultDto.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment =new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDto.ok();
    }
}
