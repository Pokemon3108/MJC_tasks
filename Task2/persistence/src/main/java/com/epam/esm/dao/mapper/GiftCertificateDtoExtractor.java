package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.Tag;


//TODO: The class is not used. Do we still need it?
public class GiftCertificateDtoExtractor implements ResultSetExtractor<List<GiftCertificateDto>> {

    @Override
    public List<GiftCertificateDto> extractData(ResultSet rs) throws SQLException {

        Map<Long, GiftCertificateDto> map = new LinkedHashMap<>();
        GiftCertificateDto certificate = null;
        while (rs.next()) {
            Long id = rs.getLong("cert_id");
            certificate = map.get(id);
            if (certificate == null) {
                certificate = new GiftCertificateDto();
                certificate.setId(id);
                certificate.setPrice(rs.getBigDecimal("price"));
                certificate.setName(rs.getString("cert_name"));
                certificate.setDescription(rs.getString("description"));
                certificate.setDuration(rs.getInt("duration"));
                certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
                certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
                map.put(id, certificate);
            }
            Long tagId = rs.getLong("tag_id");
            String tagName = rs.getString("tag_name");
            if (tagName != null) {
                Tag tag = new Tag(rs.getString("tag_name"), tagId);
                certificate.addTag(tag);
            }
        }
        return new ArrayList<>(map.values());
    }
}
