package g65.repository;

import g65.repository.mapper.CollectionFileMapper;
import g65.repository.po.CollectionFilePO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionFileRepository {

    private final CollectionFileMapper collectionFileMapper;

    public List<CollectionFilePO> findByUserId(Integer userId) {
        return collectionFileMapper.findByUserId(userId);
    }

    public void deleteByUserIdAndFileId(Integer userId, Integer fileId) {
        collectionFileMapper.deleteUserCollectionFile(userId, fileId);
    }

    public int countByUserIdAndFileId(Integer userId, Integer fileId) {
        return collectionFileMapper.countByUserIdAndFileId(userId, fileId);
    }

    public void insert(CollectionFilePO collectionFilePO) {
        collectionFileMapper.insert(collectionFilePO);
    }

    public int updateNameByUserIdAndFileId(Integer userId, Integer fileId, String newName) {
        return collectionFileMapper.updateNameByUserIdAndFileId(userId, fileId, newName);
    }

    public void transferCollectionItems(Integer sourceFileId, Integer targetFileId, List<Integer> itemIds) {
        collectionFileMapper.transferCollectionItems(sourceFileId, targetFileId, itemIds);
    }
}
