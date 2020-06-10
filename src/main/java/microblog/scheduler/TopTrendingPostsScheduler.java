package microblog.scheduler;

import microblog.repositories.PostsRepository;
import microblog.scheduler.score.TopTrendingScoreCalculator;
import microblog.repositories.models.PostEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopTrendingPostsScheduler {

    private TopTrendingScoreCalculator topTrendingScoreCalculator;
    private PostsRepository postsRepository;

    public TopTrendingPostsScheduler(TopTrendingScoreCalculator topTrendingScoreCalculator, PostsRepository postsRepository) {
        this.topTrendingScoreCalculator = topTrendingScoreCalculator;
        this.postsRepository = postsRepository;
    }

    @Scheduled(fixedDelay = 120000)
    public void calculateTopTrending() {
        List<PostEntity> allPosts = getAllPosts();
        long curDateInMillis = new Date().getTime();
        List<PostEntity> updatedPosts = getUpdatedPostsWithTrendingScore(allPosts, curDateInMillis);
        saveUpdatedPostsWithTrendingScore(updatedPosts);
    }

    private List<PostEntity> getUpdatedPostsWithTrendingScore(List<PostEntity> allPosts, long curDateInMillis) {
        return allPosts.stream()
                .map(post -> calculateTrendingScoreForPost(curDateInMillis, post))
                .sorted(sortByTrendingScore())
                .collect(Collectors.toList());
    }

    private void saveUpdatedPostsWithTrendingScore(List<PostEntity> updatedPosts) {
        postsRepository.saveAll(updatedPosts);
    }

    private List<PostEntity> getAllPosts() {
        return postsRepository.findAll();
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
