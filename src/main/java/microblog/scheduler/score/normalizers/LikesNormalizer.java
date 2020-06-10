package microblog.scheduler.score.normalizers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LikesNormalizer extends Normalizer {
    public LikesNormalizer(@Value("${trending-formula.likes.max}") long max,
                           @Value("${trending-formula.likes.min}") long min,
                           @Value("${trending-formula.likes.weight}") int weight) {
        this.max = max;
        this.min = min;
        this.weight = weight;
    }
}
