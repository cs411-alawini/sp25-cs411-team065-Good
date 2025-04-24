package g65.repository.po;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionFilePO {
    private Integer fileId;
    private Integer userId;
    private String name;
}
