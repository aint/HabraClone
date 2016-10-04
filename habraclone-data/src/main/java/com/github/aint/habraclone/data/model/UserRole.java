package com.github.aint.habraclone.data.model;

import javax.persistence.*;

@Entity(name = "Role")
public class UserRole implements IEntity {
    private static final long serialVersionUID = -3836356052817241387L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String role;

    public UserRole() {
    }

    public UserRole(String role) {
        this.role = role;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
