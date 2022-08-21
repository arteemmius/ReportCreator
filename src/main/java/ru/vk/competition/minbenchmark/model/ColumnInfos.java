package ru.vk.competition.minbenchmark.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnInfos {
    private String title;
    private String type;
    private String size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ColumnInfos(String title, String type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ColumnInfos{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
