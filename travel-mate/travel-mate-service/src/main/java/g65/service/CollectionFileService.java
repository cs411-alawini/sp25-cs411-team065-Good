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
     * @param collectionItemIds the ID of the attraction to remove
     */
    void deleteCollectionItem(Integer userId, Integer fileId, List<Integer> collectionItemIds);

    /**
     * Creates a new collection folder for the specified user.
     *
     * @param userId the ID of the user for whom the folder is being created
     * @param name   the desired name of the new collection folder
     * @return       a VO representing the newly created folder, including its generated fileId
     */
    CollectionFileVO createCollectionFolder(Integer userId, String name);

    /**
     * Adds an item (attraction) to a user’s collection folder.
     *
     * @param userId the current user’s ID
     * @param fileId the collection folder’s ID
     * @param itemId the attraction’s ID
     */
    void addCollectionItem(Integer userId, Integer fileId, Integer itemId);


    /**
     * Renames a collection folder belonging to a user.
     *
     * @param userId   the ID of the current user
     * @param fileId the ID of the folder to rename
     * @param newName  the new folder name
     * @return the updated folder VO
     */
    CollectionFileVO renameCollectionFolder(Integer userId, Integer fileId, String newName);
}

