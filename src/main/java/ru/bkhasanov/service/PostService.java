package ru.bkhasanov.service;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.bkhasanov.dao.PostDAO;
import ru.bkhasanov.model.Post;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by bulat on 07.03.17.
 */
public class PostService {
    @Inject
    private PostDAO postDAO;

    @Inject
    private CommentService commentService;

    @Inject
    private SessionFactory sessionFactory;

    public void save(Post post) {
        inTransaction(() -> postDAO.save(post));
    }

    public void update(Post post) {
        inTransaction(() -> postDAO.update(post));
    }

    public Optional<Post> get(int postId) {
        return inTransaction(() -> postDAO.get(postId));
    }

    public boolean delete(int id) {
        return inTransaction(() -> {
            commentService.deleteByPostId(id);
            return postDAO.delete(id);
        });
    }

    public Collection<Post> getAll() {
        return inTransaction(() -> postDAO.getAll());
    }

    private <T> T inTransaction(Supplier<T> supplier) {
        Optional<Transaction> transaction = beginTransaction();
        try {
            T result = supplier.get();
            transaction.ifPresent(Transaction::commit);
            return result;
        } catch (RuntimeException e) {
            transaction.ifPresent(Transaction::rollback);
            throw e;
        }
    }

    private void inTransaction(Runnable runnable) {
        inTransaction(() -> {
            runnable.run();
            return null;
        });
    }

    private Optional<Transaction> beginTransaction() {
        Transaction transaction = sessionFactory.getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
            return Optional.of(transaction);
        }
        return Optional.empty();
    }
}
