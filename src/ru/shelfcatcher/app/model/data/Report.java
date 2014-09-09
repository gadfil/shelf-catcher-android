package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Report {
    private long [] images_id;
    private long store_id;
    private long category_id;

    public long[] getImages_id() {
        return images_id;
    }

    public void setImages_id(long[] images_id) {
        this.images_id = images_id;
    }

    public long getStore_id() {
        return store_id;
    }

    public void setStore_id(long store_id) {
        this.store_id = store_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }
}
