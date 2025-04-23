package g65.repository.po;

import lombok.Data;

@Data
public class ItemPO {
    private Integer itemId;
    private ItemType type;
    private Integer count;

    public enum ItemType {
        Attraction,
        Hotel
    }
}
