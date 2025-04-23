package g65.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionFileVO {
    private Integer fileId;
    private Integer userId;
    private String name;
}
