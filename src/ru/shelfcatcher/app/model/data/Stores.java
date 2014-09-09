package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Stores {
    private long id;
    private String full_name;

    @Override
    public String toString() {
        return  full_name ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
