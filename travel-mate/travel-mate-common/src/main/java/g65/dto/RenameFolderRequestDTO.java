package g65.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameFolderRequestDTO {
    @NotBlank(message = "Folder name must not be blank")
    private String name;
}