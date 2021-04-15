package bangkit.adhytia.github_user.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.database.DatabaseContract
import bangkit.adhytia.github_user.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import bangkit.adhytia.github_user.helper.MappingHelper
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

    fun getAll(): List<User> {
        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        return MappingHelper.mapCursorToArrayList(cursor)
    }

    fun getByUsername(username: String): User? {
        val uriWithId = Uri.parse("$CONTENT_URI/$username")
        val cursor = context.contentResolver.query(uriWithId, null, null, null, null)
        return MappingHelper.mapCursorToObject(cursor)
    }

    fun insert(user: User) {
        val values = ContentValues()

        values.put(DatabaseContract.UserColumns.USERNAME, user.username)
        values.put(DatabaseContract.UserColumns.NAME, user.name)
        values.put(DatabaseContract.UserColumns.AVATAR, user.avatar)
        values.put(DatabaseContract.UserColumns.COMPANY, user.company)
        values.put(DatabaseContract.UserColumns.LOCATION, user.location)
        values.put(DatabaseContract.UserColumns.REPOSITORY, user.repository)
        values.put(DatabaseContract.UserColumns.FOLLOWERS, user.followers)
        values.put(DatabaseContract.UserColumns.FOLLOWING, user.following)

        context.contentResolver.insert(CONTENT_URI, values)
    }

    fun deleteByUsername(username: String) {
        val uriWithId = Uri.parse("$CONTENT_URI/$username")
        context.contentResolver.delete(uriWithId, null, null)
    }
}