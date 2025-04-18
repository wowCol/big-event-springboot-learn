package org.finalwork.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data // lombok自动为值添加set，get，toString方法注解
public class User {
    @NonNull
    private Integer id;//主键ID

    private String username;//用户名

    @JsonIgnore
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱

    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
