package g65.api;

import g65.dto.CreateFolderRequestDTO;
import g65.dto.DeleteItemRequestDTO;
import g65.dto.RenameFolderRequestDTO;
import g65.response.Response;
import g65.vo.AttractionVO;
import g65.vo.CollectionFileVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/collection_file")
public interface CollectionFileApi {

    /**
     * Creates a new collection folder for the current user.
     *
     * @param request DTO containing the name for the new folder
     * @return a standardized response containing the created folder's details
     */
    @PostMapping("/files")
    Response<CollectionFileVO> createCollectionFolder(@RequestBody CreateFolderRequestDTO request);

    /**
     * Retrieves the list of files collected by the current user.
     *
     * @return a standardized response containing the user's collection file list
     */
    @GetMapping("/files")
    Response<List<CollectionFileVO>> getUserCollectionFiles();

    /**
     * Retrieves the items (e.g., attractions) collected inside a specific folder.
     *
     * @param fileId the ID of the collection folder
     * @return a standardized response containing a list of collected items
     */
    @GetMapping("/files/{fileId}/items")
    Response<List<AttractionVO>> getCollectionFileItems(@PathVariable("fileId") Integer fileId);

    /**
     * Deletes a file from the current user's collection.
     *
     * @param fileId the ID of the collected file to remove
     * @return a standardized response indicating deletion success
     */
    @DeleteMapping("/files/{fileId}")
    Response<Void> deleteUserCollectionFile(@PathVariable("fileId") Integer fileId);

    /**
     * Deletes a single item from a specific collection folder.
     *
     * @param fileId the folder ID
     * @param request the item IDs to remove
     * @return deletion success
     */
    @PostMapping("/files/{fileId}/items")
    Response<Void> deleteCollectionItem(
            @PathVariable("fileId") Integer fileId,
            @RequestBody DeleteItemRequestDTO request
    );

    /**
     * Adds an attraction into a specific collection folder for the current user.
     *
     * @param fileId the collection-folder ID
     * @param itemId the attraction (item) ID to add
     * @return a standardized response indicating success
     */
    @PostMapping("/files/{fileId}/items/{itemId}")
    Response<Void> addCollectionItem(
            @PathVariable("fileId") Integer fileId,
            @PathVariable("itemId") Integer itemId
    );

    /**
     * Renames an existing collection folder for the current user.
     *
     * @param fileId the ID of the folder to rename
     * @param request  DTO containing the new folder name
     * @return the updated folder details
     */
    @PutMapping("/files/{fileId}")
    Response<CollectionFileVO> renameFolder(
            @PathVariable("fileId") Integer fileId,
            @RequestBody @Valid RenameFolderRequestDTO request
    );
}
