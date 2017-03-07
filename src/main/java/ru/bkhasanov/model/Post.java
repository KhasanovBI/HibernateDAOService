package ru.bkhasanov.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by bulat on 07.03.17.
 */
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Column(name = "create_datetime", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @Column(name = "text", nullable = false)
    private String text;

    /**
     * for Hibernate only
     */
    @Deprecated
    Post() {
    }

    public Post(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Post otherPost = (Post) obj;
        return Objects.equals(id, otherPost.id)
                && Objects.equals(createDatetime, otherPost.createDatetime)
                && Objects.equals(text, otherPost.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(text);
        result = 31 * result + Objects.hashCode(createDatetime);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Post{id=%d, createDatetime=%s, text='%s'}",
                id, createDatetime, text);
    }

    public Integer getId() {
        return id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
