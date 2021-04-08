package com.epam.esm.comparator;

import java.util.Comparator;

import com.epam.esm.entity.GiftCertificate;

public class DateComparator implements Comparator<GiftCertificate> {

    @Override
    public int compare(GiftCertificate o1, GiftCertificate o2) {

        return o1.getCreateDate().compareTo(o2.getCreateDate());
    }
}
