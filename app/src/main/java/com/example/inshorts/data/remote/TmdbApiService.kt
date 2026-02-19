package com.example.inshorts.data.remote

import com.example.inshorts.data.remote.dto.ConfigurationDto
import com.example.inshorts.data.remote.dto.MovieDto
import com.example.inshorts.data.remote.dto.NowPlayingResponseDto
import com.example.inshorts.data.remote.dto.SearchResponseDto
import com.example.inshorts.data.remote.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationDto

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): TrendingResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): NowPlayingResponseDto

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): SearchResponseDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDto
}
