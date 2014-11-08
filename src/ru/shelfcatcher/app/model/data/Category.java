package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Category {
    private long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
