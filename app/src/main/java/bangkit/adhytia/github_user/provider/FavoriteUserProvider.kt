package bangkit.adhytia.github_user.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import bangkit.adhytia.github_user.database.DatabaseContract.AUTHORITY
import bangkit.adhytia.github_user.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import bangkit.adhytia.github_user.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import bangkit.adhytia.github_user.database.UserDatabaseHelper

class FavoriteUserProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_USERNAME = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userHelper: UserDatabaseHelper

        init {
            // content://bangkit.adhytia.github_user/user
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            // content://bangkit.adhytia.github_user/user/username
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/*", USER_USERNAME)
        }
    }

    override fun onCreate(): Boolean {
        userHelper = UserDatabaseHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {

            USER -> userHelper.queryAll()
            USER_USERNAME -> userHelper.getByUsername(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> userHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }


    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (USER_USERNAME) {
            sUriMatcher.match(uri) -> userHelper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}