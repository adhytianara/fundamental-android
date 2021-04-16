package bangkit.adhytia.github_user.view

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.github_user.utils.Variables
import bangkit.adhytia.github_user.adapter.GridUserAdapter
import bangkit.adhytia.github_user.databinding.ActivityMainBinding
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import bangkit.adhytia.github_user.viewmodel.MainViewModel
import bangkit.adhytia.github_user.viewmodel.MainViewModelFactory

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

        val repository = Repository(this)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        observeUserList(viewModel)
        observeUser(viewModel)
        observeUserSearchResult(viewModel)

        setupSearchView()
        binding.btnConnectivity.setOnClickListener { showConnectivityView(Variables.isNetworkConnected) }

        showRecyclerGrid()

        showConnectivityView(Variables.isNetworkConnected)
        if (Variables.isNetworkConnected) {
            showLoading(true)
            viewModel.getUserList()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.clearFocus()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showNoDataView(false)
                showConnectivityView(Variables.isNetworkConnected)
                if (Variables.isNetworkConnected) {
                    showLoading(true)
                    if (p0.isNullOrBlank()) {
                        viewModel.getUserList()
                    } else {
                        viewModel.searchUserByUsername(p0)
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                showNoDataView(false)
                if (p0.isNullOrBlank()) {
                    showConnectivityView(Variables.isNetworkConnected)
                    if (Variables.isNetworkConnected) {
                        showLoading(true)
                        viewModel.getUserList()
                    }
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
            } else {
                Log.e("Error", response.errorBody().toString())
            }
        })
    }

    private fun observeUser(viewModel: MainViewModel) {
        viewModel.user.observe(this, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                val user = User.verifyUserData(response.body())
                moveToDetailsPage(user!!)
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
            } else {
                Log.e("Error", response.errorBody().toString())
            }
        })
    }

    private fun showRecyclerGrid() {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == ORIENTATION_PORTRAIT) 2 else 4
        binding.rvUsers.layoutManager = GridLayoutManager(this, spanCount)
        binding.rvUsers.adapter = gridUserAdapter

        gridUserAdapter.setOnItemClickCallback(object : GridUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showConnectivityView(Variables.isNetworkConnected)
                if (Variables.isNetworkConnected) {
                    showLoading(true)
                    viewModel.getUserDetailsByUsername(data.username)
                }
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
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoDataView(state: Boolean) {
        if (state) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }

    private fun showConnectivityView(state: Boolean) {
        if (state) {
            binding.groupNoInternet.visibility = View.GONE
            binding.groupSearch.visibility = View.VISIBLE
            if (gridUserAdapter.listUser.isEmpty()) {
                viewModel.getUserList()
            }
        } else {
            binding.groupNoInternet.visibility = View.VISIBLE
            binding.groupSearch.visibility = View.GONE
        }
    }

    fun moveToFavoritePage(view: View) {
        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
        startActivity(intent)
    }

    fun moveToSettingPage(view: View) {
        val intent = Intent(this@MainActivity, SettingActivity::class.java)
        startActivity(intent)
    }
}