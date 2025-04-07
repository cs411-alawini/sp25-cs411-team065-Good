package g65.repository.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttractionPO {
    private Integer locationId;
    private Integer itemId;
    private String name;
    private String imageUrl;
    private BigDecimal rating;
    private String description;
    private String state;
}
