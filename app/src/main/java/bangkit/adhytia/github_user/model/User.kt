package bangkit.adhytia.github_user.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey
    @ColumnInfo(name = "username")
    @SerializedName("login")
    var username: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    var avatar: String,

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
    companion object {
        fun verifyUserData(user: User?): User? {
            user?.name = if (user?.name == null) "" else user.name
            user?.company = if (user?.company == null) "" else user.company
            user?.location = if (user?.location == null) "" else user.location
            return user
        }
    }
}