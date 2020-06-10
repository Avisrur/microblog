package microblog.scheduler.score.normalizers;

import org.springframework.stereotype.Component;

@Component
public abstract class Normalizer {
    long max;
    long min;
    int weight;

    public double calculate(long num){
        return (double)(num - min)/(max-min)*weight;
    }
}
