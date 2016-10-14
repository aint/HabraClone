package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.data.repository.HubRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class HubTransactionalServiceTest {

    private static final long TEST_HUB_ID = 1L;

    @Mock
    private HubRepository hubRepository;

    private HubTransactionalService hubService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hubService = new HubTransactionalService(hubRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllArticlesOfHub() {
        Article article1 = new Article("title-1", "preview-1", "body-1", "keywords-1");
        Article article2 = new Article("title-2", "preview-2", "body-2", "keywords-2");
        List<Article> expectedArticles = Arrays.asList(article1, article2);

        Hub hub1 = new Hub("hub-1", "info-1");
        hub1.setArticles(expectedArticles);
//        hub1.setCreationDate(); init in entity


        Mockito.when(hubRepository.findOne(TEST_HUB_ID)).thenReturn(hub1);

        List<Article> actualArticles = hubService.getAllArticlesOfHub(TEST_HUB_ID);
        assertEquals(expectedArticles, actualArticles);
    }

    @Test(expected = NullPointerException.class)
    public void getAllArticlesOfHubNull() {
        hubService.getAllArticlesOfHub(null);
    }

    @Test
    public void getRepository() {
        assertNotNull(hubService.getRepository());
    }

    @Test
    public void getById() {
        Mockito.when(hubRepository.findOne(TEST_HUB_ID)).thenReturn(new Hub("hub-1", "info-1"));

        Hub actualHub = hubService.getById(TEST_HUB_ID).orElse(null);
        assertNotNull(actualHub);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByIdWithNull() {
        Mockito.when(hubRepository.findOne(TEST_HUB_ID)).thenReturn(null);

        hubService.getById(TEST_HUB_ID).orElseThrow(NoSuchElementException::new);
    }

}