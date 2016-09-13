package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.Hub;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This interface represents persistence methods for {@link Hub} objects.
 *
 * @author Oleksandr Tyshkovets
 */
public interface HubRepository extends CrudRepository<Hub, Long> {

    /**
     * Returns a {@code Hub} by the given {@code name}.
     *
     * @param name the name of the requested hub
     * @return a {@code Hub} or {@code null} if a hub with the given {@code name} not found
     */
    Hub getByHubName(String name);

    /**
     * Returns the 10 most popular hubs.
     *
     * @return a list of the most popular hubs
     */
    List getMostPopularHubs();

}
