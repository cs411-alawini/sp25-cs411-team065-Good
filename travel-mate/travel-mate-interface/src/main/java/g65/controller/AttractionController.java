package g65.controller;

import g65.api.AttractionApi;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.AttractionService;
import g65.vo.AttractionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AttractionController implements AttractionApi {

    private final AttractionService attractionService;

    @Override
    public Response<List<AttractionVO>> getTopAttractionsByState(String state) {
        List<AttractionVO> attractionVOS = attractionService.getTopAttractionsByState(state);
        return Response.<List<AttractionVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(attractionVOS)
                .build();
    }

    @Override
    public Response<List<AttractionVO>> getTopRatedAttractions(Integer n) {
        List<AttractionVO> topAttractions = attractionService.getTopRatedAttractions(n);
        return Response.<List<AttractionVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(topAttractions)
                .build();
    }

    @Override
    public Response<Long> getAttractionCountByState(String state) {
        Long count = attractionService.countByState(state);
        return Response.<Long>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(count)
                .build();
    }

    @Override
    public Response<AttractionVO> getAttractionById(Integer locationId) {
        AttractionVO attractionVO = attractionService.getAttractionById(locationId);
        return Response.<AttractionVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(attractionVO)
                .build();
    }
}
