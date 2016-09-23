package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.config.DataSpringConfig;
import com.github.aint.habraclone.data.model.Hub;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class HubRepositoryTest {

    private static final String HUB_TABLE = Hub.class.getSimpleName();

    private static final Operation DELETE_ALL = sequenceOf(deleteAllFrom(HUB_TABLE));
    private static final Operation INSERT_DATA =
            sequenceOf(
                    insertInto(HUB_TABLE)
                            .columns("id", "name", "creationDate", "description",  "rating")
                            .values(1L, "Hub_1", "2016-07-01 15:47:17", "First Hub", 11)
                            .values(2L, "Hub_2", "2016-07-01 15:47:17", "Second Hub", 12)
                            .build());

    @Autowired
    private DataSource dataSource;
    @Autowired
    private HubRepository hubRepository;

    @Before
    public void setUp() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), INSERT_DATA).launch();
    }

    @After
    public void tearDown() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), DELETE_ALL).launch();
    }


    @Test
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, hubRepository.findOne(1L).getId());
    }

    @Test
    public void findAll() {
        final int expectedSize = 2;

        List<Hub> allHubs = new ArrayList<>();
        hubRepository.findAll().forEach(allHubs::add);

        assertEquals(expectedSize, allHubs.size());
    }
}