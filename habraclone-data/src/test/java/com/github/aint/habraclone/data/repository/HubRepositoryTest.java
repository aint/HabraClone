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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.generator.ValueGenerators.sequence;
import static com.ninja_squad.dbsetup.generator.ValueGenerators.stringSequence;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static java.util.Comparator.reverseOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class HubRepositoryTest {

    private static final String HUB_TABLE = Hub.class.getSimpleName();

    private static final int HUBS_COUNT = 20;

    private static final Operation DELETE_ALL = sequenceOf(deleteAllFrom(HUB_TABLE));
    private static final Operation INSERT_DATA = sequenceOf(
            insertInto(HUB_TABLE)
                    .withGeneratedValue("id", sequence().startingAt(1L))
                    .withGeneratedValue("name", stringSequence("hub-").startingAt(1L))
                    .withGeneratedValue("rating", sequence().startingAt(-10))
                    .columns("creationDate")
                    .repeatingValues(LocalDateTime.now()).times(HUBS_COUNT)
                    .build());

    @Autowired
    private DataSource dataSource;
    @Autowired
    private HubRepository hubRepository;

    @Before
    public void setUp() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), sequenceOf(DELETE_ALL, INSERT_DATA)).launch();
    }
    @After
    public void tearDown() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), DELETE_ALL).launch();
    }

    @Test
    public void findByName() {
        assertNotNull(hubRepository.findByName("hub-1"));
    }

    @Test
    public void findByNameNull() {
        assertNull(hubRepository.findByName(null));
    }

    @Test
    public void findByNameNotFound() {
        assertNull(hubRepository.findByName("hub-42"));
    }

    @Test
    public void findTop10ByOrderByRating() {
        List<Integer> actualRatings = hubRepository.findTop10ByOrderByRatingDesc().stream()
                .map(Hub::getRating)
                .collect(Collectors.toList());
        List<Integer> expectedRatings = IntStream.range(0, 10)
                .boxed()
                .sorted(reverseOrder())
                .collect(Collectors.toList());
        assertEquals(expectedRatings, actualRatings);
    }

    /*===== Common methods =====*/

    @Test
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, hubRepository.findOne(1L).getId());
    }

    @Test
    public void findAll() {
        List<Hub> allHubs = new ArrayList<>();
        hubRepository.findAll().forEach(allHubs::add);

        assertEquals(HUBS_COUNT, allHubs.size());
    }

    @Test
    public void save() {
        Hub hub = new Hub("hub-101", "description");
        hub.setCreationDate(LocalDateTime.now());
        assertNotNull(hubRepository.save(hub));
    }

    @Test
    public void update() {
        final long hubId = 1L;
        final String expectedName = "new_name";

        Hub hub = hubRepository.findOne(hubId);
        hub.setName(expectedName);
        hubRepository.save(hub);

        assertEquals(expectedName, hubRepository.findOne(hubId).getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUsersWithSameEmail() {
        final String hubName = "same_hub_name";

        Hub hub1 = new Hub(hubName, "description");
        hub1.setCreationDate(LocalDateTime.now());
        Hub hub2 = new Hub(hubName, "description");
        hub2.setCreationDate(LocalDateTime.now());

        hubRepository.save(hub1);
        hubRepository.save(hub2);
    }

    @Test
    public void delete() {
        final long hubId = 1L;
        hubRepository.delete(hubId);
        assertNull(hubRepository.findOne(hubId));
    }
}