package zhangdahu.community.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    public String content;
    public Long parentId;
    public Integer type;
}
