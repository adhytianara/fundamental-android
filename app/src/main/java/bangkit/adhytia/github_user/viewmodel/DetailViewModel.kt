package bangkit.adhytia.github_user.viewmodel

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val repository: Repository) : ViewModel() {

    val user: MutableLiveData<Cursor> = MutableLiveData()

    fun findById(id: Long) {
        runBlocking {
            launch {
                user.value = repository.findById(id)
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

    fun deleteById(userId: Long) {
        runBlocking {
            launch {
                repository.deleteById(userId)
            }
        }
    }
}