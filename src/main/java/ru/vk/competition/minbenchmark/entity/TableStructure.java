package ru.vk.competition.minbenchmark.entity;

import javax.persistence.Column;

public class TableStructure {
    @Column(name = "field")
    private String field;
    @Column(name = "type")
    private String type;
    @Column(name = "key")
    private String key;
    @Column(name = "default")
    private String defaultV;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        if (type != null && type.equalsIgnoreCase("VARCHAR")) {
            type = "CHARACTER VARYING"; //for strange task
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultV() {
        return defaultV;
    }

    public void setDefaultV(String defaultV) {
        this.defaultV = defaultV;
    }
}
