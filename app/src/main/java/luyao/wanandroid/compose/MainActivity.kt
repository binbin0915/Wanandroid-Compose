package luyao.wanandroid.compose

import androidx.compose.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import luyao.util.ktx.base.BaseVMActivity
import luyao.wanandroid.compose.ui.WanandroidApp
import luyao.wanandroid.compose.ui.home.ArticleViewModel

class MainActivity : BaseVMActivity<ArticleViewModel>() {

    override fun providerVMClass() = ArticleViewModel::class.java

    override fun initView() {
        setContent {
            val data = +observe(mViewModel.uiState)
            data?.let { WanandroidApp(it){index ->
                when(index){
                    0 -> mViewModel.getHomeArticleList()
                    1 -> mViewModel.getSquareArticleList()
                    2 -> mViewModel.getLatestProjectList()
                }
            } }
        }
    }

    override fun initData() {
       mViewModel.getHomeArticleList()
    }
}

// general purpose observe effect. this will likely be provided by LiveData. effect API for
// compose will also simplify soon.
fun <T> observe(data: LiveData<T>) = effectOf<T?> {
    val result = +state<T?> { data.value }
    val observer = +memo { Observer<T> { result.value = it } }

    +onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }

    result.value
}


