package g65.repository.mapper;

import g65.repository.po.CollectionFilePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionFileMapper {
    List<CollectionFilePO> findByUserId(Integer userId);
}
