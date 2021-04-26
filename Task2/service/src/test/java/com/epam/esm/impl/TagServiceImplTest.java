package com.epam.esm.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;


class TagServiceImplTest {

    TagServiceImpl service = new TagServiceImpl();

    @Mock
    TagDao tagDao = new TagDaoImpl();

    @BeforeEach
    void init() {

        MockitoAnnotations.openMocks(this);
        service.setTagDao(tagDao);
    }

    @Test
    void createTest() {

        Tag tag = new Tag("nature");
        final long id = 1L;

        Mockito.when(tagDao.readTagByName(tag.getName())).thenReturn(Optional.empty());
        Mockito.when(tagDao.insert(tag)).thenReturn(id);
        Assertions.assertEquals(id, service.create(tag));
    }

    @Test
    void throwsExceptionCreateTest() {

        Tag tag = new Tag("nature");
        Mockito.when(tagDao.readTagByName(tag.getName())).thenReturn(Optional.of(tag));
        Assertions.assertThrows(DuplicateTagException.class, () -> service.create(tag));
    }

    @Test
    void readTagByIdTest() {

        final long id = 1L;
        Tag tag = new Tag("nature", id);
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
        Mockito.when(tagDao.read(id)).thenReturn(Optional.of(new Tag()));

        service.delete(id);
        Mockito.verify(tagDao, Mockito.times(1)).deleteCertificateTagsByTagId(id);
        Mockito.verify(tagDao, Mockito.times(1)).delete(id);
    }


    @Test
    void setTagsIdTest() {

        Set<String> tagNames = new HashSet<>(Arrays.asList("tag1", "tag2"));
        Set<Tag> tagsWithId = new HashSet<>(Collections.singletonList(new Tag("tag1", 1L)));
        Set<Tag> newTags = new HashSet<>(Collections.singletonList(new Tag("tag2", 2L)));

        Mockito.when(tagDao.readTagsByNames(tagNames)).thenReturn(tagsWithId);
        newTags.forEach(it -> Mockito.when(tagDao.insert(it)).thenReturn(it.getId()));

        tagsWithId.addAll(newTags);
        Assertions.assertArrayEquals(
                service.setTagsId(tagNames.stream().map(Tag::new).collect(Collectors.toSet())).toArray(),
                tagsWithId.toArray());

    }


}
