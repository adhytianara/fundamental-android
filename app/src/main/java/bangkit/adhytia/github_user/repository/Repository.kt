package bangkit.adhytia.github_user.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import bangkit.adhytia.github_user.api.RetrofitInstance
import bangkit.adhytia.github_user.database.AppDatabase
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import bangkit.adhytia.github_user.provider.GithubUserProvider
import retrofit2.Response

class Repository(val context: Context) {
    private var mContentResolver: ContentResolver? = null

    init {
        mContentResolver = context.contentResolver
    }

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

    suspend fun getAll(): Cursor {
        return AppDatabase.getInstance(context)!!.userDao().selectAll()
    }

    suspend fun findById(id: Long): Cursor {
        return AppDatabase.getInstance(context)!!.userDao().selectById(id)
    }

    suspend fun insert(user: User): Long {
        val values = ContentValues()
        values.put("username", user.username)
        values.put("name", user.name)
        values.put("avatar_url", user.avatar_url)
        values.put("company", user.company)
        values.put("location", user.location)
        values.put("repository", user.repository)
        values.put("followers", user.followers)
        values.put("following", user.following)
        val uri = mContentResolver?.insert(
            GithubUserProvider.URI_CHEESE,
            values
        )
        return ContentUris.parseId(uri!!)
    }

    suspend fun deleteById(id: Long): Int {
        return AppDatabase.getInstance(context)?.userDao()?.deleteById(id)!!
    }
}