package g65.repository;

import g65.aggregate.AnalyzeUserFavoritesAggregate;
import g65.repository.mapper.CollectionFileMapper;
import g65.repository.po.AttractionPO;
import g65.repository.po.CollectionFilePO;
import g65.vo.AttractionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CollectionFileRepository {

    private final CollectionFileMapper collectionFileMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<CollectionFilePO> findByUserId(Integer userId) {
        return collectionFileMapper.findByUserId(userId);
    }

    public void deleteByUserIdAndFileId(Integer userId, Integer fileId) {
        collectionFileMapper.deleteUserCollectionFile(userId, fileId);
    }

    public int countByUserIdAndFileId(Integer userId, Integer fileId) {
        return collectionFileMapper.countByUserIdAndFileId(userId, fileId);
    }

    public void insert(CollectionFilePO collectionFilePO) {
        collectionFileMapper.insert(collectionFilePO);
    }

    public int updateNameByUserIdAndFileId(Integer userId, Integer fileId, String newName) {
        return collectionFileMapper.updateNameByUserIdAndFileId(userId, fileId, newName);
    }

    public void transferCollectionItems(Integer sourceFileId, Integer targetFileId, List<Integer> itemIds) {
        collectionFileMapper.transferCollectionItems(sourceFileId, targetFileId, itemIds);
    }

    public AnalyzeUserFavoritesAggregate analyzeUserFavorites(Integer userId) {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{CALL analyze_user_favorites(?)}")) {
                cs.setInt(1, userId);
                boolean hasResults = cs.execute();

                List<AttractionPO> attractions = new ArrayList<>();
                List<String> topStates = new ArrayList<>();

                if (hasResults) {
                    try (ResultSet rs1 = cs.getResultSet()) {
                        while (rs1.next()) {
                            AttractionPO po = AttractionPO.builder()
                                    .locationId(rs1.getInt("id"))
                                    .itemId(rs1.getInt("item_id"))
                                    .name(rs1.getString("name"))
                                    .imageUrl(rs1.getString("image_url"))
                                    .rating(rs1.getBigDecimal("rating"))
                                    .description(rs1.getString("description"))
                                    .state(rs1.getString("state"))
                                    .build();
                            attractions.add(po);
                        }
                    }
                }

                if (cs.getMoreResults()) {
                    try (ResultSet rs2 = cs.getResultSet()) {
                        while (rs2.next()) {
                            topStates.add(rs2.getString("state"));
                        }
                    }
                }

                List<AttractionVO> attractionVOS = new ArrayList<>();
                for (AttractionPO po : attractions) {
                    AttractionVO attractionVO = AttractionVO.builder()
                            .locationId(po.getLocationId())
                            .itemId(po.getItemId())
                            .name(po.getName())
                            .imageUrl(po.getImageUrl())
                            .rating(po.getRating())
                            .description(po.getDescription())
                            .state(po.getState())
                            .build();
                    attractionVOS.add(attractionVO);
                }

                return AnalyzeUserFavoritesAggregate.builder()
                        .topAttractions(attractionVOS)
                        .topStates(topStates)
                        .build();
            }
        });
    }
}
