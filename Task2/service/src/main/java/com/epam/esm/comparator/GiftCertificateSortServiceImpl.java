package com.epam.esm.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.esm.dto.GiftCertificateDto;

public class GiftCertificateSortServiceImpl implements GiftCertificateSortService {

    private final Map<String, Comparator<GiftCertificateDto>> comparatorMap = new HashMap<>();

    public GiftCertificateSortServiceImpl() {

        comparatorMap.put("name", Comparator.comparing(GiftCertificateDto::getName));
        comparatorMap.put("date", Comparator.comparing(GiftCertificateDto::getCreateDate));
    }

    @Override
    public List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params,
            Direction direction) {

        List<Comparator<GiftCertificateDto>> comparators = params.stream()
                .map(comparatorMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Comparator<GiftCertificateDto> combined = comparators.stream()
                .reduce(Comparator::thenComparing).orElse(null);
        if (combined == null) {
            return certificates;
        }
        if (direction == Direction.DESC) {
            combined = combined.reversed();
        }
        return certificates.stream().sorted(combined).collect(Collectors.toList());
    }
}
