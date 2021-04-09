package com.epam.esm.comparator;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;

public interface ComparatorService {

    List<GiftCertificateDto> sort(List<GiftCertificateDto> certificates, List<String> params, String direction);
}
