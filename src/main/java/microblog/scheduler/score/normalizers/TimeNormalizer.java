package microblog.scheduler.score.normalizers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimeNormalizer extends Normalizer {
    public TimeNormalizer(@Value("${trending-formula.time.max}") long max,
                          @Value("${trending-formula.time.min}") long min,
                          @Value("${trending-formula.time.weight}") int weight) {
        this.max = max;
        this.min = min;
        this.weight = weight;
    }
}
