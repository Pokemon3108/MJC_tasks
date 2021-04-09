package com.epam.esm.comparator;

import java.util.Comparator;

import com.epam.esm.dto.GiftCertificateDto;

public class DateComparator implements Comparator<GiftCertificateDto> {

    @Override
    public int compare(GiftCertificateDto o1, GiftCertificateDto o2) {

        return o1.getCreateDate().compareTo(o2.getCreateDate());
    }
}
