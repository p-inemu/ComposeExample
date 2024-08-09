package app.ui.screen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.data.db.entity.TodoTask
import app.ui.element.TaskCard
import shared.compose.Spacer
import shared.compose.clickableRipple

data class MainScreenState(
    val tasks: List<TodoTask> = listOf()
)

typealias MainScreenEventHandler = (MainScreenEvent) -> Unit
sealed class MainScreenEvent{
    data class OnTaskPressed(val task: TodoTask): MainScreenEvent()
    data class OnTaskCheckPressed(val task: TodoTask): MainScreenEvent()
    data class OnTaskDeletePressed(val task: TodoTask): MainScreenEvent()
    data object NewTaskPressed: MainScreenEvent()
}

@Composable
@Preview(widthDp = 400, heightDp = 800)
private fun Preview() {
    MainScreen(
        state = MainScreenState(
            tasks = listOf(
                TodoTask("Task 1"),
                TodoTask("Task 2"),
                TodoTask("Task 3", true),
                TodoTask("Task 4"),
            )
        ),
        eventHandler = {}
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    MainScreen(modifier, state, viewModel::handleEvent)
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    eventHandler: MainScreenEventHandler
) {
    Column(
        modifier
            .background(Color(0xFFEDFCD0))
            .systemBarsPadding()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Todo List",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                val stroke = remember{
                    Stroke(width = 16f,
                        pathEffect = PathEffect
                            .dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
                Box(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFF73B873),
                                style = stroke,
                                cornerRadius = CornerRadius(16.dp.toPx())
                            )
                        }.clickableRipple {
                            eventHandler(MainScreenEvent.NewTaskPressed)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+",
                        style = TextStyle(
                            color = Color(0xFF73B873),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                Spacer(size = 16.dp)
            }
            items(state.tasks.size){
                val taskData = state.tasks[it]
                TaskCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    taskData = taskData,
                    onCheckPressed = {
                        eventHandler(MainScreenEvent.OnTaskCheckPressed(taskData))
                    },
                    onDeletePressed = {
                        eventHandler(MainScreenEvent.OnTaskDeletePressed(taskData))
                    },
                    onPressed = {
                        eventHandler(MainScreenEvent.OnTaskPressed(taskData))
                    }
                )
                Spacer(size = 16.dp)
            }
            item {
                Spacer(size = 200.dp)
            }
        }
    }
}