package ac.liverpool.forum.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 用户表(Users)表实体类
 *
 * @author makejava
 * @since 2025-03-21 18:31:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users  {
//用户ID
    private Long id;
//用户名（唯一）
    private String username;
//密码（加密存储）
    private String password;
//用户昵称
    private String nickname ;
//邮箱
    private String email;
//头像URL
    private String avatar;
//学校
    private String school;
//专业
    private String major;
//个人简介
    private String bio;
//注册时间
    private Date createdAt;
//软删除标记
    private Integer isDeleted;
    // 新增字段

    // 新增字段
    private String studentId;
    private String grade;
    private Boolean emailVerified;
    private String verificationCode;
    private LocalDateTime verificationCodeExpireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;



}

