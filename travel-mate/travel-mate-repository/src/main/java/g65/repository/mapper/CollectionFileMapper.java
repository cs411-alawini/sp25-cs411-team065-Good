package g65.repository.mapper;

import g65.repository.po.CollectionFilePO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectionFileMapper {

    List<CollectionFilePO> findByUserId(Integer userId);

    void deleteUserCollectionFile(@Param("userId") Integer userId,
                                 @Param("fileId") Integer fileId);

    int countByUserIdAndFileId(@Param("userId") Integer userId,
                               @Param("fileId") Integer fileId);

    void insert(CollectionFilePO collectionFilePO);

    int updateNameByUserIdAndFileId(@Param("userId") Integer userId,
                                    @Param("fileId") Integer fileId,
                                    @Param("newName") String newName);

    void transferCollectionItems(@Param("sourceFileId") Integer sourceFileId,
                                 @Param("targetFileId") Integer targetFileId,
                                 @Param("itemIds") List<Integer> itemIds);
}
