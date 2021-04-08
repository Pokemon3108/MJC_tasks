package com.epam.esm.comparator;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface ComparatorService {
    List<GiftCertificate> sort(List<GiftCertificate> certificates, List<String> params, String direction);
}
