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
}
