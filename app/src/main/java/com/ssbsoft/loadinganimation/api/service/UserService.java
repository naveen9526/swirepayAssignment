package com.ssbsoft.loadinganimation.api.service;

import com.ssbsoft.loadinganimation.model.Page;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("users?delay=3")
    Call<Page> getUser();
}
