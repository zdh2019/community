package zhangdahu.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhangdahu.community.dto.FileDto;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload()
    {
        FileDto dto=new FileDto();
        dto.setSuccess(1);
        dto.setUrl("/img/huaji.jpg");
        return dto;
    }
}
