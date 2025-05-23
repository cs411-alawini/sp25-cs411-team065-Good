package g65.repository.mapper;


import g65.repository.po.AttractionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionMapper {

    List<AttractionPO> queryAttractionListByStateOrderByRating(String state);

    List<AttractionPO> queryTopRatedAttractions(Integer n);

    Long countByState(String state);

    AttractionPO findByLocationId(@Param("locationId") Integer locationId);
}
