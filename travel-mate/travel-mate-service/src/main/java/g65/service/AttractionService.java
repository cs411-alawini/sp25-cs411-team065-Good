package g65.service;

import g65.vo.AttractionVO;

import java.util.List;

public interface AttractionService {

    /**
     * Retrieves attractions within the specified state, ordered by descending rating.
     *
     * @param state the name of the state to filter attractions by
     * @return a list of AttractionVOs representing the top-rated attractions in that state
     */
    List<AttractionVO> getTopAttractionsByState(String state);

    /**
     * Retrieves the top-N rated attractions across all states.
     *
     * @param n the maximum number of attractions to return
     * @return a list of AttractionVOs for the top-N rated attractions
     */
    List<AttractionVO> getTopRatedAttractions(Integer n);

    /**
     * Counts how many attractions exist in the specified state.
     *
     * @param state the name of the state to count attractions in
     * @return the total number of attractions in that state
     */
    Long countByState(String state);

    /**
     * Fetches detailed information for a given attraction.
     *
     * @param locationId the ID of the attraction
     * @return an AttractionVO with full details
     */
    AttractionVO getAttractionById(Integer locationId);
}