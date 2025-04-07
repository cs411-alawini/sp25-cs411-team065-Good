package g65.repository.mapper;

import g65.repository.po.HotelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HotelMapper {
    List<HotelPO> queryAllHotel();
}
