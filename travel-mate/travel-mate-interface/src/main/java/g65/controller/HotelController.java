package g65.controller;

import g65.api.HotelApi;
import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.HotelService;
import g65.vo.HotelVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class HotelController implements HotelApi {

    private final HotelService hotelService;

    @Override
    public Response<List<HotelVO>> getHotelsByAttraction(Integer attractionId) {
        log.info("[GetHotels] Fetching hotels for attractionId={}", attractionId);
        List<HotelVO> hotels = hotelService.getHotelsByAttraction(attractionId);
        if (hotels == null) {
            throw new BizException(ResponseCode.NOT_FOUND);
        }
        return Response.<List<HotelVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(hotels)
                .build();
    }
}
