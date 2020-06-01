package microblog.repositories;

import microblog.repositories.models.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<PostEntity, String> {
}
