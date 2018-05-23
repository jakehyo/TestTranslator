package com.example.han.testtranslator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by per6 on 5/23/18.
 */

public interface WordsAPI {
    String baseUrl = "";

    @GET("words")
    Call<DefinitionList> getJokeList(@Query("definitions") String search);

}
