package com.example.demo.social.github.api;

import java.util.Date;

public class GitHubUserProfile {

    private static final long serialVersionUID = 1L;

    private final long id;

    private final String login;

    private final String avatarUrl;

    private final Date createdAt;

    private String name;

    private String location;

    private String company;

    private String blog;

    private String email;

    public GitHubUserProfile(long id, String login, String avatarUrl, Date createdAt) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
