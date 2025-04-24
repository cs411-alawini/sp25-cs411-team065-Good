package g65.repository;

import g65.repository.mapper.CollectionMapper;
import g65.repository.po.AttractionPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionRepository {

    private final CollectionMapper collectionMapper;

    public List<AttractionPO> findByFileId(Integer fileId) {
        return collectionMapper.findByFileId(fileId);
    }

    public void deleteByFileIdAndItemId(Integer fileId, Integer itemId) {
        collectionMapper.deleteByFileIdAndItemId(fileId, itemId);
    }

    public int insertMapping(Integer fileId, Integer itemId) {
        return collectionMapper.insertMapping(fileId, itemId);
    }

    public int countByUserIdAndItemId(Integer userId, Integer itemId) {
        return collectionMapper.countByUserIdAndItemId(userId, itemId);
    }
}
