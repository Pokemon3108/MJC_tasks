package com.epam.esm.impl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;

@TestInstance(Lifecycle.PER_CLASS)
class TagServiceImplTest {

    private TagServiceImpl service = new TagServiceImpl();

    @Mock
    private TagDao tagDao = new TagDaoImpl();

    @BeforeAll
    void init() {

        MockitoAnnotations.openMocks(this);
        service.setTagDao(tagDao);
    }

    @Test
    void createTest() {

        Tag tag = new Tag("nature");
        Long id = 1L;

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

        long id = 1L;
        Tag tag = new Tag("nature", id);
        Mockito.when(tagDao.read(id)).thenReturn(Optional.of(tag));
        Assertions.assertEquals(tag, service.readTagById(id));
    }

    @Test
    void throwsExceptionReadTagByIdTest() {

        long id = 15L;
        Mockito.when(tagDao.read(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoTagException.class, () -> service.readTagById(id));
    }


}
