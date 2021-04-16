package bangkit.adhytia.github_user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.github_user.repository.Repository

class FollowingViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowingViewModel(repository) as T
    }
}