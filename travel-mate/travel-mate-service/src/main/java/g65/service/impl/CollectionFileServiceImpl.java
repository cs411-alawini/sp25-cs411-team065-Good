package g65.service.impl;

import g65.exception.BizException;
import g65.repository.CollectionFileRepository;
import g65.repository.CollectionRepository;
import g65.repository.po.AttractionPO;
import g65.repository.po.CollectionFilePO;
import g65.response.ResponseCode;
import g65.service.CollectionFileService;
import g65.vo.AttractionVO;
import g65.vo.CollectionFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionFileServiceImpl implements CollectionFileService {

    private final CollectionFileRepository collectionFileRepository;
    private final CollectionRepository collectionRepository;

    @Override
    public List<CollectionFileVO> getUserCollectionFiles(Integer userId) {
        List<CollectionFilePO> collectionFilePOS = collectionFileRepository.findByUserId(userId);
        List<CollectionFileVO> collectionFileVOS = new ArrayList<>();
        for (CollectionFilePO collectionFilePO : collectionFilePOS) {
            CollectionFileVO collectionFileVO = CollectionFileVO.builder()
                    .fileId(collectionFilePO.getFileId())
                    .userId(collectionFilePO.getUserId())
                    .name(collectionFilePO.getName())
                    .build();
            collectionFileVOS.add(collectionFileVO);
        }
        return collectionFileVOS;
    }

    @Override
    public List<AttractionVO> getCollectionFileItems(Integer userId, Integer fileId) {
        if (collectionFileRepository.countByUserIdAndFileId(userId, fileId) == 0) {
            throw new BizException(ResponseCode.PERMISSION_DENIED);
        }
        List<AttractionPO> items = collectionRepository.findByFileId(fileId);
        List<AttractionVO> attractionVOS = new ArrayList<>();
        for (AttractionPO item : items) {
            AttractionVO attractionVO = AttractionVO.builder()
                    .locationId(item.getLocationId())
                    .itemId(item.getItemId())
                    .name(item.getName())
                    .imageUrl(item.getImageUrl())
                    .rating(item.getRating())
                    .description(item.getDescription())
                    .state(item.getState())
                    .build();
            attractionVOS.add(attractionVO);
        }
        return attractionVOS;
    }

    @Override
    @Transactional
    public void deleteUserCollectionFile(Integer userId, Integer fileId) {
        collectionFileRepository.deleteByUserIdAndFileId(userId, fileId);
    }

    @Override
    @Transactional
    public void deleteCollectionItem(Integer userId, Integer fileId, List<Integer> collectionItemIds) {
        if (collectionFileRepository.countByUserIdAndFileId(userId, fileId) == 0) {
            throw new BizException(ResponseCode.PERMISSION_DENIED);
        }
        collectionRepository.deleteByFileIdAndItemId(fileId, collectionItemIds);
    }

    @Override
    @Transactional
    public CollectionFileVO createCollectionFolder(Integer userId, String name) {
        CollectionFilePO collectionFilePO = CollectionFilePO.builder()
                .userId(userId)
                .name(name)
                .build();
        collectionFileRepository.insert(collectionFilePO);
        return CollectionFileVO.builder()
                .fileId(collectionFilePO.getFileId())
                .userId(userId)
                .name(name)
                .build();
    }

    @Override
    @Transactional
    public void addCollectionItem(Integer userId, Integer fileId, Integer itemId) {
        if (collectionFileRepository.countByUserIdAndFileId(userId, fileId) == 0) {
            throw new BizException(ResponseCode.PERMISSION_DENIED);
        }
        int inserted = collectionRepository.insertMapping(fileId, itemId);
        if (inserted == 0) {
            throw new BizException(ResponseCode.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public CollectionFileVO renameCollectionFolder(Integer userId, Integer fileId, String newName) {
        int count = collectionFileRepository.countByUserIdAndFileId(userId, fileId);
        if (count == 0) {
            throw new BizException(ResponseCode.PERMISSION_DENIED);
        }

        int updated = collectionFileRepository.updateNameByUserIdAndFileId(userId, fileId, newName);
        if (updated == 0) {
            throw new BizException(ResponseCode.SERVER_ERROR);
        }

        return CollectionFileVO.builder()
                .fileId(fileId)
                .userId(userId)
                .name(newName)
                .build();
    }

    @Override
    public void transferCollectionItems(Integer userId, Integer sourceFileId, Integer targetFileId, List<Integer> itemIds) {
        collectionFileRepository.transferCollectionItems(sourceFileId, targetFileId, itemIds);
    }
}
