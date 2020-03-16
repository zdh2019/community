package zhangdahu.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了"),
    TARGET_PARAM_NOT_FOUND(2002,"未找到问题或评论"),
    NO_LOGIN(2003,"未登入,请登入后重试"),
    SYS_ERROR(2004,"服务器冒烟了"),
    TYPE_PARAM_WORNG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"评论未找到,换个评论试试"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    NOTIFICATION_NO_FOUND(2008,"评论不存在"),
    READ_NOTIFICATION_FAIL(2009,"读取评论出错，请重试"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？");

    @Override
    public String  getMessage()
    {
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
}
