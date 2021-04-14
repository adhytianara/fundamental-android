package bangkit.adhytia.github_user.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class GithubUserProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "bangkit.adhytia.github_user"
        val URI_CHEESE: Uri = Uri.parse("content://" + AUTHORITY + "/" + User.TABLE_NAME)

        private const val CODE_CHEESE_DIR = 1
        private const val CODE_CHEESE_ITEM = 2

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        MATCHER.addURI(AUTHORITY, User.TABLE_NAME, CODE_CHEESE_DIR);
        MATCHER.addURI(AUTHORITY, User.TABLE_NAME + "/*", CODE_CHEESE_ITEM);
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (MATCHER.match(uri)) {
            CODE_CHEESE_DIR -> throw java.lang.IllegalArgumentException(
                "Invalid URI, cannot update without ID$uri"
            )
            CODE_CHEESE_ITEM -> {
                val context = context ?: return 0

                val repository = Repository(context)

                var count: Int? = null

                runBlocking {
                    val response = async { repository.deleteById(ContentUris.parseId(uri)) }
                    count = response.await()
                }

                context.contentResolver.notifyChange(uri, null)
                count!!
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_CHEESE_DIR -> "vnd.android.cursor.dir/" + AUTHORITY + "." + User.TABLE_NAME
            CODE_CHEESE_ITEM -> "vnd.android.cursor.item/" + AUTHORITY + "." + User.TABLE_NAME
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (MATCHER.match(
            uri
        )) {
            CODE_CHEESE_DIR -> {
                val context = context ?: return null
                val repository = Repository(context)

                var id: Long? = null
                runBlocking {
                    val response = async { repository.insert(User.fromContentValues(values)!!) }
                    id = response.await()
                }
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id!!)
            }
            CODE_CHEESE_ITEM -> throw java.lang.IllegalArgumentException(
                "Invalid URI, cannot insert with ID: $uri"
            )
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String?>?, selection: String?,
        selectionArgs: Array<String?>?, sortOrder: String?
    ): Cursor? {
        val code: Int = MATCHER.match(uri)
        return if (code == CODE_CHEESE_DIR || code == CODE_CHEESE_ITEM) {
            val context = context ?: return null
            val repository = Repository(context)
            var cursor: Cursor? = null
            if (code == CODE_CHEESE_DIR) {
                runBlocking {
                    val response = async { repository.getAll() }
                    cursor = response.await()
                }
            } else {
                runBlocking {
                    val response = async { repository.findById(ContentUris.parseId(uri)) }
                    cursor = response.await()
                }
            }
            cursor!!.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
        return 0
    }
}