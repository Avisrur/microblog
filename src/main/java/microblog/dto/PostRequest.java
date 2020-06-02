package microblog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import microblog.repositories.models.PostEntity;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
    String userId;
    String body;

    public PostEntity toPostEntity() {
        return PostEntity.builder()
                .postId(UUID.randomUUID().toString())
                .userId(this.userId)
                .body(this.body)
                .likes(0)
                .createdDate(new Date())
                .build();
    }
}
