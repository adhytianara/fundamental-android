package bangkit.adhytia.github_user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val repository: Repository) : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()

    fun getByUsername(username: String) {
        runBlocking {
            launch {
                user.value = repository.getByUsername(username)
            }
        }
    }

    fun insert(user: User) {
        runBlocking {
            launch {
                repository.insert(user)
            }
        }
    }

    fun deleteByUsername(username: String) {
        runBlocking {
            launch {
                repository.deleteByUsername(username)
            }
        }
    }
}