package luyao.wanandroid.compose.ui

import androidx.compose.Model

/**
 * Created by luyao
 * on 2019/11/12 10:26
 */
sealed class Screen {
    object Home : Screen()
    object About : Screen()
}


@Model
object WanandroidStatus {
    var currentScreen: Screen = Screen.Home
}

fun navigateTo(destination: Screen) {
    WanandroidStatus.currentScreen = destination
}