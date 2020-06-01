package microblog.controllers;

import lombok.extern.slf4j.Slf4j;
import microblog.dto.PostRequest;
import microblog.repositories.models.PostEntity;
import microblog.services.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @PostMapping("/posts")
    public ResponseEntity addPost(@RequestBody PostRequest postRequest) {
        log.info("Got request {}", postRequest);
        postsService.savePost(postRequest.toPostEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public List<PostEntity> getAllPosts() {
        return postsService.findAll();
    }
}
