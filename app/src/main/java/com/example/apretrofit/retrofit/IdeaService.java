package com.example.apretrofit.retrofit;

import com.example.apretrofit.models.Idea;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IdeaService {
    //retrieve all ideas
    @GET("ideas")
    Call<List<Idea>> getIdeas();

    //filtring ideas by owner  http://10.0.2.2:9000/ideas?owner=Jim
    @GET("ideas")
    Call<List<Idea>> getIdeasByOwner(@Query("owner") String owner);
/* HashMap<String,String> filters = new HashMap<>();
        filters.put("owner", "Jim");
        filters.put("count", "1");
*
* */
    //filtring ideas by owner   http://10.0.2.2:9000/ideas?owner=Jim&count=1
    @GET("ideas")
    Call<List<Idea>> getIdeasByMap(@QueryMap HashMap<String, String> filters);

    @GET("ideas/{id}")
    Call<Idea> getIdea(@Path("id")int id);

    ////////////////////////////////////////////
    @POST("ideas")
    Call<Idea> createIdea(@Body Idea idea);
    ///////////////////////////////
    @FormUrlEncoded
    @PUT("ideas/{id}")
    Call<Idea> updateIdea(@Path("id") int id,
                          @Field("description") String desc,
                          @Field("status") String status,
                          @Field("owner") String owner,
                          @Field("name") String name);
    /////////////////////////////////////////////////////////////
    @DELETE("ideas/{id}")
    Call<Void> deleteIdea(@Path("id")int id);


}
