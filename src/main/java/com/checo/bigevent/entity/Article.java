package com.checo.bigevent.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 主键ID

    @NotEmpty
    @Size(min = 1, max = 20, message = "文章标题字数应该 1-20 位")
    private String title; // 文章标题

    @NotEmpty
    private String content; // 文章内容

    @URL
    private String coverImg; // 封面图像

    @Pattern(regexp = "^(已发布|草稿)$", message = "字段值只能是“已发布”或“草稿”")
    private String state; // 发布状态 已发布|草稿

    @NotNull
    private Integer categoryId; // 文章分类 id

    @TableField(fill = FieldFill.INSERT)
    private Integer createUser; // 创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; // 更新时间
}
