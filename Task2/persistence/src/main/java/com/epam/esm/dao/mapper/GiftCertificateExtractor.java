package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException {
        Map<Long, GiftCertificate> map = new LinkedHashMap<>();
        GiftCertificate certificate = null;
        while (rs.next()) {
            Long id = rs.getLong("cert_id");
            if (certificate == null) {
                certificate = new GiftCertificate();
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
            Tag tag = new Tag(rs.getString("tag_name"), tagId);
            certificate.addTag(tag);
        }
        return new ArrayList<>(map.values());
    }
}
