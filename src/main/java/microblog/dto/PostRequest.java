package microblog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import microblog.repositories.models.PostEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
    String userId;
    String body;
    public PostEntity toPostEntity(){
        PostEntity.builder().build();
        return PostEntity.builder().userId(this.userId).body(this.body).build();
    }
}
