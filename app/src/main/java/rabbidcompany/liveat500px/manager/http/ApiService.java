package rabbidcompany.liveat500px.manager.http;

import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by noneemotion on 14/7/2559.
 */
public interface ApiService {
    //Post to the remaining part (list) of the Url
    //http://nuuneoi.com/courses/500px/list
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList(); //Declare a method's name. <Object> is generic.
}
