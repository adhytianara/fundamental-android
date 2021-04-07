package bangkit.adhytia.github_user.model

import com.google.gson.annotations.SerializedName

data class UserSearchResult(
    @SerializedName("items") var userList: List<User>,
    var name: String
)
