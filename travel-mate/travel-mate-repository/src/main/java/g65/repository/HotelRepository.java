package g65.repository;

import g65.repository.mapper.HotelMapper;
import g65.repository.po.HotelPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelRepository {
    private final HotelMapper hotelMapper;

    public List<HotelPO> findByAttractionId(Integer attractionId) {
        return hotelMapper.findByAttractionId(attractionId);
    }
}
