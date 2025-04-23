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

    /**
     * Retrieves all attractions that a user has collected in a given folder.
     *
     * @param userId the ID of the user whose collections are being queried
     * @param fileId the ID of the collection folder
     * @return a list of AttractionVO representing the attractions in that folder
     */
    List<AttractionVO> getCollectionFileItems(Integer userId, Integer fileId);

    /**
     * Removes a single attraction from a user’s collection folder.
     *
     * @param userId the ID of the user performing the deletion
     * @param fileId the ID of the collection folder containing the attraction
     * @param itemId the ID of the attraction to remove
     */
    void deleteCollectionItem(Integer userId, Integer fileId, Integer itemId);
}

