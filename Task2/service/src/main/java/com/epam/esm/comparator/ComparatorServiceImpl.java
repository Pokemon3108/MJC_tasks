package com.epam.esm.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.comparator.Comparators;

import com.epam.esm.dto.GiftCertificateDto;

//TODO: It seems that the class sorts certificates, maybe GiftCertificateSortService(or smth like this) will be better?
public class ComparatorServiceImpl implements ComparatorService {

    //TODO: it can be final
    private Map<String, Comparator<GiftCertificateDto>> comparatorMap = new HashMap<>();

    public ComparatorServiceImpl() {

        //TODO: Can we use Comparator.comparing(GiftCertificateDto::getName) instead of DateComparator class?
        comparatorMap.put("name", new NameComparator());
        //TODO: Can we use Comparator.comparing(GiftCertificateDto::getCreateDate) instead of DateComparator class?
        comparatorMap.put("date", new DateComparator());
    }

    @Override
    public List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params, String direction) {

        //TODO: direction also can be moved to enum
        //TODO: possible option of refactoring:
//       Comparator<GiftCertificateDto> certificateDtoComparator = params.stream()
//                .map(param -> comparatorMap.get(param))
//                .filter(Objects::nonNull)
//                .reduce(Comparator::thenComparing)
//                .orElse(Comparator.comparing(GiftCertificateDto::getName));
//
//
//        if (direction.equalsIgnoreCase("DESC")) {
//            certificateDtoComparator = certificateDtoComparator.reversed();
//        }
//
//        return certificates.stream()
//                .sorted(certificateDtoComparator)
//                .collect(Collectors.toList());


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
