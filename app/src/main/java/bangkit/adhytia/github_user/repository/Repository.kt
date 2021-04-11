package bangkit.adhytia.github_user.repository

import android.content.Context
import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.database.AppDatabase
import bangkit.adhytia.github_user.database.UserDao
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import retrofit2.Response

class Repository(val context: Context) {

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

    suspend fun getAll(): List<User>? {
        return AppDatabase.getInstance(context)?.userDao()?.getAll()
    }

    suspend fun findByUsername(username: String): User? {
        return AppDatabase.getInstance(context)?.userDao()?.findByUsername(username)
    }

    suspend fun insertAll(vararg users: User): Unit? {
        return AppDatabase.getInstance(context)?.userDao()?.insertAll(*users)
    }

    suspend fun delete(user: User): Unit? {
        return AppDatabase.getInstance(context)?.userDao()?.delete(user)
    }
}