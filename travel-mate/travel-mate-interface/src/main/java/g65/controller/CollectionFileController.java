package g65.controller;

import g65.api.CollectionFileApi;
import g65.dto.CreateFolderRequestDTO;
import g65.dto.DeleteItemRequestDTO;
import g65.dto.RenameFolderRequestDTO;
import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import g65.service.CollectionFileService;
import g65.util.UserContext;
import g65.vo.AttractionVO;
import g65.vo.CollectionFileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CollectionFileController implements CollectionFileApi {

    private final CollectionFileService collectionFileService;

    @Override
    public Response<CollectionFileVO> createCollectionFolder(CreateFolderRequestDTO request) {
        Integer userId = UserContext.getUserId();
        if (userId == null) throw new BizException(ResponseCode.UNAUTHORIZED);
        CollectionFileVO created = collectionFileService.createCollectionFolder(userId, request.getName());
        return Response.<CollectionFileVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(created)
                .build();
    }

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
    public Response<List<AttractionVO>> getCollectionFileItems(Integer fileId) {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }
        List<AttractionVO> items = collectionFileService.getCollectionFileItems(userId, fileId);
        return Response.<List<AttractionVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(items)
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

    @Override
    public Response<Void> deleteCollectionItem(Integer fileId, DeleteItemRequestDTO request) {
        Integer userId = UserContext.getUserId();
        if (userId == null) throw new BizException(ResponseCode.UNAUTHORIZED);
        collectionFileService.deleteCollectionItem(userId, fileId, request.getItems());
        return Response.<Void>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public Response<Void> addCollectionItem(Integer fileId, Integer itemId) {
        Integer userId = UserContext.getUserId();
        if (userId == null) throw new BizException(ResponseCode.UNAUTHORIZED);

        collectionFileService.addCollectionItem(userId, fileId, itemId);
        return Response.<Void>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public Response<CollectionFileVO> renameFolder(Integer fileId, RenameFolderRequestDTO request) {
        Integer userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCode.UNAUTHORIZED);
        }
        log.info("[RenameFolder] userId={}, fileId={}, newName={}",
                userId, fileId, request.getName());

        CollectionFileVO updated = collectionFileService.renameCollectionFolder(userId, fileId, request.getName());
        return Response.<CollectionFileVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .msg(ResponseCode.SUCCESS.getMessage())
                .data(updated)
                .build();
    }
}
