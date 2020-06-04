package microblog.handlers.sort;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties
public class TopTrendingSort {
    private String property;
    private String direction;
}
