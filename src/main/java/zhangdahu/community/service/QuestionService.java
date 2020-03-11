package zhangdahu.community.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.exception.CustomizeErrorCode;
import zhangdahu.community.exception.CustomizeException;
import zhangdahu.community.mapper.QuestionExtMapper;
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

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDto list(Integer page, Integer size)
    {
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<QuestionDto>(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions =questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question: questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(question,dto);
            dto.setUser(user);
            questionDtoList.add(dto);
        }
        paginationDto.setDatas(questionDtoList);
        return  paginationDto;
    }

    public PaginationDto listByid(Long userId, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(example);
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<QuestionDto>(totalCount,page,size);
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
        paginationDto.setDatas(questionDtoList);
        return  paginationDto;
    }

    public QuestionDto getDtoById(Long id) {
        Question question =questionMapper.selectByPrimaryKey(id);
        if(question==null) {
            throw  new CustomizeException("你找的问题不在了");
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public Question getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null) {
            throw  new CustomizeException("你找的问题不在了");
        }
        return question;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
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
            int updated=questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1)
            {
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> selectRelated(QuestionDto questionDto) {
        if(StringUtils.isBlank(questionDto.getTag())) {
            return new ArrayList<>();
        }
        String[] tags=StringUtils.split(questionDto.getTag(),",");
        Question question=new Question();
        question.setId(questionDto.getId());
        question.setTag(StringUtils.join(tags,'|'));
        return  questionExtMapper.selectRelated(question);
    }
}
