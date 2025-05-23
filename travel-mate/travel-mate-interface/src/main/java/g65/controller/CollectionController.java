package g65.controller;

import g65.aggregate.AnalyzeUserFavoritesAggregate;
import g65.api.CollectionApi;
import g65.dto.AnalyzeUserFavoritesResponseDTO;
import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.CollectionFileService;
import g65.service.CollectionService;
import g65.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CollectionController implements CollectionApi {

    private final CollectionService collectionService;
    private final CollectionFileService collectionFileService;

    @Override
    public Response<Boolean> isItemCollected(Integer itemId) {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }
        boolean exist = collectionService.isItemCollected(userId, itemId);
        return Response.<Boolean>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(exist)
                .build();
    }

    @Override
    public Response<AnalyzeUserFavoritesResponseDTO> analyzeUserFavorites() {
        Integer userId = UserContext.getUserId();
        if (userId == null) throw new BizException(ResponseCode.UNAUTHORIZED);

        AnalyzeUserFavoritesAggregate result = collectionFileService.analyzeUserFavorites(userId);

        return Response.<AnalyzeUserFavoritesResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(AnalyzeUserFavoritesResponseDTO.builder()
                        .topAttractions(result.getTopAttractions())
                        .topStates(result.getTopStates())
                        .build())
                .build();
    }
}
