package app.ui.screen.textInput


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import shared.compose.BottomSheet
import shared.compose.ClickableRect
import shared.compose.Spacer

data class TextInputScreenState(
    val text: String = "",
    val titleCustomText: String? = null,
    val okButtonCustomText: String? = null,
)

typealias TextInputEventHandler = (TextInputEvent) -> Unit
sealed class TextInputEvent {
    data class NewText(val newText: String): TextInputEvent()
    data object Cancel: TextInputEvent()
    data object Ok: TextInputEvent()
}

@Preview(widthDp = 350, heightDp = 400)
@Composable
private fun Preview() {
    Dialog(onDismissRequest = {}) {
        Box(Modifier.fillMaxSize()){
            BottomSheet(Modifier.align(Alignment.BottomCenter)) {
                TextInputScreen(TextInputScreenState()){}
            }
        }
    }
}

@Composable
fun TextInputScreen(viewModel: TextInputViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    TextInputScreen(state, viewModel::handleEvent)
}

@Composable
fun TextInputScreen(state: TextInputScreenState, eventHandler: TextInputEventHandler) {
    Column(Modifier.padding(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.titleCustomText ?: "Enter text",
            color = Color(0xff10275A),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )
        Spacer(size = 20.dp)
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp)),
            value = state.text,
            textStyle = TextStyle(
                color = Color(0xff10275A),
                fontSize = 16.sp,
            ),
            singleLine = true,
            onValueChange = {
                eventHandler.invoke(TextInputEvent.NewText(it))
            },
            decorationBox = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffF6F6F6), RoundedCornerShape(20.dp))
                    .border(1.4.dp, Color(0xff5669FF), RoundedCornerShape(20.dp))
                    .padding(start = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    it()
                }
            }
        )
        Spacer(size = 20.dp)
        Row {
            Box(modifier = Modifier
                .height(50.dp)
                .weight(1f)
                .border(1.4.dp, Color(0xff8A8BB3), shape = RoundedCornerShape(10.dp))
                .clickable {
                    eventHandler.invoke(TextInputEvent.Cancel)
                },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Cancel",
                    color = Color(0xFF9AA8C7),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
            }
            Spacer(size = 20.dp)
            ClickableRect(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                radius = 10.dp,
                bg = Color(0xFF5669FF),
                onClick = {
                    eventHandler.invoke(TextInputEvent.Ok)
                }
            ){
                Text(
                    text = state.okButtonCustomText ?: "OK",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}