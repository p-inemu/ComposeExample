package app

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.qualifiers.ApplicationContext
import shared.Navigation
import javax.inject.Inject

class AppNavigation @Inject constructor(
    @ApplicationContext val appContext: Context
): Navigation(){

    fun toInputText(
        text: String,
        onResult: (newText: String) -> Unit
    ){
        to(route = "inputText",
            args = Bundle().apply {
                putString("text", text)
            },
            onResult = {
                it?.getString("text")?.let {
                    onResult(it)
                }
            }
        )
    }
}