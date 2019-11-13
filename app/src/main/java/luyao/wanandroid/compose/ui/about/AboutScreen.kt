package luyao.wanandroid.compose.ui.about

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.DrawImage
import androidx.ui.layout.*
import androidx.ui.material.TopAppBar
import androidx.ui.material.themeTextStyle
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import luyao.wanandroid.compose.R
import luyao.wanandroid.compose.ui.VectorImageButton

/**
 * Created by luyao
 * on 2019/11/13 14:19
 */
@Composable
fun AboutScreen(openDrawer: () -> Unit) {

    val image = +imageResource(R.drawable.wechat)

    FlexColumn {
        inflexible {
            TopAppBar(title = { Text("关于") },
                navigationIcon = {
                    VectorImageButton(id = R.drawable.ic_drawer, onClick = { openDrawer() })
                })
        }

        expanded(1f) {
            Center {
                Column(crossAxisAlignment = CrossAxisAlignment.Center) {


                    Container(width = 160.dp, height = 160.dp, alignment = Alignment.Center) {
                        DrawImage(image = image)
                    }

                    Padding(left = 28.dp,right = 28.dp,top = 20.dp) {
                        Text( style = +themeTextStyle { h6 }, text = "更多最新内容，扫码关注公众号 秉心说 ！")
                    }

                    Padding(left = 28.dp,right = 28.dp,top = 20.dp) {
                        Text(style = +themeTextStyle { body1 },text = "添加微信 bingxinshuo_ ，加入 Jetpack 讨论群。")
                    }
                }
            }
        }
    }
}