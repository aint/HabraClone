package com.github.aint.habraclone.data.model;

import java.io.Serializable;

public interface IEntity extends Serializable {

    long getId();

    void setId(long id);
}
