package microblog.handlers.sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
@ConfigurationProperties(prefix = "sort-config")
public class SortConfig {
    private List<TopTrendingSort> topTrendingSortList;
}
