package bangkit.adhytia.github_user

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class UsersData(private val mainActivity: MainActivity) {

    val listUsers: ArrayList<User>
        get() {
            val list = arrayListOf<User>()
            try {
                val obj = JSONObject(loadJSONFromAsset())
                val userArray = obj.getJSONArray("users")
                for (i in 0 until userArray.length()) {
                    val userDetail = userArray.getJSONObject(i)

                    val user = User(
                        userDetail.getString("username"),
                        userDetail.getString("name"),
                        userDetail.getString("avatar"),
                        userDetail.getString("company"),
                        userDetail.getString("location"),
                        userDetail.getInt("repository"),
                        userDetail.getInt("follower"),
                        userDetail.getInt("following")
                    )

                    list.add(user)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return list
        }

    private fun loadJSONFromAsset(): String {
        val json: String?

        try {
            val inputStream = mainActivity.assets.open("githubuser.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8

            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}




