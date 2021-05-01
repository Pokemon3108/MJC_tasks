package com.epam.esm.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.GiftCertificateDtoConverter;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.order.CreationOrderException;
import com.epam.esm.exception.order.NoOrderException;

/**
 * Implementation of order service
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    private UserService userService;

    private GiftCertificateService certificateService;

    private GiftCertificateDtoConverter certificateDtoConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, GiftCertificateService certificateService,
            GiftCertificateDtoConverter converter) {

        this.orderDao = orderDao;
        this.userService = userService;
        this.certificateService = certificateService;
        this.certificateDtoConverter = converter;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Order makeOrder(Long userId, Long certificateId) {

        GiftCertificateDto certificateDto = certificateService.read(certificateId);
        User user = userService.read(userId);

        Order order = new Order();
        order.setCertificate(certificateDtoConverter.convertToEntity(certificateDto));
        order.setCost(certificateDto.getPrice());
        order.setUser(user);
        Optional<Order> createdOrder = orderDao.create(order);
        return createdOrder.orElseThrow(CreationOrderException::new);
    }

    @Override
    public Order read(Long orderId) {

        return orderDao.read(orderId).orElseThrow(() -> new NoOrderException(orderId));
    }
}
