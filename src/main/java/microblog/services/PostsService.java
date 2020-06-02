package microblog.services;

import lombok.extern.slf4j.Slf4j;
import microblog.dto.PostUpdate;
import microblog.exceptions.PostNotFound;
import microblog.exceptions.UnknownQueryParam;
import microblog.repositories.PostsRepository;
import microblog.repositories.models.PostEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostsService {

    private PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public PostEntity savePost(PostEntity postEntity) {
        log.info("About to save {}", postEntity);
        return postsRepository.save(postEntity);
    }

    public List<PostEntity> findBy(Map<String, String> queryParams) {
        if (queryParams.get("userId") != null) {
            return getPostsByUserId(queryParams.get("userId"));
        }
        if (queryParams.get("postId") != null) {
            return Collections.singletonList(getPostByPostId(queryParams.get("postId")));
        }
        if (queryParams.isEmpty()) {
            return postsRepository.findAll();
        }
        throw new UnknownQueryParam(String.format("Unable to retrieve posts by these query params '%s'", queryParams));
    }

    public PostEntity updatePost(String postId, PostUpdate postUpdate) {
        PostEntity postToUpdate = getPostByPostId(postId);
        if (postToUpdate != null) {
            postToUpdate.setBody(postUpdate.getBody());
            return savePost(postToUpdate);
        } else {
            throw new PostNotFound(String.format("Unable to update post with post ID '%s' because it is not found", postId));
        }
    }

    public void deleteById(String postId) {
        if (this.postsRepository.deleteByPostId(postId) == 0) {
            throw new PostNotFound(String.format("Unable to delete post with post ID '%s' because it is not found", postId));
        }
    }

    private PostEntity getPostByPostId(String postId) {
        return postsRepository.findByPostId(postId);
    }

    private List<PostEntity> getPostsByUserId(String userId) {
        return postsRepository.findByUserId(userId);
    }


}
