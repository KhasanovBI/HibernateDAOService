package ru.bkhasanov.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bulat on 07.03.17.
 */
public class Comment {
    private Integer id;

    private Date createDatetime;

    private String text;

    private Integer postId;

    public Comment(int id, Date createDatetime, String text, int postId) {
        this.id= id;
        this.createDatetime = createDatetime;
        this.text = text;
        this.postId = postId;
    }

    public Comment(String text, int postId) {
        this.text = text;
        this.postId = postId;
    }

    @Override
    public String toString() {
        return String.format("Comment{id=%d, createDatetime=%s, text='%s', postId=%d}",
                id, createDatetime, text, postId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
