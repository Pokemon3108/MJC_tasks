package com.epam.esm.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.esm.dto.GiftCertificateDto;

public class ComparatorServiceImpl implements ComparatorService {

    private Map<String, Comparator<GiftCertificateDto>> comparatorMap = new HashMap<>();

    public ComparatorServiceImpl() {

        comparatorMap.put("name", new NameComparator());
        comparatorMap.put("date", new DateComparator());
    }

    @Override
    public List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params, String direction) {

        List<Comparator<GiftCertificateDto>> comparators = params.stream()
                .map(param -> comparatorMap.get(param))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Comparator<GiftCertificateDto> combined = comparators.stream()
                .reduce(Comparator::thenComparing).orElse(null);
        if (combined == null) {
            return certificates;
        }
        if (direction.equalsIgnoreCase("DESC")) {
            combined = combined.reversed();
        }
        return certificates.stream().sorted(combined).collect(Collectors.toList());
    }
}
