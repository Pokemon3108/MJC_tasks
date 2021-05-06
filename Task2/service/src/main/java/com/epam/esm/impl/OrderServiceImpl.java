package com.epam.esm.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.order.NoOrderException;

/**
 * Implementation of order service
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    private UserService userService;

    private GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, GiftCertificateService certificateService) {

        this.orderDao = orderDao;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Long makeOrder(Long userId, Long certificateId) {

        GiftCertificateDto certificateDto = certificateService.read(certificateId);
        UserDto user = userService.read(userId);

        OrderDto order = new OrderDto();
        order.setCertificate(certificateDto);
        order.setCost(certificateDto.getPrice());
        order.setUser(user);

        return orderDao.create(order);
    }

    @Override
    public OrderDto read(Long orderId) {

        return orderDao.read(orderId).orElseThrow(() -> new NoOrderException(orderId));
    }

    @Override
    public Set<OrderDto> readUserOrders(long userId, int page, int size) {

        UserDto userDto = userService.read(userId);
        return orderDao.readUserOrder(userDto, page, size);
    }

    @Override
    public long countUserOrders(UserDto user) {

        return orderDao.countUserOrders(user);
    }
}
