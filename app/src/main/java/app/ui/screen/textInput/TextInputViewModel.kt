package app.ui.screen.textInput

import android.content.Context
import androidx.lifecycle.ViewModel
import app.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


private typealias ScreenState = TextInputScreenState
private typealias ScreenEvent = TextInputEvent
@HiltViewModel
class TextInputViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    val appNavigation: AppNavigation
): ViewModel() {
    private val mutableState = MutableStateFlow(ScreenState())
    val state = mutableState.asStateFlow()

    private var inited = false
    fun init(
        text: String,
        titleCustomText: String? = null,
        okButtonCustomText: String? = null,
    ){
        if(inited) return
        mutableState.update {
            it.copy(
                text = text,
                titleCustomText = titleCustomText,
                okButtonCustomText = okButtonCustomText
            )
        }
    }

    fun handleEvent(event: ScreenEvent){
        when(event){
            is TextInputEvent.NewText -> {
                mutableState.update {
                    it.copy(
                        text = event.newText
                    )
                }
            }
            is TextInputEvent.Cancel -> {
                appNavigation.back()
            }
            is TextInputEvent.Ok -> {
                appNavigation.setResults {
                    putString("text", state.value.text)
                }
                appNavigation.back()
            }
            else -> {}
        }
    }
}