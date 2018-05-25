package com.example.han.testtranslator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by per6 on 5/23/18.
 */

public interface WordsAPI {

    String baseUrl = "https://wordsapiv1.p.mashape.com/";
    String API_KEY = "4Yo98HCCBimsh7xLLcEyag2ogcbLp1fWq3fjsn9TbmUl0Bb7gO";

    @Headers("X-Mashape-Key: "+ API_KEY)
    @GET("words/{word}/definitions")
    Call<DefinitionList> getDefinitions(@Path("word") String word);



}
