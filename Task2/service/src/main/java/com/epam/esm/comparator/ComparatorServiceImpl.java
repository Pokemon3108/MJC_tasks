package com.epam.esm.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.esm.entity.GiftCertificate;

public class ComparatorServiceImpl implements ComparatorService {

    private Map<String, Comparator<GiftCertificate>> comparatorMap = new HashMap<>();

    public ComparatorServiceImpl() {

        comparatorMap.put("name", new NameComparator());
        comparatorMap.put("date", new DateComparator());
    }

    @Override
    public List<GiftCertificate> sort(List<GiftCertificate> certificates, List<String> params, String direction) {

        List<Comparator<GiftCertificate>> comparators = params.stream()
                .map(param -> comparatorMap.get(param))
                .collect(Collectors.toList());
        Comparator<GiftCertificate> combined = comparators.stream()
                .reduce(Comparator::thenComparing).get();
        if (combined == null) {
            return certificates;
        }
        if (direction.equalsIgnoreCase("DESC")) {
            combined = combined.reversed();
        }
        return certificates.stream().sorted(combined).collect(Collectors.toList());
    }
}
