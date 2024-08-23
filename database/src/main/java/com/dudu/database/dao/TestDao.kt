package cn.fooltech.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cn.fooltech.database.model.TestEntity

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/22
 *     desc   :
 * </pre>
 */
@Dao
interface TestDao {

    @Insert
    suspend fun insert(testBean: TestEntity)

    @Query("select * from test_info limit :pageSize offset (:page * :pageSize)")
    suspend fun getAll(page: Int, pageSize: Int): List<TestEntity>

}