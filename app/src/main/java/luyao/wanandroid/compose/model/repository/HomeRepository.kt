package luyao.wanandroid.compose.model.repository

import luyao.wanandroid.compose.model.api.BaseRepository
import luyao.wanandroid.compose.model.api.WanRetrofitClient
import luyao.wanandroid.compose.model.bean.ArticleList
import luyao.wanandroid.compose.model.bean.Banner
import luyao.wanandroid.compose.model.bean.Result
import luyao.wanandroid.compose.util.executeResponse

/**
 * Created by luyao
 * on 2019/4/10 14:09
 */
class HomeRepository : BaseRepository() {

    suspend fun getBanners(): Result<List<Banner>> {
        return safeApiCall(call = {requestBanners()},errorMessage = "")
    }

    private suspend fun requestBanners(): Result<List<Banner>> =
        executeResponse(WanRetrofitClient.service.getBanner())


    suspend fun getArticleList(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "网络错误")
    }

    private suspend fun requestArticleList(page: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.getHomeArticles(page).execute())
}