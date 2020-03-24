package com.example.retrofitexample;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DataService {
    @GET("/photos")
    Observable<List<Photo>> getAllPhotos();
}
