package microblog.repositories.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class PostEntity {
    @Id
    private String id;
    private String userId;
    private String body;
}
