package microblog.comparators;

import lombok.extern.slf4j.Slf4j;
import microblog.repositories.models.PostEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TopTrendingComparator implements Comparator<PostEntity> {

    private List<Comparator<PostEntity>> listComparators;

    public TopTrendingComparator(TopLikesComparator topLikesComparator, DateComparator dateComparator){
        this.listComparators = new ArrayList<Comparator<PostEntity>>() {
            {
                add(topLikesComparator);
                add(dateComparator);
            }
        };
    }

    @Override
    public int compare(PostEntity post1, PostEntity post2) {

        for (Comparator<PostEntity> comparator : listComparators) {
            int result = comparator.compare(post1, post2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
