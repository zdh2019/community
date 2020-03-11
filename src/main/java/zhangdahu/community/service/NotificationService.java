package zhangdahu.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangdahu.community.dto.NotificationDto;
import zhangdahu.community.dto.PaginationDto;
import zhangdahu.community.dto.QuestionDto;
import zhangdahu.community.enums.NotificationEnum;
import zhangdahu.community.enums.NotificationStatusEnum;
import zhangdahu.community.exception.CustomizeErrorCode;
import zhangdahu.community.exception.CustomizeException;
import zhangdahu.community.mapper.CommentMapper;
import zhangdahu.community.mapper.NotificationMapper;
import zhangdahu.community.mapper.QuestionMapper;
import zhangdahu.community.mapper.UserMapper;
import zhangdahu.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    public PaginationDto list(Long userId, Integer page, Integer size) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount=(int)notificationMapper.countByExample(example);
        PaginationDto<NotificationDto> paginationDto = new PaginationDto<NotificationDto>(totalCount,page,size);
        Integer offset=size*(paginationDto.getCurrentPage()-1);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notifications =notificationMapper.selectByExample(notificationExample);
        if(notifications.size()==0)
        {
            return paginationDto;
        }
        Set<Long> Notifiers = notifications.stream().map(notfiy -> notfiy.getNotifier()).collect(Collectors.toSet());
        Set<Long> Questions = notifications.stream().filter(x->x.getType()==NotificationEnum.REPLY_QUESTION.getType()).map(t -> t.getOuterid()).collect(Collectors.toSet());
        Set<Long> Comments = notifications.stream().filter(x->x.getType()==NotificationEnum.REPLY_COMMENT.getType()).map(t -> t.getOuterid()).collect(Collectors.toSet());
        ArrayList<Long> questionIds=new ArrayList<>(Questions);
        ArrayList<Long> userIds = new ArrayList<>(Notifiers);
        ArrayList<Long> commentIds = new ArrayList<>(Comments);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdIn(questionIds);
        List<Question> questions=questionMapper.selectByExample(questionExample);
        Map<Long, Question> questionMap = questions.stream().collect(Collectors.toMap(c->c.getId(),c->c));
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdIn(commentIds);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        Map<Long, Comment> commentMap = comments.stream().collect(Collectors.toMap(c->c.getId(),c->c));
        List<NotificationDto> notificationDtos=new ArrayList<>();
        int skipcount=0;
        int count=0;
        for (Notification notification:notifications) {
            if(count==size)
            {
                break;
            }
            if(skipcount<offset)
            {
                skipcount++;
                continue;
            }
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setId(notification.getId());
            notificationDto.setNotifierId(notification.getNotifier());
            notificationDto.setGmtCreate(notification.getGmtCreate());
            User user=userMap.get(notification.getNotifier());
            notificationDto.setNotifierName(user.getName());
            notificationDto.setStatus(notification.getStatus());
            notificationDto.setType(NotificationEnum.valueOf(notification.getType()));
            if(notification.getType()==NotificationEnum.REPLY_COMMENT.getType())
            {
                Comment comment = commentMap.get(notification.getOuterid());
                notificationDto.setValue(comment.getContent());
                Question parentQuesion=questionMapper.selectByPrimaryKey(comment.getParentId());
                if(parentQuesion==null)
                {
                    throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
                notificationDto.setReferentId(parentQuesion.getId());
            }else if(notification.getType()==NotificationEnum.REPLY_QUESTION.getType()) {
                Question question=questionMap.get(notification.getOuterid());
                notificationDto.setValue(question.getTitle());
                notificationDto.setReferentId(question.getId());
            }
            notificationDtos.add(notificationDto);
            count++;
        }
        paginationDto.setDatas(notificationDtos);
        return  paginationDto;
    }

    public long unreadCount(Long userId)
    {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public Notification read(Long id, Long parentId, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null)
        {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NO_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId()))
        {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        Question question = questionMapper.selectByPrimaryKey(parentId);
        if(question==null)
        {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        return notification;
    }
}
