package g65.service.impl;

import g65.exception.BizException;
import g65.repository.CollectionFileRepository;
import g65.repository.po.CollectionFilePO;
import g65.response.ResponseCode;
import g65.service.CollectionFileService;
import g65.vo.CollectionFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionFileServiceImpl implements CollectionFileService {

    private final CollectionFileRepository collectionFileRepository;

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
    public void deleteUserCollectionFile(Integer userId, Integer fileId) {
        collectionFileRepository.deleteByUserIdAndFileId(userId, fileId);
    }
}
