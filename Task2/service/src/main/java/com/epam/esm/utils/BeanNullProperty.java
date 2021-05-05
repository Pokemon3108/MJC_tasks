package com.epam.esm.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Service for working with null properties of bean
 */
//TODO remove
public class BeanNullProperty {

    /**
     * Copy the non-null property values of the given source bean into the given target bean
     *
     * @param src    the source bean
     * @param target the target bean
     */
    public static void copyNonNullProperties(Object src, Object target) {

        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    /**
     * @param src object with null fields
     * @return names of fields, that are null
     */
    private static String[] getNullProperties(Object src) {

        final BeanWrapper wrapper = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();

        return Arrays.stream(pds)
                .filter(pd -> wrapper.getPropertyValue(pd.getName()) == null)
                .map(PropertyDescriptor::getName)
                .toArray(String[]::new);
    }
}
