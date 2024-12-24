package com.example.new_api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdviceApiService {
    @GET("advice")
    Call<Advice> getRandomAdvice();

}
