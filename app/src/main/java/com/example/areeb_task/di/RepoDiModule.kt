package com.example.areeb_task.di

import com.example.areeb_task.data.remote.GithubReposService
import com.example.areeb_task.data.repository.UserRepositoryImpl
import com.example.areeb_task.domian.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoDiModule {

    @Provides
    @Singleton
    fun provideRepoApi() : GithubReposService {
        val authToken = "Bearer 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"
        val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor {
                    val original = it.request()
                    val request = original.newBuilder()
                        .header("Authorization",authToken)
                        .method(original.method,original.body)
                        .build()
                    it.proceed(request)
                }
                .build()
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(GithubReposService::class.java)
        }
        return api
    }
    @Provides
    @Singleton
    fun provideRepository(
        api: GithubReposService
    ):UserRepository{
        return UserRepositoryImpl(api)
    }
}