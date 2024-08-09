package app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.data.db.entity.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTasksDAO {
    @Query("SELECT * FROM TodoTask WHERE id = :id")
    fun getById(id: Long): TodoTask?
    @Query("SELECT * FROM TodoTask ORDER BY id DESC LIMIT :pageSize OFFSET (:row-1)*:pageSize")
    fun getByRow(row: Long, pageSize: Int = 1): TodoTask?

    @Query("SELECT * FROM TodoTask")
    fun getAll(): Flow<List<TodoTask>>

    @Insert
    suspend fun insert(data: TodoTask): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(data: TodoTask): Long
    @Query("DELETE FROM TodoTask WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT Count(id) FROM TodoTask")
    fun getRowsCount(): Int
    @Query("SELECT id FROM TodoTask ORDER BY id DESC LIMIT 1")
    fun getLastId(): Int
}