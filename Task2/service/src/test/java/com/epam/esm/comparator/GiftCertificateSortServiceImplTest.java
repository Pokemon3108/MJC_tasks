package com.epam.esm.comparator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.epam.esm.dto.GiftCertificateDto;

class GiftCertificateSortServiceImplTest {

    GiftCertificateSortServiceImpl service = new GiftCertificateSortServiceImpl();

    static Object[][] sortTestData() {

        GiftCertificateDto dto1 = new GiftCertificateDto();
        dto1.setName("Aeroplane");
        dto1.setCreateDate(LocalDateTime.of(2020, 12, 12, 4, 5));

        GiftCertificateDto dto2 = new GiftCertificateDto();
        dto2.setName("Boom");
        dto2.setCreateDate(LocalDateTime.of(1999, 12, 12, 12, 5));

        GiftCertificateDto dto3 = new GiftCertificateDto();
        dto3.setName("Zone");
        dto3.setCreateDate(LocalDateTime.of(2021, 3, 3, 23, 19));

        GiftCertificateDto dto4 = new GiftCertificateDto();
        dto4.setName("Zone");
        dto4.setCreateDate(LocalDateTime.of(2009, 8, 31, 17, 0));

        GiftCertificateDto dto5 = new GiftCertificateDto();
        dto5.setName("Zzzz");
        dto5.setCreateDate(LocalDateTime.of(2009, 8, 31, 17, 0));

        List<GiftCertificateDto> source = Arrays.asList(dto1, dto2, dto3, dto4, dto5);
        List<GiftCertificateDto> nameSortAsc = Arrays.asList(dto1, dto2, dto3, dto4, dto5);
        List<GiftCertificateDto> dateSortAsc = Arrays.asList(dto2, dto4, dto5, dto1, dto3);
        List<GiftCertificateDto> nameDateSortAsc = Arrays.asList(dto1, dto2, dto4, dto3, dto5);
        List<GiftCertificateDto> dateNameSortAsc = Arrays.asList(dto2, dto4, dto5, dto1, dto3);

        return new Object[][]{
                {source, Collections.singletonList("name"), Direction.ASC, nameSortAsc},
                {source, Collections.singletonList("date"), Direction.ASC, dateSortAsc},
                {source, Arrays.asList("name", "date"), Direction.ASC, nameDateSortAsc},
                {source, Arrays.asList("name", "date"), Direction.DESC, reverse(nameDateSortAsc)},
                {source, Arrays.asList("date", "name"), Direction.ASC, dateNameSortAsc},
                {source, Collections.singletonList("qwerty"), Direction.DESC, source},
                {source, new ArrayList<>(), Direction.ASC, source}
        };
    }

    private static List<GiftCertificateDto> reverse(List<GiftCertificateDto> list) {

        List<GiftCertificateDto> reversedList = new LinkedList<>();
        new LinkedList<>(list).descendingIterator().forEachRemaining(reversedList::add);
        return reversedList;
    }

    @ParameterizedTest
    @MethodSource("sortTestData")
    void sortTest(List<GiftCertificateDto> certificates, List<String> params, Direction direction,
            List<GiftCertificateDto> sortedList) {

        Assertions.assertArrayEquals(service.sort(certificates, params, direction).toArray(), sortedList.toArray());
    }
}
