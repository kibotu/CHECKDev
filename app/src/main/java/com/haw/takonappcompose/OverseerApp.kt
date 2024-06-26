package com.haw.takonappcompose

import android.app.Application
import androidx.room.Room
import com.haw.takonappcompose.database.AppDatabase
import com.haw.takonappcompose.json.json
import com.haw.takonappcompose.network.Api
import com.haw.takonappcompose.overseer.Overseer
import com.haw.takonappcompose.repositories.Repository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OverseerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            modules(module {
                single {
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                }
                single {
                    OkHttpClient.Builder().apply {
                        // set timeouts
                        connectTimeout(60, TimeUnit.SECONDS)
                        readTimeout(60, TimeUnit.SECONDS)
                        writeTimeout(60, TimeUnit.SECONDS)
                        retryOnConnectionFailure(true)
                        addInterceptor(
                            HttpLoggingInterceptor {
                                Timber.tag("Ollama").v(it)
                            }.apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                    }.build()
                }
                single {
                    Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:11434/")
                        .client(get())
                        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                        .build()
                }
                single {
                    val retrofit: Retrofit = get()
                    retrofit.create(Api::class.java)
                }
                single {
                    Room
                        .databaseBuilder(
                            this@OverseerApp,
                            AppDatabase::class.java,
                            "db_takon"
                        ).fallbackToDestructiveMigration()
                        .build()
                }
                single {
                    val api: Api = get()
                    val database: AppDatabase = get()

                    Repository(
                        api = api,
                        answerDao = database.answerDao(),
                        roleDao = database.roleDao(),
                        scenarioDao = database.scenarioDao(),
                        phaseDao = database.phaseDao(),
                        actionDao = database.actionDao(),
                    )

                }
                single {
                    Overseer(get())
                }
            })
        }

    }

}