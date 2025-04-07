package g65.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionVO {
    private Integer locationId;
    private Integer itemId;
    private String name;
    private String imageUrl;
    private BigDecimal rating;
    private String description;
    private String state;
}
