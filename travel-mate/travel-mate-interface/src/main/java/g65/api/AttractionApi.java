package g65.api;

import g65.response.Response;
import g65.vo.AttractionVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/attractions")
public interface AttractionApi {

    /**
     * Retrieves a list of top-rated attractions in the specified state.
     * The results are ordered by rating in descending order and limited to 10 entries.
     *
     * @param state the name of the state to filter attractions by
     * @return a standardized result containing a list of attraction view objects
     */
    @GetMapping("/state")
    Response<List<AttractionVO>> getTopAttractionsByState(@RequestParam("state") String state);
}
