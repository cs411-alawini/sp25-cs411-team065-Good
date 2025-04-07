package g65.repository.po;

import lombok.Data;

@Data
public class ItemPO {
    private Integer itemId;
    private ItemType type;

    public enum ItemType {
        Attraction,
        Hotel
    }
}
