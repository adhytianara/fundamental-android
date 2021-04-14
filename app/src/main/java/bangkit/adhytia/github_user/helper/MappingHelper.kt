package bangkit.adhytia.github_user.helper

import android.database.Cursor
import bangkit.adhytia.github_user.model.User

object MappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        usersCursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("_id"))
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val avatar_url = getString(getColumnIndexOrThrow("avatar_url"))
                val company = getString(getColumnIndexOrThrow("company"))
                val location = getString(getColumnIndexOrThrow("location"))
                val repository = getInt(getColumnIndexOrThrow("repository"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val following = getInt(getColumnIndexOrThrow("following"))
                usersList.add(
                    User(
                        id,
                        username,
                        name,
                        avatar_url,
                        company,
                        location,
                        repository,
                        followers,
                        following
                    )
                )
            }
        }
        return usersList
    }

    fun mapCursorToObject(usersCursor: Cursor?): User? {
        var user: User? = null
        usersCursor?.apply {
            moveToFirst()
            val id = getLong(getColumnIndexOrThrow("_id"))
            val username = getString(getColumnIndexOrThrow("username"))
            val name = getString(getColumnIndexOrThrow("name"))
            val avatar_url = getString(getColumnIndexOrThrow("avatar_url"))
            val company = getString(getColumnIndexOrThrow("company"))
            val location = getString(getColumnIndexOrThrow("location"))
            val repository = getInt(getColumnIndexOrThrow("repository"))
            val followers = getInt(getColumnIndexOrThrow("followers"))
            val following = getInt(getColumnIndexOrThrow("following"))
            user = User(
                id,
                username,
                name,
                avatar_url,
                company,
                location,
                repository,
                followers,
                following
            )
        }
        return user
    }
}