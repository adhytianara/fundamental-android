package bangkit.adhytia.github_user.model

import android.content.ContentValues
import android.os.Parcelable
import android.provider.BaseColumns
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = User.TABLE_NAME)
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    var id: Long,

    @ColumnInfo(name = "username")
    @SerializedName("login")
    var username: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String,

    @ColumnInfo(name = "company")
    var company: String,

    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "repository")
    @SerializedName("public_repos")
    var repository: Int,

    @ColumnInfo(name = "followers")
    var followers: Int,

    @ColumnInfo(name = "following")
    var following: Int

) : Parcelable {
    constructor() : this(0, "", "", "", "", "", 0, 0, 0)

    companion object {
        const val TABLE_NAME = "user"
        const val COLUMN_ID: String = BaseColumns._ID

        fun verifyUserData(user: User?): User? {
            user?.name = if (user?.name == null) "" else user.name
            user?.company = if (user?.company == null) "" else user.company
            user?.location = if (user?.location == null) "" else user.location
            return user
        }

        //        fun fromContentValues(values: ContentValues?): User {
//            val cheese: User = User()
//            if (values != null && values.containsKey(COLUMN_ID)) {
//                cheese.id = values.getAsLong(COLUMN_ID)
//            }
//            if (values != null && values.containsKey("username")) {
//                cheese.name = values.getAsString("username")
//            }
//            if (values != null && values.containsKey("name")) {
//                cheese.name = values.getAsString("name")
//            }
//            if (values != null && values.containsKey("avatar_url")) {
//                cheese.name = values.getAsString("avatar_url")
//            }
//            if (values != null && values.containsKey("company")) {
//                cheese.name = values.getAsString("company")
//            }
//            if (values != null && values.containsKey("location")) {
//                cheese.name = values.getAsString("location")
//            }
//            if (values != null && values.containsKey("repository")) {
//                cheese.name = values.getAsString("repository")
//            }
//            if (values != null && values.containsKey("followers")) {
//                cheese.name = values.getAsString("followers")
//            }
//            if (values != null && values.containsKey("following")) {
//                cheese.name = values.getAsString("following")
//            }
//            return cheese
//        }
        fun fromContentValues(values: ContentValues?): User? {
            if (values != null && values.containsKey(COLUMN_ID)) {
                if (values.containsKey("username")) {
                    if (values.containsKey("name")) {
                        if (values.containsKey("avatar_url")) {
                            if (values.containsKey("company")) {
                                if (values.containsKey("location")) {
                                    if (values.containsKey("repository")) {
                                        if (values.containsKey("followers")) {
                                            if (values.containsKey("following")) {
                                                return User(
                                                    values.getAsLong(COLUMN_ID),
                                                    values.getAsString("username"),
                                                    values.getAsString("name"),
                                                    values.getAsString("avatar_url"),
                                                    values.getAsString("company"),
                                                    values.getAsString("location"),
                                                    values.getAsInteger("repository"),
                                                    values.getAsInteger("followers"),
                                                    values.getAsInteger("following")
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Log.d("DEBUG", "fromContentValues failde")
            return null
        }
    }
}