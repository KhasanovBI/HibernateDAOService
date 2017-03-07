package ru.bkhasanov.service;

import ru.bkhasanov.dao.CommentDAO;
import ru.bkhasanov.model.Comment;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by bulat on 07.03.17.
 */
public class CommentService {
    @Inject
    private CommentDAO commentDAO;

    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    public boolean delete(int commentId) {
        return commentDAO.delete(commentId);
    }

    public Optional<Comment> get(int id) {
        return commentDAO.get(id);
    }

    public int deleteByPostId(int postId) {
        return commentDAO.deleteByPostId(postId);
    }

    public Collection<Comment> getByPostId(int postId) {
        return commentDAO.getByPostId(postId);
    }
}
