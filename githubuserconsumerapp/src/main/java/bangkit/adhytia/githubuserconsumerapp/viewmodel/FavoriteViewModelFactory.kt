package bangkit.adhytia.githubuserconsumerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.githubuserconsumerapp.repository.Repository

class FavoriteViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}