package com.tistory.nittaku.rx_retrofit2;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
/*
http://bis.naju.go.kr:8080/json/arriveAppInfo?BUSSTOP_ID=1439
        */
public interface Retrofit2 {

    @GET("json/arriveAppInfo")
    Observable<Bus> getData(@Query("BUSSTOP_ID") int busstop_id);
}
