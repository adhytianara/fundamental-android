package bangkit.adhytia.github_user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.*

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    val userList: MutableLiveData<List<User>> = MutableLiveData()

    fun getAll() {
        runBlocking {
            launch {
                userList.value = repository.getAll()
            }
        }
    }
}