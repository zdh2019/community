package zhangdahu.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import zhangdahu.community.dto.ResultDto;
import zhangdahu.community.exception.CustomizeErrorCode;
import zhangdahu.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex,
                                     Model model,
                                     HttpServletResponse response) {
        String contentType=request.getContentType();
        if("application/json"==contentType){
            ResultDto resultDto=null;
            if(ex instanceof CustomizeException)
                resultDto= ResultDto.errorOf((CustomizeException) ex);
            else
                resultDto=  ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            try{
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            }catch (IOException ioe) {
            }
            return null;
        }else{
            if(ex instanceof CustomizeException) {
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
    private HttpStatus getStatus (HttpServletRequest request) {
        Integer statusCode = (Integer) request . getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR ;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
