package bangkit.adhytia.github_user.repository

import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import retrofit2.Response

class Repository {
    suspend fun getUserList(): Response<List<User>> {
        return RetrofitInstance.api.getUserList()
    }

    suspend fun getUserDetailsByUsername(username: String): Response<User> {
        return RetrofitInstance.api.getUserDetailsByUsername(username)
    }

    suspend fun searchUserByUsername(username: String): Response<UserSearchResult> {
        return RetrofitInstance.api.searchUserByUsername(username)
    }

    suspend fun getUserFollower(username: String): Response<List<User>> {
        return RetrofitInstance.api.getUserFollower(username)
    }

    suspend fun getUserFollowing(username: String): Response<List<User>> {
        return RetrofitInstance.api.getUserFollowing(username)
    }
}