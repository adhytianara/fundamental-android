package bangkit.adhytia.githubuserconsumerapp.model

data class User(
    var username: String,
    var name: String,
    var avatar: String,
    var company: String,
    var location: String,
    var repository: Int,
    var followers: Int,
    var following: Int

)