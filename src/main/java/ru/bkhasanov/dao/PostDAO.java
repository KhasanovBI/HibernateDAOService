package ru.bkhasanov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.bkhasanov.model.Post;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by bulat on 07.03.17.
 */
public class PostDAO {
    @Inject
    private SessionFactory sessionFactory;

    public void save(Post post) {
        if (post.getId() != null) {
            throw new IllegalArgumentException("can not save " + post + " with assigned id");
        }
        session().save(post);
    }

    public Optional<Post> get(int postId) {
        Post post = session().get(Post.class, postId);
        return Optional.ofNullable(post);
    }

    public Set<Post> getAll() {
        CriteriaQuery<Post> criteriaQuery = session().getCriteriaBuilder().createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        criteriaQuery.select(postRoot);
        List<Post> posts = session().createQuery(criteriaQuery).getResultList();
        return new HashSet<Post>(posts);
    }

    public void update(Post post) {
        session().update(post);
    }

    public boolean delete(int postId) {
        return session().createQuery("DELETE Post WHERE id = :id")
                .setParameter("id", postId)
                .executeUpdate() != 0;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
