package ua.sg.academy.java2.habraclone.model;

import java.io.Serializable;

public interface IEntity extends Serializable {

    long getId();

    void setId(long id);
}
