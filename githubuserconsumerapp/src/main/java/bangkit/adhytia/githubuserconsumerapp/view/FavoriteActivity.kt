package bangkit.adhytia.githubuserconsumerapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bangkit.adhytia.githubuserconsumerapp.repository.Repository
import bangkit.adhytia.githubuserconsumerapp.R
import bangkit.adhytia.githubuserconsumerapp.adapter.ListFavoriteAdapter
import bangkit.adhytia.githubuserconsumerapp.databinding.ActivityFavoriteBinding
import bangkit.adhytia.githubuserconsumerapp.model.User
import bangkit.adhytia.githubuserconsumerapp.viewmodel.FavoriteViewModel
import bangkit.adhytia.githubuserconsumerapp.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listFavoriteAdapter: ListFavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.title)

        binding.rvUsers.setHasFixedSize(true)
        listFavoriteAdapter = ListFavoriteAdapter()

        val repository = Repository(this)
        val viewModelFactory = FavoriteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)

        observeUserList(viewModel)

        showRecyclerList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    private fun observeUserList(viewModel: FavoriteViewModel) {
        viewModel.userList.observe(this, { userList ->
            if (userList.isNullOrEmpty()) {
                showNoDataView(true)
            } else {
                listFavoriteAdapter.setFavoriteList(userList as ArrayList<User>)
                showNoDataView(false)
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = listFavoriteAdapter
    }

    private fun showNoDataView(state: Boolean) {
        if (state) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }
}