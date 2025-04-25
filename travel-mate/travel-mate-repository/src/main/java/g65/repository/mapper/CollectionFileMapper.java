package g65.repository.mapper;

import g65.repository.po.CollectionFilePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
