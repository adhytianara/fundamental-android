package bangkit.adhytia.github_user.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.adapter.ListFavoriteAdapter
import bangkit.adhytia.github_user.databinding.ActivityFavoriteBinding
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import bangkit.adhytia.github_user.viewmodel.FavoriteViewModel
import bangkit.adhytia.github_user.viewmodel.FavoriteViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listFavoriteAdapter: ListFavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.favorite_page_title)

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
        runBlocking {
            launch {
                viewModel.getAll()
            }
        }
    }

    private fun observeUserList(viewModel: FavoriteViewModel) {
        viewModel.userList.observe(this, { usersCursor ->
//            val userList = MappingHelper.mapCursorToArrayList(usersCursor)
//            if (userList.isNullOrEmpty()) {
//                showNoDataView(true)
//            } else {
//                listFavoriteAdapter.setFavoriteList(userList as ArrayList<User>)
//                showNoDataView(false)
//            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = listFavoriteAdapter

        listFavoriteAdapter.setOnItemClickCallback(object :
            ListFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                moveToDetailsPage(data)
            }
        })
    }

    private fun moveToDetailsPage(user: User) {
        val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
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