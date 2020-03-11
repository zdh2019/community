package zhangdahu.community.dto;


import lombok.Data;
import zhangdahu.community.exception.CustomizeErrorCode;
import zhangdahu.community.exception.CustomizeException;

@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDto errorOf(Integer code,String message)
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }
    public static ResultDto errorOf(CustomizeErrorCode errorCode)
    {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDto ok()
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    public static <T>ResultDto ok(T t)
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }


    public static ResultDto errorOf(CustomizeException ex) {
        return  errorOf(ex.getCode(),ex.getMessage());
    }
}
