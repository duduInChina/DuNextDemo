package cn.fooltech.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/22
 *     desc   :
 * </pre>
 */
@Entity(tableName = "test_info")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
)