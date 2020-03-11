package zhangdahu.community.dto;

import lombok.Data;
import zhangdahu.community.model.User;
@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifierId;
    private String notifierName;
    private Long referentId;
    private String type;
    private String value;
}
