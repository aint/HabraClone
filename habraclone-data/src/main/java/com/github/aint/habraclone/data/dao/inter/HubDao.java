package com.github.aint.habraclone.data.dao.inter;

import com.github.aint.habraclone.data.dao.hibernate.HubHibernateDao;
import com.github.aint.habraclone.data.model.Hub;

import java.util.List;

/**
 * This interface represents persistence methods for {@link Hub} objects.
 *
 * @author Oleksandr Tyshkovets
 * @see HubHibernateDao
 */
public interface HubDao extends GeneralDao {

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
