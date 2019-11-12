package luyao.wanandroid.compose.model.repository

import luyao.wanandroid.compose.model.api.BaseRepository
import luyao.wanandroid.compose.model.api.WanRetrofitClient
import luyao.wanandroid.compose.model.bean.ArticleList
import luyao.wanandroid.compose.model.bean.Result
import luyao.wanandroid.compose.util.isSuccess
import java.io.IOException

/**
 * Created by luyao
 * on 2019/10/15 10:33
 */
class SquareRepository :  BaseRepository(){


    suspend fun getSquareArticleList(page:Int): Result<ArticleList> {
        return safeApiCall(call = {requestSquareArticleList(page)},errorMessage = "网络异常")
    }

    private suspend fun requestSquareArticleList(page: Int): Result<ArticleList> {
        val response = WanRetrofitClient.service.getSquareArticleList(page)
        return executeResponse(response.execute())

    }
}