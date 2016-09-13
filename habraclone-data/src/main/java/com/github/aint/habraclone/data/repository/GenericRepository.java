package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.IEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenericRepository<T extends IEntity> extends CrudRepository<T, Long> {

    List<T> getAllSortedDeskByRating();

}
