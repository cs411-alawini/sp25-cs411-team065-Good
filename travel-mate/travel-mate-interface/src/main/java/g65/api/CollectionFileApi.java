package g65.api;

import g65.response.Response;
import g65.vo.CollectionFileVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping("/api/collection_file")
public interface CollectionFileApi {

    /**
     * Retrieves the list of files collected by the current user.
     *
     * @return a standardized response containing the user's collection file list
     */
    @GetMapping("/files")
    Response<List<CollectionFileVO>> getUserCollectionFiles();

    /**
     * Deletes a file from the current user's collection.
     *
     * @param fileId the ID of the collected file to remove
     * @return a standardized response indicating deletion success
     */
    @DeleteMapping("/files/{fileId}")
    Response<Void> deleteUserCollectionFile(@PathVariable("fileId") Integer fileId);
}
