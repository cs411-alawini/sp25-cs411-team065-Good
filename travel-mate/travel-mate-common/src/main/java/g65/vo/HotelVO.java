package g65.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelVO {
    private Integer id;
    private Integer itemId;
    private String name;
    private String imageUrl;
    private BigDecimal rating;
    private String description;
    private String address;
}
