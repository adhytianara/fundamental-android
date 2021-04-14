package bangkit.adhytia.github_user.helper

import android.database.Cursor
import bangkit.adhytia.github_user.database.DatabaseContract
import bangkit.adhytia.github_user.model.User

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()
        userCursor?.apply {
            while (moveToNext()) {
                userList.add(
                    User(
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPOSITORY)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                    )
                )
            }
        }
        return userList
    }

    fun mapCursorToObject(usersCursor: Cursor?): User? {
        var user: User? = null
        usersCursor?.apply {
            if (count > 0) {
                moveToFirst()
                user = User(
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR)),
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY)),
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPOSITORY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                )
            }
        }
        return user
    }
}