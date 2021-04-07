package bangkit.adhytia.github_user.repository

import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.model.User
import retrofit2.Response

class Repository {
    suspend fun getUserList(): Response<List<User>> {
        return RetrofitInstance.api.getUserList()
    }

    suspend fun getUserByUsername(username: String): Response<User> {
        return RetrofitInstance.api.getUserByUsername(username)
    }
}