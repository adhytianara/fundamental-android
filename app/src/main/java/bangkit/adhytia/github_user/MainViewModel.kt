package bangkit.adhytia.github_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.model.UserSearchResult
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val userList: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val user: MutableLiveData<Response<User>> = MutableLiveData()
    val userSearchResult: MutableLiveData<Response<UserSearchResult>> = MutableLiveData()

    fun getUserList() {
        viewModelScope.launch {
            val response: Response<List<User>> = repository.getUserList()
            userList.value = response
        }
    }

    fun getUserDetailsByUsername(username: String) {
        viewModelScope.launch {
            val response: Response<User> = repository.getUserDetailsByUsername(username)
            user.value = response
        }
    }

    fun searchUserByUsername(username: String) {
        viewModelScope.launch {
            val response: Response<UserSearchResult> = repository.searchUserByUsername(username)
            userSearchResult.value = response
        }
    }
}