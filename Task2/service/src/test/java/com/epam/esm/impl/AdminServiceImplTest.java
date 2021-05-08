package com.epam.esm.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

class AdminServiceImplTest {

    UserServiceImpl userService;

    TagServiceImpl tagService;

    AdminServiceImpl adminService;

    @BeforeEach
    void init() {

        userService = mock(UserServiceImpl.class);
        tagService = mock(TagServiceImpl.class);
        adminService = new AdminServiceImpl(userService, tagService);
    }

    @Test
    void getMostPopularTagOfRichestUserTest() {

        final long userId = 5;
        UserDto userDto = new UserDto();
        userDto.setId(userId);

        final long tagId = 900;
        final String tagName = "aquapark";
        TagDto tagDto = new TagDto(tagId, tagName);

        Mockito.when(userService.readRichest()).thenReturn(userDto);
        Mockito.when(tagService.readMostPopularTag(userDto)).thenReturn(tagDto);

        Assertions.assertEquals(tagDto, adminService.getMostPopularTagOfRichestUser());

        Mockito.verify(userService, times(1)).readRichest();
        Mockito.verify(tagService, times(1)).readMostPopularTag(userDto);
    }


}
