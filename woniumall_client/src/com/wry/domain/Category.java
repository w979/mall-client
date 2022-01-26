package com.wry.domain;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;

    private String name;

    private Integer status;

    private String navable;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNavable() {
        return navable;
    }

    public void setNavable(String navable) {
        this.navable = navable == null ? null : navable.trim();
    }
}