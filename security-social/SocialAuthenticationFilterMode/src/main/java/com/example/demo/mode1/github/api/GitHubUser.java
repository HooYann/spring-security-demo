/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.mode1.github.api;

import java.io.Serializable;

/**
 * A GitHub user
 *
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 * @author Andy Wilkinson
 */
public class GitHubUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String url;

    private String login;

    private String avatarUrl;

    private String gravatarId;

    private String name;

    private String email;

    public GitHubUser() {}

    public GitHubUser(long id, String url, String login, String avatarUrl, String gravatarId) {
        this.id = id;
        this.url = url;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.gravatarId = gravatarId;
    }

    public Long getId() { return id; }

    public String getUrl() { return url; }

    public String getLogin() { return login; }

    public String getAvatarUrl() { return avatarUrl; }

    public String getGravatarId() { return gravatarId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
