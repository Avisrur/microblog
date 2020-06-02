package microblog.services;

import lombok.extern.slf4j.Slf4j;
import microblog.dto.PostUpdate;
import microblog.exceptions.PostNotFoundException;
import microblog.exceptions.UnknownQueryParamException;
import microblog.exceptions.UpdateFailureException;
import microblog.handlers.TopTrendingPostsHandler;
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
    private TopTrendingPostsHandler topTrendingPostsHandler;

    public PostsService(PostsRepository postsRepository, TopTrendingPostsHandler topTrendingPostsHandler) {
        this.postsRepository = postsRepository;
        this.topTrendingPostsHandler = topTrendingPostsHandler;
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
        throw new UnknownQueryParamException(String.format("Unable to retrieve posts by these query params '%s'", queryParams));
    }

    public PostEntity like(String postId) {
        return updatePostBy("like", postId, null);
    }

    public PostEntity updatePost(String postId, PostUpdate newPost) {
        return updatePostBy("newText", postId, newPost);
    }

    public List<PostEntity> getTopTrendingPosts() {
        List<PostEntity> allPosts = postsRepository.findAll();
//        log.info("BEFORE SORTING: {}",allPosts);
        topTrendingPostsHandler.sortByTopTrending(allPosts);
//        log.info("AFTER SORTING: {}",allPosts);
        return allPosts;
    }

    public void deleteById(String postId) {
        if (this.postsRepository.deleteByPostId(postId) == 0) {
            throw new PostNotFoundException(String.format("Unable to delete post with post ID '%s' because it is not found", postId));
        }
    }

    private PostEntity updatePostBy(String updateBy, String postId, PostUpdate newPost) {
        PostEntity postToUpdate = getPostByPostId(postId);
        if (postToUpdate != null) {
            return handleUpdatePost(updateBy, postToUpdate, newPost);
        } else {
            throw new PostNotFoundException(String.format("Unable to update post with post ID '%s' because it is not found", postId));
        }
    }

    private PostEntity handleUpdatePost(String updateBy, PostEntity postToUpdate, PostUpdate newPost) {
        if (updateBy.equals("like")) {
            return updatePostLikes(postToUpdate);
        }
        if (updateBy.equals("newText")) {
            return updatePostWithNewText(postToUpdate, newPost);
        }
        throw new UpdateFailureException(String.format("Unable to update post because of unknown criteria '%s'", updateBy));
    }

    private PostEntity updatePostLikes(PostEntity postToUpdate) {
        postToUpdate.setLikes(postToUpdate.getLikes() + 1);
        return savePost(postToUpdate);
    }

    private PostEntity updatePostWithNewText(PostEntity postToUpdate, PostUpdate newPost) {
        postToUpdate.setBody(newPost.getBody());
        return savePost(postToUpdate);
    }

    private PostEntity getPostByPostId(String postId) {
        return postsRepository.findByPostId(postId);
    }

    private List<PostEntity> getPostsByUserId(String userId) {
        return postsRepository.findByUserId(userId);
    }
}
