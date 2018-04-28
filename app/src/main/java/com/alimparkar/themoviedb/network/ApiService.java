package com.alimparkar.themoviedb.network;

import com.alimparkar.themoviedb.models.TopMovieResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for making api calls
 *
 */
public interface ApiService {

    @GET("movie/now_playing")
    Single<TopMovieResponse> getTopMovies(@Query("api_key") String apiKey,
        @Query("page") int pageNo);
}
