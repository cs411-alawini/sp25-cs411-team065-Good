package g65.service;

public interface CollectionService {

    /**
     * Determines if a specific attraction is already in the user's collection.
     *
     * @param userId the current user's ID
     * @param itemId the attraction's ID
     * @return true if the attraction is collected, false otherwise
     */
    boolean isItemCollected(Integer userId, Integer itemId);
}
