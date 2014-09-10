package ru.shelfcatcher.app.model.operation.netowrk;

import retrofit.http.*;
import ru.shelfcatcher.app.model.data.*;

/**
 * Created by gadfil on 09.09.2014.
 */
public interface Api {
    public static final String BASE_URL = "http://shelf-catcher.herokuapp.com/api/v1";
    public static final String LOGIN = "/login";
    public static final String USERS_ME = "/users/me";
    public static final String REPORTS = "reports";
    public static final String STORES = "/stores";
    public static final String SHELVES = "/shelves";
    public static final String CATEGORIES = "/categories";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";

    @FormUrlEncoded
    @POST(LOGIN)
    public ResponseUserObject login(@Field(KEY_LOGIN) String login, @Field(KEY_PASSWORD) String password);

    @GET(USERS_ME)
    public ResponseUserObject userMe(@Query(KEY_TOKEN) String token);

    @GET(STORES)
    public ResponseStoreArray getStores(@Query(KEY_TOKEN) String token);

    @GET(CATEGORIES)
    public ResponseCategoryArray getCategories( @Query(KEY_TOKEN) String token);

    @GET(STORES+"/{id}"+SHELVES)
    public ResponseShelvesArray getShelves(@Path("id") String id, @Query(KEY_TOKEN) String token);


}
