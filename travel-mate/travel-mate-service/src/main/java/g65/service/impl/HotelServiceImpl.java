package g65.service.impl;

import g65.repository.HotelRepository;
import g65.repository.po.HotelPO;
import g65.service.HotelService;
import g65.vo.HotelVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<HotelVO> getHotelsByAttraction(Integer attractionId) {
        List<HotelPO> hotelPOS = hotelRepository.findByAttractionId(attractionId);
        List<HotelVO> hotelVOS = new ArrayList<>();
        for (HotelPO hotelPO : hotelPOS) {
            HotelVO hotelVO = HotelVO.builder()
                    .id(hotelPO.getId())
                    .itemId(hotelPO.getItemId())
                    .name(hotelPO.getName())
                    .imageUrl(hotelPO.getImageUrl())
                    .rating(hotelPO.getRating())
                    .description(hotelPO.getDescription())
                    .address(hotelPO.getAddress())
                    .build();
            hotelVOS.add(hotelVO);
        }
        return hotelVOS;
    }
}
