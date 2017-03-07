package ru.bkhasanov.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.bkhasanov.model.Comment;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by bulat on 07.03.17.
 */
public class CommentDAO {
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM comment WHERE id = :id";
    private static final String UPDATE_QUERY = "UPDATE comment SET text = :text, post_id = :post_id WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM comment WHERE id = :id";
    private static final String SELECT_BY_POST_ID_QUERY = "SELECT * FROM comment WHERE post_id = :post_id";
    private static final String DELETE_BY_POST_ID_QUERY = "DELETE FROM comment WHERE post_id = :post_id";
    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (resultSet, rowNum) ->
            new Comment(
                    resultSet.getInt("id"),
                    resultSet.getTimestamp("create_datetime"),
                    resultSet.getString("text"),
                    resultSet.getInt("post_id")
            );
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Inject
    public CommentDAO(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("comment")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Comment comment) {
        if (comment.getId() != null) {
            throw new IllegalArgumentException("Can not insert " + comment + " with already assigned id");
        }

        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        Map<String, Object> params = new HashMap<>();
        params.put("text", comment.getText());
        params.put("post_id", comment.getPostId());
        params.put("create_datetime", currentTimestamp);
        int commentId = simpleJdbcInsert.executeAndReturnKey(params).intValue();

        comment.setId(commentId);
        comment.setCreateDatetime(currentTimestamp);
    }

    public Optional<Comment> get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        Comment comment;
        try {
            comment = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, params, COMMENT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
        return Optional.of(comment);
    }

    public boolean update(Comment comment) {
        if (comment.getId() == null) {
            throw new IllegalArgumentException(comment + " not specified. Nothing to update.");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", comment.getId());
        params.put("text", comment.getText());
        params.put("post_id", comment.getPostId());

        return namedParameterJdbcTemplate.update(UPDATE_QUERY, params) != 0;
    }

    public boolean delete(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.update(DELETE_QUERY, params) != 0;
    }


    public Collection<Comment> getByPostId(int postId) {

        Map<String, Object> params = new HashMap<>();
        params.put("post_id", postId);

        return new HashSet<>(namedParameterJdbcTemplate.query(SELECT_BY_POST_ID_QUERY, params, COMMENT_ROW_MAPPER));
    }

    public int deleteByPostId(int postId) {
        Map<String, Object> params = new HashMap<>();
        params.put("post_id", postId);
        return namedParameterJdbcTemplate.update(DELETE_BY_POST_ID_QUERY, params);
    }
}
