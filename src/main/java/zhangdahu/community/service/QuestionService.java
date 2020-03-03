package zhangdahu.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.mapper.UserMapper;
import zhangdahu.community.model.Question;
import zhangdahu.community.model.QuestionExample;
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
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        PaginationDto paginationDto = new PaginationDto(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);
        List<Question> questions =questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question: questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(question,dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        paginationDto.setQuestions(questionDtoList);
        return  paginationDto;
    }

    public PaginationDto listByid(Integer userId, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(example);
        PaginationDto paginationDto = new PaginationDto(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions =questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,
                new RowBounds(offset,size));
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question: questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(question,dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        paginationDto.setQuestions(questionDtoList);
        return  paginationDto;
    }

    public QuestionDto getDtoById(Integer id) {
        Question question =questionMapper.selectByPrimaryKey(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public Question getById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            //创建
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTitle(question.getTitle());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExample(updateQuestion, example);
        }
    }
}
