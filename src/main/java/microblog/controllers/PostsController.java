package microblog.controllers;

import lombok.extern.slf4j.Slf4j;
import microblog.dto.PostRequest;
import microblog.dto.PostUpdate;
import microblog.exceptions.PostNotFoundException;
import microblog.exceptions.UnknownQueryParamException;
import microblog.exceptions.UpdateFailureException;
import microblog.repositories.models.PostEntity;
import microblog.services.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @PostMapping
    public ResponseEntity addPost(@RequestBody PostRequest postRequest) {
        log.info("Got request {}", postRequest);
        PostEntity postEntity = postsService.savePost(postRequest.toPostEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(postEntity);
    }

    @GetMapping
    public List<PostEntity> getPosts(@RequestParam Map<String, String> queryParams) {
        log.info("About to retrieve posts by: {}", queryParams);
        return postsService.findBy(queryParams);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostEntity> updatePost(@PathVariable String postId, @RequestBody PostUpdate postUpdate){
        PostEntity updatedPostEntity = postsService.updatePost(postId, postUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPostEntity);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        postsService.deleteById(postId);
        log.info("Post with id {} was deleted", postId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<PostEntity> likePost(@PathVariable String postId){
        PostEntity updatedPostEntity = postsService.like(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPostEntity);
    }

    //like
    //getTopTrendingPosts
    //update
    //delete
    //error handling


    @ExceptionHandler({UnknownQueryParamException.class, PostNotFoundException.class})
    public void handleBadRequest(RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UpdateFailureException.class)
    public void handleUnknownUpdateCriteria(RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
}
