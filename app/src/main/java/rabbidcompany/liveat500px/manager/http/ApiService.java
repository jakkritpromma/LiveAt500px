package rabbidcompany.liveat500px.manager.http;

import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by noneemotion on 14/7/2559.
 */
public interface ApiService {
    //Post to the remaining part (list) of the Url
    //http://nuuneoi.com/courses/500px/list
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList(); //Declare a method. <Object> is generic.

    //The service from the server is needed.
    @POST("list/after/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListAfterId(@Path("id") int id);

    //This is a service for load more (i.e. endless scrolling down).
    @POST("list/before/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListBeforeId(@Path("id") int id);
}
