package g65.api;

import g65.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/collection")
public interface CollectionApi {

    /**
     * Check if the current user has collected a given item (across all their folders).
     *
     * @param itemId the item ID to check
     * @return true if collected, false otherwise
     */
    @GetMapping("/items/{itemId}/exists")
    Response<Boolean> isItemCollected(
            @PathVariable("itemId") Integer itemId
    );
}
