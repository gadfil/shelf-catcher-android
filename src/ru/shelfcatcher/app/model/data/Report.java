package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Report {
   private long category_id;
    private long shelf_id;
    private long[]image_ids;

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getShelf_id() {
        return shelf_id;
    }

    public void setShelf_id(long shelf_id) {
        this.shelf_id = shelf_id;
    }

    public long[] getImage_ids() {
        return image_ids;
    }

    public void setImage_ids(long[] image_ids) {
        this.image_ids = image_ids;
    }
}
