package bangkit.adhytia.github_user.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.github_user.viewmodel.MainViewModel
import bangkit.adhytia.github_user.viewmodel.MainViewModelFactory
import bangkit.adhytia.github_user.adapter.GridUserAdapter
import bangkit.adhytia.github_user.databinding.ActivityMainBinding
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gridUserAdapter: GridUserAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.rvUsers.setHasFixedSize(true)
        gridUserAdapter = GridUserAdapter()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        observeUserList(viewModel)
        observeUser(viewModel)
        observeUserSearchResult(viewModel)

        showLoading(true)
        viewModel.getUserList()

        setupSearchView()

        showRecyclerGrid()
    }

    override fun onResume() {
        super.onResume()
        binding.searchView?.clearFocus()
    }

    private fun setupSearchView() {
        binding.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showLoading(true)
                showNoDataView(false)
                if (p0.isNullOrBlank()) {
                    viewModel.getUserList()
                } else {
                    viewModel.searchUserByUsername(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                showNoDataView(false)
                if (p0.isNullOrBlank()) {
                    showLoading(true)
                    viewModel.getUserList()
                }
                return false
            }
        })
    }

    private fun observeUserList(viewModel: MainViewModel) {
        viewModel.userList.observe(this, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                gridUserAdapter.setUserList(response.body() as ArrayList<User>)
                Log.d("Response", response.body().toString())
                Log.d("Response", response.headers().toString())
            } else {
                Log.e("Error", response.errorBody().toString())
            }
        })
    }

    private fun observeUser(viewModel: MainViewModel) {
        viewModel.user.observe(this, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                val user = verifyUserData(response.body())
                moveToDetailsPage(user!!)
                Log.d("Response", response.body().toString())
            } else {
                Log.e("Error", response.errorBody().toString())
            }
        })
    }

    private fun observeUserSearchResult(viewModel: MainViewModel) {
        viewModel.userSearchResult.observe(this, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                val userList = response.body()?.userList as ArrayList<User>
                gridUserAdapter.setUserList(userList)
                if (userList.isEmpty()) {
                    showNoDataView(true)
                }
                Log.d("Response", response.body().toString())
            } else {
                Log.e("Error", response.errorBody().toString())
            }
        })
    }

    private fun verifyUserData(user: User?): User? {
        user?.name = if (user?.name == null) "" else user.name
        user?.company = if (user?.company == null) "" else user.company
        user?.location = if (user?.location == null) "" else user.location
        return user
    }

    private fun showRecyclerGrid() {
        binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        binding.rvUsers.adapter = gridUserAdapter

        gridUserAdapter.setOnItemClickCallback(object : GridUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showLoading(true)
                viewModel.getUserDetailsByUsername(data.username)
            }
        })
    }

    private fun moveToDetailsPage(user: User) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar?.visibility = View.VISIBLE
        } else {
            binding.progressBar?.visibility = View.GONE
        }
    }

    private fun showNoDataView(state: Boolean) {
        if (state) {
            binding.tvNoData!!.visibility = View.VISIBLE
        } else {
            binding.tvNoData!!.visibility = View.GONE
        }
    }
}