package g65.repository.mapper;

import g65.repository.po.HotelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotelMapper {
    List<HotelPO> queryAllHotel();

    List<HotelPO> findByAttractionId(@Param("attractionId") Integer attractionId);
}
