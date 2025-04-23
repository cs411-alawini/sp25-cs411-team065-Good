package g65.service.impl;

import g65.repository.AttractionRepository;
import g65.repository.po.AttractionPO;
import g65.service.AttractionService;
import g65.vo.AttractionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public List<AttractionVO> getTopAttractionsByState(String state) {
        System.out.println(state);
        List<AttractionPO> attractionPOS = attractionRepository.findByStateOrderByRating(state);
        List<AttractionVO> attractionVOS = new ArrayList<>();
        for (AttractionPO attractionPO : attractionPOS) {
            AttractionVO attractionVO = AttractionVO.builder()
                    .locationId(attractionPO.getLocationId())
                    .itemId(attractionPO.getItemId())
                    .name(attractionPO.getName())
                    .imageUrl(attractionPO.getImageUrl())
                    .rating(attractionPO.getRating())
                    .description(attractionPO.getDescription())
                    .state(attractionPO.getState())
                    .build();
            attractionVOS.add(attractionVO);
        }
        return attractionVOS;
    }

    @Override
    public List<AttractionVO> getTopRatedAttractions(Integer n) {
        List<AttractionPO> attractionPOS = attractionRepository.findTopRated(n);
        List<AttractionVO> attractionVOS = new ArrayList<>();
        for (AttractionPO attractionPO : attractionPOS) {
            AttractionVO attractionVO = AttractionVO.builder()
                    .locationId(attractionPO.getLocationId())
                    .itemId(attractionPO.getItemId())
                    .name(attractionPO.getName())
                    .imageUrl(attractionPO.getImageUrl())
                    .rating(attractionPO.getRating())
                    .description(attractionPO.getDescription())
                    .state(attractionPO.getState())
                    .build();
            attractionVOS.add(attractionVO);
        }
        return attractionVOS;
    }

    @Override
    public Long countByState(String state) {
        return attractionRepository.countByState(state);
    }
}
