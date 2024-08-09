package app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.data.db.dao.TodoTasksDAO
import app.data.db.entity.TodoTask

@Database(
    version = 1,
    entities = [
        TodoTask::class,
    ],
)
abstract class TasksRoomDatabase : RoomDatabase(){
    abstract fun tasksDAO(): TodoTasksDAO
}

class TasksDatabase private constructor(
    val db: TasksRoomDatabase
){
    companion object {
        private var inst: TasksDatabase? = null
        fun get(context: Context): TasksDatabase{
            if(inst == null){
                val db = Room.databaseBuilder(context.applicationContext,
                    TasksRoomDatabase::class.java,
                    "TasksRoomDatabase")
                    .build()
                inst = TasksDatabase(db)
            }
            return inst!!
        }

        fun close(){
            inst?.close()
            inst = null
        }
    }

    val tasksDAO = db.tasksDAO()

    fun close(){
        db.close()
    }
}
