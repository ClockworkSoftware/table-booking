package org.clockwork.tablebooking.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.clockwork.tablebooking.network.auth.AuthApiService
import org.clockwork.tablebooking.network.reservation.ReservationApiService
import org.clockwork.tablebooking.network.util.InstantTypeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(interceptor: AuthInterceptor, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tablebooking.dragonmadness-home.crazedns.ru")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthSession(): AuthSession {
        return AuthSession
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReservationRepository(retrofit: Retrofit): ReservationApiService {
        return retrofit.create(ReservationApiService::class.java)
    }
}