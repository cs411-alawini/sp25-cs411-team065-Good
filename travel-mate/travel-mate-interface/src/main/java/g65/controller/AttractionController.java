package g65.controller;

import g65.api.AttractionApi;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.AttractionService;
import g65.vo.AttractionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
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
}
