package cn.fooltech.database.di

import cn.fooltech.database.AppDatabase
import cn.fooltech.database.dao.TestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/22
 *     desc   :
 * </pre>
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesTestInfoDao(
        database: AppDatabase,
    ): TestDao = database.testDao()

}