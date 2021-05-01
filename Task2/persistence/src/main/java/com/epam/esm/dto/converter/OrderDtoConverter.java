package com.epam.esm.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;

/**
 * The type Order dto converter for converting from entity to dto and vice versa
 */
@Component
public class OrderDtoConverter extends Converter<Order, OrderDto> {

    private GiftCertificateDtoConverter certificateDtoConverter;

    private UserDtoConverter userDtoConverter;

    @Autowired
    public OrderDtoConverter(GiftCertificateDtoConverter certificateDtoConverter,
            @Lazy UserDtoConverter userDtoConverter) {

        this.certificateDtoConverter = certificateDtoConverter;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public OrderDto convertToDto(Order order) {

        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setCertificate(certificateDtoConverter.convertToDto(order.getCertificate()));
        orderDto.setUser(userDtoConverter.convertToDto(order.getUser()));
        return orderDto;
    }

    @Override
    public Order convertToEntity(OrderDto dto) {

        if (dto == null) {
            return null;
        }
        Order order = new Order();
        order.setId(dto.getId());
        order.setCost(dto.getCost());
        order.setCertificate(certificateDtoConverter.convertToEntity(dto.getCertificate()));
        order.setUser(userDtoConverter.convertToEntity(dto.getUser()));
        return order;
    }

}
