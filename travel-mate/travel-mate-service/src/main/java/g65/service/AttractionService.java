package g65.service;

import g65.vo.AttractionVO;

import java.util.List;

public interface AttractionService {

    List<AttractionVO> getTopAttractionsByState(String state);

    List<AttractionVO> getTopRatedAttractions(Integer n);

    Long countByState(String state);
}