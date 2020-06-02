package microblog.handlers;

import lombok.extern.slf4j.Slf4j;
import microblog.comparators.TopTrendingComparator;
import microblog.repositories.models.PostEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TopTrendingPostsHandler {

    private TopTrendingComparator topTrendingComparator;

    public TopTrendingPostsHandler(TopTrendingComparator topTrendingComparator) {
        this.topTrendingComparator = topTrendingComparator;
    }


    public void sortByTopTrending(List<PostEntity> allPosts){
        Collections.sort(allPosts,topTrendingComparator);
    }
}