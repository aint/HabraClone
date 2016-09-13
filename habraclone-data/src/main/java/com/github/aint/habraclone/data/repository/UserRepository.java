package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns a {@code User} by the given {@code username}.
     *
     * @param username userName of the requested user
     * @return a {@code User} or {@code null} if a {@code User} with the given {@code username} not found
     */
    User getByUserName(String username);

    /**
     * Returns a {@code User} by the given {@code email}.
     *
     * @param email
     *            email address of the requested user
     * @return a {@code User} or {@code null} if a {@code User} with the given {@code email} not found
     */
    User getByEmail(String email);

    /**
     * Returns all non activated {@code Users}.
     *
     * @return a list of non activated {@code Users}
     */
    List<User> getNonActivatedUsers();

}
