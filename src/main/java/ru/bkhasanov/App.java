package ru.bkhasanov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bkhasanov.di.SpringConfig;
import ru.bkhasanov.model.Comment;
import ru.bkhasanov.model.Post;
import ru.bkhasanov.service.CommentService;
import ru.bkhasanov.service.PostService;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static final int COMMENTS_COUNT = 10;
    public static void main(String[] args) {
        final ApplicationContext applicationContext = createApplicationContext();
        PostService postService = applicationContext.getBean(PostService.class);
        CommentService commentService = applicationContext.getBean(CommentService.class);
        run(postService, commentService);
    }

    private static ApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(SpringConfig.class);
    }
    
    private static void run(PostService postService, CommentService commentService) {
        Post post = new Post("Post");
        postService.save(post);

        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < COMMENTS_COUNT; ++i) {
            Comment comment = new Comment("comment " + i, post.getId());
            commentList.add(comment);
            commentService.save(comment);
        }

        post.setText("Updated");
        postService.update(post);

        int postIdToDelete = post.getId();
        postService.delete(postIdToDelete);

        System.out.println("Is post exist: " + postService.get(postIdToDelete).isPresent());
        System.out.println("Is comment exist: " + commentService.get(commentList.get(5).getId()).isPresent());
    }
}
