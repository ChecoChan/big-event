package com.checo.bigevent.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 主键ID

    @NotEmpty
    private String username; // 用户名

    @JsonIgnore
    private String password; // 密码

    @NotEmpty
    @Size(min = 1, max = 10, message = "昵称应该 1-10 位")
    private String nickname; // 昵称

    @NotEmpty
    @Email
    private String email; // 邮箱

    @URL
    private String userPic; // 用户头像地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; // 更新时间
}
