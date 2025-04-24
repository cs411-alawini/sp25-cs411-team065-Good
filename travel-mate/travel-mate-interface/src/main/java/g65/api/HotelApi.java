package g65.api;

import g65.response.Response;
import g65.vo.HotelVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/hotels")
public interface HotelApi {

    /**
     * Retrieves all hotels associated with the specified attraction.
     *
     * @param attractionId the ID of the attraction
     * @return a standardized response containing a list of HotelVO
     */
    @GetMapping("/by-attraction/{attractionId}")
    Response<List<HotelVO>> getHotelsByAttraction(
            @PathVariable("attractionId") Integer attractionId
    );
}
