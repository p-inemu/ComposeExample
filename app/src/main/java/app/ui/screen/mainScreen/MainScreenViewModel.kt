package app.ui.screen.mainScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.AppNavigation
import app.data.db.TasksDatabase
import app.data.repo.TasksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    val appNavigation: AppNavigation
): ViewModel() {
    var repo: TasksRepo? = TasksRepo(
        TasksDatabase.get(appContext)
    )

    private val mutableState = MutableStateFlow(MainScreenState())
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            repo?.getTasksFlow()?.collect { newTasks ->
                mutableState.update {
                    it.copy(
                        tasks = newTasks
                    )
                }
            }
        }
    }

    fun handleEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.NewTaskPressed -> {
                appNavigation.toInputText(""){
                    viewModelScope.launch {
                        repo?.addTask(it, false)?.let {
                            Log.e("addTask", "addTask result $it")
                        }
                    }
                }
            }
            is MainScreenEvent.OnTaskPressed -> {
                appNavigation.toInputText(event.task.name){
                    viewModelScope.launch {
                        repo?.addOrUpdateTask(
                            event.task.copy(
                                name = it
                            )
                        )
                    }
                }
            }
            is MainScreenEvent.OnTaskCheckPressed -> {
                viewModelScope.launch {
                    repo?.addOrUpdateTask(
                        event.task.copy(
                            done = !event.task.done
                        )
                    )
                }
            }
            is MainScreenEvent.OnTaskDeletePressed -> {
                viewModelScope.launch {
                    repo?.deleteTask(event.task)
                }
            }
            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        repo = null
    }
}