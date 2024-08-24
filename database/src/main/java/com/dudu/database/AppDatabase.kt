package cn.fooltech.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.fooltech.database.dao.TestDao
import cn.fooltech.database.model.TestEntity

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/22
 *     desc   :
 * </pre>
 */
@Database(
    version = 1,
    entities = [TestEntity::class],
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
}