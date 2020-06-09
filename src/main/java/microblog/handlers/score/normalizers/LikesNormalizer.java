package microblog.handlers.score.normalizers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LikesNormalizer extends Normalizer {
    public LikesNormalizer(@Value("${likes.max}") long max,
                           @Value("${likes.min}") long min,
                           @Value("${likes.weight}") int weight) {
        this.max = max;
        this.min = min;
        this.weight = weight;
    }
}
