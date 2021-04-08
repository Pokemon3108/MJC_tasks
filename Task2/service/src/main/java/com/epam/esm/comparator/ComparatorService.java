package com.epam.esm.comparator;

import java.util.List;

import com.epam.esm.entity.GiftCertificate;

public interface ComparatorService {

    List<GiftCertificate> sort(List<GiftCertificate> certificates, List<String> params, String direction);
}
