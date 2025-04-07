package g65.repository.mapper;


import g65.repository.po.AttractionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionMapper {
    List<AttractionPO> queryAttractionListByStateOrderByRating(String state);
}
