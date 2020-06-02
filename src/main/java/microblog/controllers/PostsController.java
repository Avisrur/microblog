package microblog.controllers;

import lombok.extern.slf4j.Slf4j;
import microblog.dto.PostRequest;
import microblog.exceptions.UnknownQueryParam;
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
        postsService.savePost(postRequest.toPostEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<PostEntity> getPosts(@RequestParam Map<String, String> queryParams) {
        return postsService.findBy(queryParams);
    }

    //like
    //getTopTrendingPosts
    //update
    //delete
    //error handling


    @ExceptionHandler(UnknownQueryParam.class)
    public void handleUnknownQueryParams(RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
}
