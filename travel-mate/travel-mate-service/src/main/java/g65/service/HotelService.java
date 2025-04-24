package g65.service;

import g65.vo.HotelVO;

import java.util.List;

public interface HotelService {
    List<HotelVO> getHotelsByAttraction(Integer attractionId);
}
