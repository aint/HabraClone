package com.github.aint.habraclone.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserForm {

    @NotNull
    @Size(min = 5, max = 30, message = "{add_article.error.title}")
    private String username;
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "{add_article.error.preview}")
    private String email;
    @Null
    @Size(min = 5, max = 30, message = "{add_article.error.body}")
    private String fullName;
    @Null
    private Date birthday;
    @NotNull
    @Size(min = 5, max = 30, message = "{add_article.error.keywords}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
