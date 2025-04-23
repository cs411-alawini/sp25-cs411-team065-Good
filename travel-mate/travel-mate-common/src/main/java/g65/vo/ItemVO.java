package g65.vo;

import lombok.Data;

@Data
public class ItemVO {
    private Integer itemId;
    private ItemType type;
    private Integer count;

    public enum ItemType {
        Attraction,
        Hotel
    }
}
