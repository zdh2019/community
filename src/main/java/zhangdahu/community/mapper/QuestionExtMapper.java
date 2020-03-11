package zhangdahu.community.mapper;

import zhangdahu.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}
