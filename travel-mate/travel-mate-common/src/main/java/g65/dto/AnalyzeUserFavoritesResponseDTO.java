package g65.dto;

import g65.vo.AttractionVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyzeUserFavoritesResponseDTO {

    private List<AttractionVO> topAttractions;

    private List<String> topStates;
}
