package app.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.data.db.entity.TodoTask
import shared.compose.ClickableCircle
import shared.compose.Spacer
import shared.compose.clickableRipple

@Composable
@Preview(widthDp = 400, heightDp = 60)
private fun Preview(){
    TaskCard(
        taskData = TodoTask("Task 1", false),
        onCheckPressed = {},
        onDeletePressed = {},
        onPressed = {},
    )
}
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    taskData: TodoTask,
    onCheckPressed: () -> Unit,
    onDeletePressed: () -> Unit,
    onPressed: () -> Unit
) {
    Row(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFC2F79C))
            .clickableRipple {
                onPressed()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(size = 8.dp)
        Checkbox(checked = taskData.done, onCheckedChange = {
            onCheckPressed()
        })
        Spacer(size = 8.dp)
        Text(modifier = Modifier.weight(1f),
            text = taskData.name
        )
        ClickableCircle(
            Modifier.size(60.dp),
            bg = Color.Transparent,
            onClick = {
                onDeletePressed()
            }
        ){
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
        }
    }
}