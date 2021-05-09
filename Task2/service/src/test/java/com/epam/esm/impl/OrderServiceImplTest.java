package com.epam.esm.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.MaxSizeLimitException;
import com.epam.esm.exception.NoPageException;
import com.epam.esm.exception.order.NoOrderException;

class OrderServiceImplTest {

    OrderDaoImpl orderDao;

    UserServiceImpl userService;

    GiftCertificateServiceImpl certificateService;

    OrderServiceImpl orderService;

    static Object[][] readUserOrdersTestData() {

        final int validPage = 7;
        final int validSize = 20;

        final int negativeSize = -10;
        final int bigSize = 1000;
        final int negativePage = -17;

        final Class<? extends Exception> maxSizeLimitException = MaxSizeLimitException.class;
        final Class<? extends Exception> noPageException = NoPageException.class;

        return new Object[][]{
                {validPage, negativeSize, maxSizeLimitException},
                {validPage, bigSize, maxSizeLimitException},
                {validPage, validSize, noPageException},
                {negativePage, validSize, noPageException},
        };
    }

    @BeforeEach
    void init() {

        orderDao = mock(OrderDaoImpl.class);
        userService = mock(UserServiceImpl.class);
        certificateService = mock(GiftCertificateServiceImpl.class);
        orderService = new OrderServiceImpl(orderDao, userService, certificateService);
    }

    @Test
    void makeOrderTest() {

        final long certificateId = 6;
        final BigDecimal price = BigDecimal.ONE;
        final int duration = 10;
        final String description = "description";
        final String name = "name";
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setName(name);
        certificateDto.setDescription(description);
        certificateDto.setPrice(price);
        certificateDto.setDuration(duration);
        certificateDto.setId(certificateId);

        final long userId = 900;
        final String userName = "username";
        final String password = "qwert54321zxcvf4d6lk";
        UserDto userDto = new UserDto(userId);
        userDto.setName(userName);
        userDto.setPassword(password);

        final long orderId = 7;
        OrderDto orderDto = new OrderDto();
        orderDto.setCertificate(certificateDto);
        orderDto.setCost(certificateDto.getPrice());
        orderDto.setUser(userDto);

        Mockito.when(certificateService.read(certificateId)).thenReturn(certificateDto);
        Mockito.when(orderDao.create(Mockito.any())).thenReturn(orderId);
        Mockito.when(orderDao.read(orderId)).thenReturn(Optional.of(orderDto));

        Assertions.assertEquals(orderDto, orderService.makeOrder(userId, certificateId));

        Mockito.verify(certificateService, times(1)).read(certificateId);
        Mockito.verify(orderDao, times(1)).create(Mockito.any());
    }

    @Test
    void readTest() {

        final long orderId = 19;
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);

        Mockito.when(orderDao.read(orderId)).thenReturn(Optional.of(orderDto));

        Assertions.assertEquals(orderDto, orderService.read(orderId));
    }

    @Test
    void readThrowsNoOrderExceptionTest() {

        final long orderId = 19L;
        Mockito.when(orderDao.read(orderId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoOrderException.class, () -> orderService.read(orderId));
    }

    @ParameterizedTest
    @MethodSource("readUserOrdersTestData")
    void readUserOrdersThrowsExceptionsTest(final int page, final int size,
            final Class<? extends Exception> exceptionClass) {

        final long userOrdersAmount = 110;
        final long userId = 622;
        UserDto userDto = new UserDto(userId);
        Mockito.when(orderDao.countUserOrders(userDto)).thenReturn(userOrdersAmount);
        Assertions.assertThrows(exceptionClass, () -> orderService.readUserOrders(userId, page, size));
    }

    @Test
    void countUserOrdersTest() {

        final long count = 89;
        final long userId = 327;
        UserDto userDto = new UserDto(userId);

        Mockito.when(orderDao.countUserOrders(userDto)).thenReturn(count);

        Assertions.assertEquals(count, orderService.countUserOrders(userDto));

        Mockito.verify(orderDao, times(1)).countUserOrders(userDto);
    }
}
