package microblog.handlers.score;

import microblog.handlers.score.normalizers.LikesNormalizer;
import microblog.handlers.score.normalizers.TimeNormalizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopTrendingScoreCalculator {
    private final long timeWindowLimit;
    private LikesNormalizer likesNormalizer;
    private TimeNormalizer timeNormalizer;

    public TopTrendingScoreCalculator(@Value("${trending-formula.time.min}")long timeWindowLimit,
                                      LikesNormalizer likesNormalizer,
                                      TimeNormalizer timeNormalizer) {
        this.timeWindowLimit = timeWindowLimit;
        this.likesNormalizer = likesNormalizer;
        this.timeNormalizer = timeNormalizer;
    }

    public double calculateScoreFrom(long postTimeWindow, Integer likes) {
        if(postTimeWindow > timeWindowLimit)
            return calculateTrendingScoreWithoutTime(likes);
        return calculateTrendingScore(postTimeWindow,likes);
    }

    private double calculateTrendingScore(long postTimeWindow, Integer likes) {
        return (likesNormalizer.calculate(likes) + timeNormalizer.calculate(postTimeWindow)) * 10 / 2;
    }

    private double calculateTrendingScoreWithoutTime(Integer likes) {
        return likesNormalizer.calculate(likes) * 10 / 2;
    }


}
