package bangkit.adhytia.github_user.api

import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
    @GET("users")
    suspend fun getUserList(): Response<List<User>>

    @GET("users/{username}")
    suspend fun getUserDetailsByUsername(
        @Path("username") username: String
    ): Response<User>

    @GET("search/users")
    suspend fun searchUserByUsername(
        @Query("q") q: String
    ): Response<UserSearchResult>
}