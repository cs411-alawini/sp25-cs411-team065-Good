package g65.service.impl;


import g65.repository.CollectionRepository;
import g65.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    @Override
    public boolean isItemCollected(Integer userId, Integer itemId) {
        int cnt = collectionRepository.countByUserIdAndItemId(userId, itemId);
        return cnt > 0;
    }
}
