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

    TagServiceImpl tagService;

    AdminServiceImpl adminService;

    @BeforeEach
    void init() {

        tagService = mock(TagServiceImpl.class);
        adminService = new AdminServiceImpl(tagService);
    }

    @Test
    void getMostPopularTagOfRichestUserTest() {

        final long tagId = 900;
        final String tagName = "aquapark";
        TagDto tagDto = new TagDto(tagId, tagName);

        Mockito.when(tagService.readMostPopularTag()).thenReturn(tagDto);

        Assertions.assertEquals(tagDto, adminService.getMostPopularTagOfRichestUser());

        Mockito.verify(tagService, times(1)).readMostPopularTag();
    }


}
