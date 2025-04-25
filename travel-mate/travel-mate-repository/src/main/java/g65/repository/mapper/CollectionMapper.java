package g65.repository.mapper;

import g65.repository.po.AttractionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    List<AttractionPO> findByFileId(@Param("fileId") Integer fileId);

    void deleteByFileIdAndItemId(@Param("fileId") Integer fileId,
                                 @Param("itemIds") List<Integer> itemIds);

    int insertMapping(@Param("fileId") Integer fileId,
                      @Param("itemId") Integer itemId);

    int countByUserIdAndItemId(@Param("userId") Integer userId,
                               @Param("fileId") Integer itemId);
}
