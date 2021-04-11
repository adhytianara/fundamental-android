package bangkit.adhytia.github_user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val repository: Repository) : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()

    fun findByUsername(username: String) {
        runBlocking {
            launch {
                user.value = repository.findByUsername(username)
            }
        }
    }

    fun insertAll(vararg users: User) {
        runBlocking {
            launch {
                repository.insertAll(*users)
            }
        }
    }

    fun delete(userToDelete: User) {
        runBlocking {
            launch {
                repository.delete(userToDelete)
            }
        }
    }
}