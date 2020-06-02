package microblog.services;

import lombok.extern.slf4j.Slf4j;
import microblog.exceptions.UnknownQueryParam;
import microblog.repositories.PostsRepository;
import microblog.repositories.models.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostsService {

    private  PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {this.postsRepository = postsRepository;}

    public PostEntity savePost(PostEntity postEntity) {
        log.info("About to save {}",postEntity);
        return postsRepository.save(postEntity);
    }

    public List<PostEntity> findBy(Map<String, String> queryParams) {
        log.info("About to retrieve posts by: {}", queryParams);
        if (queryParams.get("userId") != null) {
            return getPostsByUsedId(queryParams.get("userId"));
        }
        if (queryParams.get("postId") != null) {
            return getPostByPostId(queryParams.get("postId"));
        }
        if(queryParams.isEmpty()){
            return postsRepository.findAll();
        }
        throw new UnknownQueryParam(String.format("Unable to retrieve posts by these query params '%s'", queryParams));
    }

    private List<PostEntity> getPostByPostId(String postId) {
        return postsRepository.findByPostId(postId);
    }

    private List<PostEntity> getPostsByUsedId(String userId) {
        return postsRepository.findByUserId(userId);
    }
}
