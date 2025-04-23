package g65.service;

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
}
