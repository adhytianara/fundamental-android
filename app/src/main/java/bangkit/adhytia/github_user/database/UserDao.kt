package bangkit.adhytia.github_user.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import bangkit.adhytia.github_user.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun selectAll(): Cursor

    @Query("SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_ID + " = :id")
    fun selectById(id: Long): Cursor

    @Insert
    fun insert(user: User): Long

    @Delete
    fun deleteById(id: Long): Int
}