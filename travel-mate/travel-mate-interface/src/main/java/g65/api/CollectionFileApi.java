package g65.api;

import g65.response.Response;
import g65.vo.CollectionFileVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping("/api/collection_file")
public interface CollectionFileApi {

    @GetMapping("/files")
    Response<List<CollectionFileVO>> getUserCollectionFiles();
}
