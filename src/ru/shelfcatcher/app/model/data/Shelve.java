package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Shelve {
    private long id;
    private String kind;

    @Override
    public String toString() {
        return kind;
    }


//    @Override
//    public String toString() {
//        return "Shelve{" +
//                "id=" + id +
//                ", kind='" + kind + '\'' +
//                '}';
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
