package ru.shelfcatcher.app.model.operation.netowrk;
import retrofit.http.*;
import ru.shelfcatcher.app.model.data.Category;
import ru.shelfcatcher.app.model.data.Store;
import ru.shelfcatcher.app.model.data.User;

/**
 * Created by gadfil on 09.09.2014.
 */
public interface Api {
    public static final String BASE_URL = "http://shelf-catcher.herokuapp.com/api/v1";
    public static final String LOGIN = "/login";
    public static final String REPORTS = "reports";
    public static final String STORES = "/stores";
    public static final String CATEGORIES = "/categories";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    @FormUrlEncoded
    @POST(LOGIN)
    public User login(@Field(KEY_LOGIN) String login,  @Field(KEY_PASSWORD) String password);

    @GET(STORES)
    public Store[] getStores();

    @GET(CATEGORIES)
    public Category[] getCategories();



}
