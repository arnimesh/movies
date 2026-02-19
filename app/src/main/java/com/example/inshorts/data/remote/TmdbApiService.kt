package com.example.inshorts.data.remote

import com.example.inshorts.data.remote.dto.ConfigurationDto
import com.example.inshorts.data.remote.dto.MovieDto
import com.example.inshorts.data.remote.dto.NowPlayingResponseDto
import com.example.inshorts.data.remote.dto.SearchResponseDto
import com.example.inshorts.data.remote.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for the TMDB API (v3).
 *
 * Base URL: https://api.themoviedb.org/3/
 * All requests require an API key; we add it via an interceptor (see [TmdbApiProvider]) so it's not in every method.
 */
interface TmdbApiService {

    /** GET /configuration — image base URL and sizes (e.g. poster w500). */
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationDto

    /** GET /trending/movie/day — trending movies. */
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): TrendingResponseDto

    /** GET /movie/now_playing — movies currently in theatres. */
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): NowPlayingResponseDto

    /** GET /search/movie — search by query. */
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): SearchResponseDto

    /** GET /movie/{id} — full movie details (genres, runtime, etc.). */
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDto
}
