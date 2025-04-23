package g65.service;

import g65.vo.AttractionVO;
import g65.vo.CollectionFileVO;

import java.util.List;

public interface CollectionFileService {

    /**
     * Fetches all files collected by the given user.
     *
     * @param userId current user ID
     * @return list of collected file VOs
     */
    List<CollectionFileVO> getUserCollectionFiles(Integer userId);

    /**
     * Deletes the specified collected file for the user.
     *
     * @param userId current user ID
     * @param fileId ID of the collected file to remove
     */
    void deleteUserCollectionFile(Integer userId, Integer fileId);

    List<AttractionVO> getCollectionFileItems(Integer userId, Integer fileId);
}
