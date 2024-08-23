package cn.fooltech.database.di

import android.content.Context
import androidx.room.Room
import cn.fooltech.database.AppDatabase
import com.dudu.database.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/22
 *     desc   :
 * </pre>
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context:Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()

}