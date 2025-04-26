package g65.repository.po;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AttractionPO {
    private Integer locationId;
    private Integer itemId;
    private String name;
    private String imageUrl;
    private BigDecimal rating;
    private String description;
    private String state;
}
