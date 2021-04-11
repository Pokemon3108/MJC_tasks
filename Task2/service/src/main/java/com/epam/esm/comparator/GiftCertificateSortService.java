package com.epam.esm.comparator;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;

public interface GiftCertificateSortService {

    List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params, Direction direction);
}
