package bangkit.adhytia.github_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val userList: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val user: MutableLiveData<Response<User>> = MutableLiveData()

    fun getUserList() {
        viewModelScope.launch {
            val response: Response<List<User>> = repository.getUserList()
            userList.value = response
        }
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            val response: Response<User> = repository.getUserByUsername(username)
            user.value = response
        }
    }
}