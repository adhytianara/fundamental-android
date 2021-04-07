package bangkit.adhytia.github_user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

        if (supportActionBar != null)
            supportActionBar?.hide()

        binding.rvUsers.setHasFixedSize(true)
        gridUserAdapter = GridUserAdapter()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        showLoading(true)
        viewModel.getUserList()
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

        showRecyclerGrid()
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
                viewModel.getUserByUsername(data.username)
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
            binding.progressBar!!.visibility = View.VISIBLE
        } else {
            binding.progressBar!!.visibility = View.GONE
        }
    }
}