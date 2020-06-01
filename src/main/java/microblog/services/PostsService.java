package microblog.services;

import microblog.repositories.PostsRepository;
import microblog.repositories.models.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostsService {
//    @Autowired
    private  PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {this.postsRepository = postsRepository;}

    public PostEntity savePost(PostEntity postEntity) {
//        return postEntity;
        return postsRepository.save(postEntity);
    }

    public List<PostEntity> findAll() {
        return new ArrayList<>();
//        return postsRepository.findAll();
    }
}
