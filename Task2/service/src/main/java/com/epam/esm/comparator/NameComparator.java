package com.epam.esm.comparator;

import java.util.Comparator;

import com.epam.esm.dto.GiftCertificateDto;

public class NameComparator implements Comparator<GiftCertificateDto> {

    @Override
    public int compare(GiftCertificateDto o1, GiftCertificateDto o2) {

        return o1.getName().compareTo(o2.getName());
    }
}
