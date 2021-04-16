package bangkit.adhytia.githubuserconsumerapp.repository

import android.content.Context
import bangkit.adhytia.githubuserconsumerapp.helper.MappingHelper
import bangkit.adhytia.githubuserconsumerapp.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import bangkit.adhytia.githubuserconsumerapp.model.User

class Repository(private val context: Context) {

    fun getAll(): List<User> {
        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        return MappingHelper.mapCursorToArrayList(cursor)
    }
}