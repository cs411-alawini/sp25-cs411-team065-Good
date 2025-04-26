package g65.aggregate;


import g65.vo.AttractionVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyzeUserFavoritesAggregate {

    private List<AttractionVO> topAttractions;

    private List<String> topStates;
}
