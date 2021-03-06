package site.jimblog.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户基本信息
 */
public class UserBasicInfo {

    @Getter @Setter private String nickname;//昵称
    @Getter @Setter private String realName;//真实姓名
    @Getter @Setter private String mail;//邮箱
    @Getter @Setter private String phone;//电话
    @Getter @Setter private Date createTime;
    @Getter @Setter private Date updateTime;

//    private static final String SQL_MAPPER_NS = "com.tortuousroad.user.entity.UserBasicInfoMapper";
//    public static final String SAVE_SQL_ID = SQL_MAPPER_NS + ".insert";

    public static final String SAVE_SQL_ID = "insertSelective";

}
