package g65.controller;

import g65.api.CollectionFileApi;
import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.CollectionFileService;
import g65.util.UserContext;
import g65.vo.CollectionFileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CollectionFileController implements CollectionFileApi {

    private final CollectionFileService collectionFileService;

    @Override
    public Response<List<CollectionFileVO>> getUserCollectionFiles() {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }
        List<CollectionFileVO> files = collectionFileService.getUserCollectionFiles(userId);
        return Response.<List<CollectionFileVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(files)
                .build();
    }

    @Override
    public Response<Void> deleteUserCollectionFile(Integer fileId) {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }
        collectionFileService.deleteUserCollectionFile(userId, fileId);
        return Response.<Void>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .build();
    }
}
