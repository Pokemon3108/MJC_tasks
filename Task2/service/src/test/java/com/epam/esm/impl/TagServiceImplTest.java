package com.epam.esm.impl;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.tag.DuplicateTagException;
import com.epam.esm.exception.tag.NoTagException;
import com.epam.esm.exception.user.UsersOrderHasNoTags;

class TagServiceImplTest {

    TagDao tagDao;
    TagServiceImpl service;

    @BeforeEach
    void init() {

        tagDao = mock(TagDaoImpl.class);
        service = new TagServiceImpl(tagDao);
    }

    @Test
    void createTest() {

        final long id = 1L;
        TagDto tag = new TagDto(id, "nature");

        Mockito.when(tagDao.readTagByName(tag.getName())).thenReturn(Optional.empty());
        Mockito.when(tagDao.insert(tag)).thenReturn(id);
        Mockito.when(tagDao.read(id)).thenReturn(Optional.of(tag));
        Assertions.assertEquals(tag, service.create(tag));
    }

    @Test
    void throwsExceptionCreateTest() {

        TagDto tag = new TagDto("nature");
        Mockito.when(tagDao.readTagByName(tag.getName())).thenReturn(Optional.of(tag));
        Assertions.assertThrows(DuplicateTagException.class, () -> service.create(tag));
    }

    @Test
    void readTagByIdTest() {

        final long id = 1L;
        TagDto tag = new TagDto(id, "nature");
        Mockito.when(tagDao.read(id)).thenReturn(Optional.of(tag));
        Assertions.assertEquals(tag, service.readTagById(id));
    }

    @Test
    void throwsExceptionReadTagByIdTest() {

        final long id = 15L;
        Mockito.when(tagDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoTagException.class, () -> service.readTagById(id));
    }

    @Test
    void throwsExceptionDeleteTest() {

        final long id = 99L;
        Mockito.when(tagDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoTagException.class, () -> service.delete(id));
    }

    @Test
    void deleteTest() {

        final long id = 1;
        Mockito.when(tagDao.read(id)).thenReturn(Optional.of(new TagDto()));

        service.delete(id);
        Mockito.verify(tagDao, Mockito.times(1)).delete(Mockito.any());
    }


    @Test
    void bindTagsWithIdsTest() {

        Set<TagDto> tagNames = new HashSet<>(Arrays.asList(new TagDto("tag1"), new TagDto("tag2")));
        Set<TagDto> tagsWithId = new HashSet<>(Collections.singletonList(new TagDto(1L, "tag1")));
        Set<TagDto> tagsToSave = new HashSet<>(Arrays.asList(new TagDto(1L, "tag1"), new TagDto("tag2")));

        Mockito.when(tagDao.readTagsByNames(tagNames.stream().map(TagDto::getName).collect(Collectors.toSet())))
                .thenReturn(tagsWithId);

        Assertions.assertArrayEquals(
                service.bindTagsWithIds(tagNames).toArray(), tagsToSave.toArray());

    }

    @Test
    void readMostPopularTagTest() {

        UserDto user = new UserDto();
        TagDto tagDto = new TagDto(1L, "name");
        Mockito.when(tagDao.readTheMostPopularTag(user)).thenReturn(Optional.of(tagDto));
        Assertions.assertEquals(tagDto, service.readMostPopularTag(user));
    }

    @Test
    void throwsExceptionReadMostPopularTagTest() {

        UserDto user = new UserDto(1L);
        Mockito.when(tagDao.readTheMostPopularTag(user)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsersOrderHasNoTags.class, () -> service.readMostPopularTag(user));
    }


}
