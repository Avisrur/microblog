package microblog.handlers.score.normalizers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimeNormalizer extends Normalizer {
    public TimeNormalizer(@Value("${time.max}") long max,
                          @Value("${time.min}") long min,
                          @Value("${time.weight}") int weight) {
        this.max = max;
        this.min = min;
        this.weight = weight;
    }
}
