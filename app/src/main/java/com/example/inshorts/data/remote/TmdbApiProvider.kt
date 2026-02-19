package com.example.inshorts.data.remote

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Builds a [TmdbApiService] instance with the given API key.
 *
 * The key is added to every request as a query parameter (api_key=...) via an OkHttp interceptor,
 * so the Retrofit interface stays clean. Call this from the app (e.g. Application or DI) with
 * [BuildConfig.TMDB_API_KEY].
 */
object TmdbApiProvider {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val PARAM_API_KEY = "api_key"

    fun create(apiKey: String): TmdbApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TmdbApiService::class.java)
    }

    /**
     * Interceptor that adds the API key to every request URL as a query parameter.
     * TMDB requires this for all endpoints.
     */
    private class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter(PARAM_API_KEY, apiKey)
                .build()
            return chain.proceed(original.newBuilder().url(url).build())
        }
    }
}
