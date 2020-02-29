package zhangdahu.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.mapper.UserMapper;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size)
    {
        Integer totalCount=questionMapper.Count();
        PaginationDto paginationDto = new PaginationDto(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);
        List<Question> questions =questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question: questions) {
            User user=userMapper.finderById(question.getCreator());
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(question,dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        paginationDto.setQuestions(questionDtoList);
        return  paginationDto;
    }

    public PaginationDto listByid(Integer id, Integer page, Integer size) {
        Integer totalCount=questionMapper.CountByid(id);
        PaginationDto paginationDto = new PaginationDto(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);
        List<Question> questions =questionMapper.listByid(id,offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question: questions) {
            User user=userMapper.finderById(question.getCreator());
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(question,dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        paginationDto.setQuestions(questionDtoList);
        return  paginationDto;
    }
}
