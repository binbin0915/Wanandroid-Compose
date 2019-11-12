package luyao.wanandroid.compose.ui

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import luyao.wanandroid.compose.R
import luyao.wanandroid.compose.ui.home.ArticleViewModel
import luyao.wanandroid.compose.ui.home.HomeScreen

/**
 * Created by luyao
 * on 2019/11/12 10:17
 */


@Composable
fun WanandroidApp(articleUiModel: ArticleViewModel.ArticleUiModel, onTabSelected: (Int) -> Unit) {

    val (drawerState, onDrawerStateChange) = +state { DrawerState.Closed }

    MaterialTheme(
        colors = lightThemeColors,
        typography = themeTypography
    ) {
        ModalDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            drawerContent = {
                AppDrawer(currentScreen = WanandroidStatus.currentScreen,
                    closeDrawer = { onDrawerStateChange(DrawerState.Closed) })
            },
            bodyContent = {
                AppContent(
                    articleUiModel,
                    { onDrawerStateChange(DrawerState.Opened) },
                    onTabSelected = onTabSelected
                )
            }
        )
    }
}

@Composable
private fun AppDrawer(
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    Column(
        crossAxisSize = LayoutSize.Expand,
        mainAxisSize = LayoutSize.Expand
    ) {
        HeightSpacer(height = 24.dp)
        Padding(padding = 16.dp) {
            Row {
                VectorImage(
                    resId = R.drawable.ic_android,
                    tint = +themeColor { primary }
                )
            }
        }
        Divider(color = Color(0x14333333))
        DrawerButton(
            icon = R.drawable.ic_home,
            label = "主页",
            isSelected = currentScreen == Screen.Home,
            action = {
                navigateTo(Screen.Home)
                closeDrawer()
            }
        )
        DrawerButton(
            icon = R.drawable.ic_home,
            label = "关于",
            isSelected = currentScreen == Screen.About
        ) {
            navigateTo(Screen.About)
            closeDrawer()
        }
    }

}

@Composable
private fun AppContent(
    articleUiModel: ArticleViewModel.ArticleUiModel,
    openDrawer: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    Crossfade(WanandroidStatus.currentScreen) { screen ->
        Surface(color = +themeColor { background }) {
            when (screen) {
                is Screen.Home -> HomeScreen(
                    articleUiModel,
                    openDrawer = { openDrawer() },
                    onTabSelected = onTabSelected
                )
                is Screen.About -> HomeScreen(
                    articleUiModel,
                    openDrawer = { openDrawer() },
                    onTabSelected = onTabSelected
                )
            }
        }
    }
}

@Composable
private fun DrawerButton(
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean,
    action: () -> Unit
) {
    val textIconColor = if (isSelected) {
        +themeColor { primary }
    } else {
        (+themeColor { onSurface }).copy(alpha = 0.6f)
    }

    val backgroundColor = if (isSelected) {
        (+themeColor { primary }).copy(alpha = 0.12f)
    } else {
        +themeColor { surface }
    }

    Padding(left = 8.dp, top = 8.dp, right = 8.dp) {
        Surface(
            color = backgroundColor,
            shape = RoundedCornerShape(4.dp)
        ) {
            Button(onClick = action, style = TextButtonStyle()) {
                Row(
                    mainAxisSize = LayoutSize.Expand,
                    crossAxisAlignment = CrossAxisAlignment.Center
                ) {
                    VectorImage(resId = icon, tint = textIconColor)
                    WidthSpacer(width = 16.dp)
                    Text(
                        text = label,
                        style = (+themeTextStyle { body2 }).copy(color = textIconColor)
                    )
                }
            }

        }
    }
}