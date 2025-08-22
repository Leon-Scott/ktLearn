/*
package com.android.myapplication

*/
/**
 * @author  longbin
 * @date 2024/10/21
 *//*


class SystemViewModel : BaseViewModel(){
    private val remoteRepository : SystemRemoteRepository by lazy { SystemRemoteRepository() }
    val page = MutableLiveData<Pagination<Article>>()

    fun getArticleList() {
        viewModelScope.launch {  //主线程开启一个协程
            // 网络请求：IO线程
            val tree : ApiResult<MutableList<Tree>> = RetrofitClient.apiService.getTreeByCoroutines()
            // 主线程
            val cid = tree?.data?.get(0)?.id
            if(cid!=null){
                // 网络请求：IO线程
                val pageResult : ApiResult<Pagination<Article>> = RetrofitClient.apiService.getArticleListByCoroutines(0, cid)
                // 主线程
                page.value = pageResult.data!!
            }
        }
    }
}

*/
/**接口定义，Retrofit从2.6.0版本开始支持协程*//*

interface ApiService {
    */
/*获取文章树结构*//*

    @GET("tree/json")
    suspend fun getTreeByCoroutines(): ApiResult<MutableList<Tree>>
    */
/*根据数结构下某个分支id，获取分支下的文章*//*

    @GET("article/list/{page}/json")
    suspend fun getArticleListByCoroutines(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>
}*/
