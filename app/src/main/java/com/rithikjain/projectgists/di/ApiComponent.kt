package com.rithikjain.projectgists.di

import com.rithikjain.projectgists.BuildConfig
import com.rithikjain.projectgists.api.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/"

@Module
@InstallIn(ApplicationComponent::class)
object ApiComponent {
  @Provides
  @Singleton
  fun providesRetrofitService(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
}

@Module
@InstallIn(ApplicationComponent::class)
object WebServiceModule {
  @Provides
  fun providesWebService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)
}

@Module
@InstallIn(ApplicationComponent::class)
object OkHttpModule {
  @Provides
  fun providesOkHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpClient.addInterceptor(
        httpLoggingInterceptor.apply {
          httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        }
      )
    }

    return httpClient.readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(60, TimeUnit.SECONDS)
      .build()
  }
}