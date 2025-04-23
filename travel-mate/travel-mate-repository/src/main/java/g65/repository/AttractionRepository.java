package g65.repository;

import g65.repository.mapper.AttractionMapper;
import g65.repository.po.AttractionPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttractionRepository {

    private final AttractionMapper attractionMapper;

    public List<AttractionPO> findByStateOrderByRating(String state) {
        return attractionMapper.queryAttractionListByStateOrderByRating(state);
    }

    public List<AttractionPO> findTopRated(Integer n) {
        return attractionMapper.queryTopRatedAttractions(n);
    }

    public Long countByState(String state) {
        return attractionMapper.countByState(state);
    }
}
