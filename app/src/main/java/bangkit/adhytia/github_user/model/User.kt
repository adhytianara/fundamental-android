package bangkit.adhytia.github_user.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("login") var username: String,
    var name: String,
    @SerializedName("avatar_url") var avatar: String,
    var company: String,
    var location: String,
    @SerializedName("public_repos") var repository: Int,
    var followers: Int,
    var following: Int
) : Parcelable
