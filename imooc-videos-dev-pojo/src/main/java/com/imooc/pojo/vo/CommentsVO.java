package com.imooc.pojo.vo;

import com.mysql.fabric.xmlrpc.base.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class CommentsVO {
    private String id;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    private Date createTime;

    /**
     * 评论内容
     */
    private String comment;
    
    private String faceImage;
    private String nickname;
    private String toNickname;
    private String timeAgoStr;
    

    }