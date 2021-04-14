package bangkit.adhytia.github_user.viewmodel

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.github_user.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    val userList: MutableLiveData<Cursor> = MutableLiveData()

    fun getAll() {
        runBlocking {
            launch {
                userList.value = repository.getAll()
            }
        }
    }
}