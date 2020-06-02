package microblog.repositories;

import microblog.repositories.models.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findByUserId(String userId);
    PostEntity findByPostId(String postId);

    int deleteByPostId(String postId);
}
