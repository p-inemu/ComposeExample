package shared.compose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Composable
fun getActivity(): Activity? = LocalContext.current.getActivity()



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun carouselAutoPlay(
    pagesCount: Int,
    interval: Long,
    animationSpec: AnimationSpec<Float> = tween(900),
    onPageChanged: ((page: Int) -> Unit)? = null,
): PagerState {
    var pageKey by remember { mutableStateOf(0) }

    val pagesInfiniteCount = remember(pagesCount) { pagesCount * 1000 }
    val pagerState = rememberPagerState(pagesInfiniteCount / 2) { pagesInfiniteCount }

    LaunchedEffect(pagerState) {
        launch {
            snapshotFlow { pagerState.currentPage }.collect { infinitePage ->
                onPageChanged?.invoke(infinitePage % pagesCount)
            }
        }
    }

    LaunchedEffect(pagerState) {
        pagerState.interactionSource.interactions.collect {
            if (it is DragInteraction.Stop) pageKey++
        }
    }

    LaunchedEffect(pageKey) {
        delay(interval)
        val newPage = (pagerState.currentPage + 1)
        pagerState.animateScrollToPage(newPage, animationSpec = animationSpec)
        pageKey++
    }

    return pagerState
}