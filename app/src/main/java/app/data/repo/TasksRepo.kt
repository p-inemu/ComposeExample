package app.data.repo

import app.data.db.TasksDatabase
import app.data.db.entity.TodoTask
import kotlinx.coroutines.flow.Flow

class TasksRepo(val db: TasksDatabase) {

    // ADD TASK
    suspend fun addTask(name: String, done: Boolean): Boolean{
        val newTask = TodoTask(name, done)
        return addOrUpdateTask(newTask)
    }
    suspend fun addOrUpdateTask(newTodoTask: TodoTask): Boolean{
        val newId = if(newTodoTask.id < 0)
            db.tasksDAO.insert(newTodoTask)
        else
            db.tasksDAO.insertOrUpdate(newTodoTask)
        return newId >= 0
    }

    // GET TASK COUNT
    fun getTasksCount(): Int {
        return db.tasksDAO.getRowsCount()
    }

    // GET TASK
    fun getTaskById(taskId: Long): TodoTask?{
        return db.tasksDAO.getById(taskId)
    }
    fun getTasksFlow(): Flow<List<TodoTask>>? {
        return db.tasksDAO.getAll()
    }

    // DELETE
    suspend fun deleteTaskById(taskId: Long){
        db.tasksDAO.deleteById(taskId)
    }
    suspend fun deleteTask(todoTask: TodoTask){
        deleteTaskById(todoTask.id)
    }
}