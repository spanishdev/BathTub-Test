package com.jordic.bathtubtest.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jordi on 01/01/2017.
 */

public interface BathService {

    @GET("/bath.json")
    Call<ResponseBody> loadTemperatures();
}
