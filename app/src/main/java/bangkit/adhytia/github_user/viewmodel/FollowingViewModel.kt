package bangkit.adhytia.github_user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class FollowingViewModel(private val repository: Repository) : ViewModel() {

    val followingList: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val user: MutableLiveData<Response<User>> = MutableLiveData()

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            val response: Response<List<User>> = repository.getUserFollowing(username)
            followingList.value = response
        }
    }

    fun getUserDetailsByUsername(username: String) {
        viewModelScope.launch {
            val response: Response<User> = repository.getUserDetailsByUsername(username)
            user.value = response
        }
    }
}