package luyao.wanandroid.compose.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.core.content.ContextCompat.startActivity
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import luyao.util.ktx.ext.toast
import luyao.wanandroid.compose.App
import luyao.wanandroid.compose.R
import luyao.wanandroid.compose.model.bean.Article
import luyao.wanandroid.compose.ui.VectorImage

/**
 * Created by luyao
 * on 2019/11/12 11:18
 */
@Composable
fun MainTab(articleUiModel: ArticleViewModel.ArticleUiModel?) {

    VerticalScroller {
        FlexColumn {
            inflexible {
                HeightSpacer(height = 16.dp)
            }
            flexible(1f) {
                articleUiModel?.showSuccess?.datas?.forEach {
                    ArticleItem(article = it)
                }

                articleUiModel?.showError?.let { toast(App.CONTEXT, it) }

                articleUiModel?.showLoading?.let { Progress() }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article) {
    val image = +imageResource(R.drawable.home_hot)
    val timeImage = +imageResource(R.drawable.ic_time)

    Padding(left = 16.dp, right = 16.dp) {

        Button(
            style = ButtonStyle(
                color = Color.Transparent,
                shape = +themeShape { button }
            ),
            onClick = { openUrl(article.link) }
        ) {
            Column {
                FlexRow {
                    inflexible {
                        Container(width = 14.dp, height = 14.dp) {
                            DrawImage(image)
                        }
                    }
                    inflexible {
                        Padding(left = 10.dp) {
                            Text(
                                text = if (article.author.isBlank()) "分享者: ${article.shareUser}" else article.author,
                                style = +themeTextStyle { caption }
                            )
                        }
                    }
                    inflexible {
                        Padding(left = 10.dp) {
                            Text(
                                text = "${article.superChapterName}/${article.chapterName}",
                                style = +themeTextStyle { caption }
                            )
                        }
                    }
                }

                FlexRow {
                    expanded(1f) {
                        Padding(top = 4.dp, bottom = 4.dp) {
                            Text(
                                text = article.title,
                                style = +themeTextStyle { body1 }
                            )
                        }
                    }
                }

                FlexRow {
                    inflexible {
                        Container(width = 14.dp, height = 14.dp) {
                            DrawImage(timeImage)
                        }
                    }
                    inflexible {
                        Padding(left = 10.dp) {
                            Text(
                                text = article.niceDate,
                                style = +themeTextStyle { caption }
                            )
                        }
                    }
                    inflexible {
                        Padding(left = 10.dp) {
                            Container(width = 14.dp, height = 14.dp) {
                                ArticleStarButton(isSelected = article.collect, onStarClick = {

                                })
                            }

                        }
                    }
                }

                HeightSpacer(height = 16.dp)
            }
        }

    }
}

@Composable
private fun ArticleStarButton(
    isSelected: Boolean,
    onStarClick: (Boolean) -> Unit
) {
    Ripple(bounded = false, radius = 24.dp) {
        Toggleable(checked = isSelected, onCheckedChange = onStarClick) {
            Container(width = 24.dp, height = 24.dp) {
                if (isSelected)
                    VectorImage(resId = R.drawable.ic_bookmarked)
                else
                    VectorImage(resId = R.drawable.ic_bookmark)
            }
        }
    }

}

@Composable
fun Progress() {
    Center {
        CircularProgressIndicator()
    }
}

private fun openUrl(url:String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    App.CONTEXT.startActivity(intent)

}