package microblog.handlers;

import microblog.handlers.score.TopTrendingScoreCalculator;
import microblog.repositories.models.PostEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopTrendingPostsHandler {

    private TopTrendingScoreCalculator topTrendingScoreCalculator;

    public TopTrendingPostsHandler(TopTrendingScoreCalculator topTrendingScoreCalculator) {
        this.topTrendingScoreCalculator = topTrendingScoreCalculator;
    }

    public List<PostEntity> calculateTopTrending(List<PostEntity> allPosts) {
        long curDateInMillis = new Date().getTime();
        return allPosts.stream()
                .map(post -> calculateTrendingScoreForPost(curDateInMillis, post))
                .sorted(sortByTrendingScore())
                .collect(Collectors.toList());
    }

    private PostEntity calculateTrendingScoreForPost(long curDateInMillis, PostEntity post) {
        post.setTrendingScore(getScore(curDateInMillis, post));
        return post;
    }

    private Comparator<PostEntity> sortByTrendingScore() {
        return Comparator.comparingDouble(PostEntity::getTrendingScore).reversed();
    }

    private double getScore(long curDateInMillis, PostEntity post) {
        long postTimeWindow = calculateTimeWindow(curDateInMillis, post.getCreatedDate().getTime());
        return topTrendingScoreCalculator.calculateScoreFrom(postTimeWindow, post.getLikes());
    }

    private long calculateTimeWindow(long curDateInMillis, long postDateInMillis) {
        return curDateInMillis - postDateInMillis;
    }
}
