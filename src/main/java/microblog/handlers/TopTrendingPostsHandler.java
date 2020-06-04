package microblog.handlers;

import lombok.extern.slf4j.Slf4j;
import microblog.comparators.TopTrendingComparator;
import microblog.handlers.sort.SortConfig;
import microblog.handlers.sort.TopTrendingSort;
import microblog.repositories.models.PostEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopTrendingPostsHandler {

    private SortConfig sortConfig;

    public TopTrendingPostsHandler(SortConfig sortConfig) {
        this.sortConfig = sortConfig;

    }

    public List<Order> createSortByConfig() {
        return sortConfig.getTopTrendingSortList().stream()
                .map(this::buildOrder)
                .collect(Collectors.toList());
    }

    private Order buildOrder(TopTrendingSort sortConfig) {
        return new Order(getSortingDirection(sortConfig.getDirection()), sortConfig.getProperty());
    }

    private Sort.Direction getSortingDirection(String direction) {
        return "asc".equals(direction.toLowerCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
