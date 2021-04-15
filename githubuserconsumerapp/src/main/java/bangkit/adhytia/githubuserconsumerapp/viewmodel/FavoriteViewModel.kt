package bangkit.adhytia.githubuserconsumerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.githubuserconsumerapp.repository.Repository
import bangkit.adhytia.githubuserconsumerapp.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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