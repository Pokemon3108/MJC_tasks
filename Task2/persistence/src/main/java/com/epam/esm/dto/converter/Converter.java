package com.epam.esm.dto.converter;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Converter<E, D> {

    abstract E convertToEntity(D dto);

    abstract D convertToDto(E entity);

    public Set<D> convertToDtos(Set<E> orderSet) {

        return orderSet.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    public Set<E> convertToEntities(Set<D> orderDtoSet) {

        return orderDtoSet.stream().map(this::convertToEntity).collect(Collectors.toSet());
    }

}
