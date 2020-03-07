package zhangdahu.community.mapper;

import zhangdahu.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
}
