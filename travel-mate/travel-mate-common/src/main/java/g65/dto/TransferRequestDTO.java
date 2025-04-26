package g65.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransferRequestDTO {
    private List<Integer> itemIds;
}