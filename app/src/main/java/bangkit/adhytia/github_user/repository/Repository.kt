package bangkit.adhytia.github_user.repository

import android.content.ContentValues
import android.content.Context
import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.database.DatabaseContract
import bangkit.adhytia.github_user.helper.MappingHelper
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import bangkit.adhytia.github_user.database.UserDatabaseHelper
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

    fun getAll(): List<User>? {
        val userHelper = UserDatabaseHelper.getInstance(context)
        userHelper.open()
        val cursor = userHelper.queryAll()
        val userList = MappingHelper.mapCursorToArrayList(cursor)
        userHelper.close()
        return userList
    }

    fun getByUsername(username: String): User? {
        val userHelper = UserDatabaseHelper.getInstance(context)
        userHelper.open()
        val cursor = userHelper.getByUsername(username)
        val user = MappingHelper.mapCursorToObject(cursor)
        userHelper.close()
        return user
    }

    fun insert(user: User): Long {
        val values = ContentValues()

        values.put(DatabaseContract.UserColumns.USERNAME, user.username)
        values.put(DatabaseContract.UserColumns.NAME, user.name)
        values.put(DatabaseContract.UserColumns.AVATAR, user.avatar)
        values.put(DatabaseContract.UserColumns.COMPANY, user.company)
        values.put(DatabaseContract.UserColumns.LOCATION, user.location)
        values.put(DatabaseContract.UserColumns.REPOSITORY, user.repository)
        values.put(DatabaseContract.UserColumns.FOLLOWERS, user.followers)
        values.put(DatabaseContract.UserColumns.FOLLOWING, user.following)

        val userHelper = UserDatabaseHelper.getInstance(context)
        userHelper.open()
        val res = userHelper.insert(values)
        userHelper.close()
        return res
    }

    fun deleteByUsername(username: String): Int {
        val userHelper = UserDatabaseHelper.getInstance(context)
        userHelper.open()
        val res = userHelper.deleteByUsername(username)
        userHelper.close()
        return res
    }
}