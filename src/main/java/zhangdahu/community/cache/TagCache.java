package zhangdahu.community.cache;

import org.apache.commons.lang3.StringUtils;
import zhangdahu.community.dto.TagDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> getTags()
    {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program=new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","node","python","java","C#","C","C++"
                        ,"golang","objective-c","typescript","shell"));
        tagDtos.add(program);
        TagDto framework=new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring",".net","yii","koa","express","angular","vue"));
        tagDtos.add(framework);
        TagDto server=new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("apache","tomcat","windows-server","docker","nginx","Linux"));
        tagDtos.add(server);
        TagDto database=new TagDto();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql","oracle","sqlserver","redis","nosql","sqlit"));
        tagDtos.add(database);
        TagDto tool=new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("IDE","Visual Studio","vs code"));
        tagDtos.add(tool);
        return tagDtos;
    }

    public static String filterInvalid(String tags)
    {
        String[] split= StringUtils.split(tags,",");
        List<TagDto> tagDtos=getTags();
        List<String> tagList=tagDtos.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
        String isValidList=Arrays.stream(split).filter(t->tagList.contains(t)).collect(Collectors.joining(","));
        return isValidList;
    }
}
