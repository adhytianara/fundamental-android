package bangkit.adhytia.github_user.api

import bangkit.adhytia.github_user.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserApi {
    @GET("users")
    suspend fun getUserList(): Response<List<User>>

    @GET("users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): Response<User>
}