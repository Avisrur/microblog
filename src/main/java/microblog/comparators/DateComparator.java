package microblog.comparators;

import microblog.repositories.models.PostEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class DateComparator implements Comparator<PostEntity> {
    @Override
    public int compare(PostEntity post1, PostEntity post2) {
        return post2.getCreatedDate().compareTo(post1.getCreatedDate());
    }
}
