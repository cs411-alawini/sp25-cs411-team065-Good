package g65.repository.mapper;

import g65.repository.po.AttractionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    List<AttractionPO> findByFileId(@Param("fileId") Integer fileId);
}
