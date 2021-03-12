package bangkit.adhytia.github_user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.github_user.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null)
            supportActionBar?.hide()

        binding.rvUsers.setHasFixedSize(true)

        val usersData = UsersData(this)
        list.addAll(usersData.listUsers)

        showRecyclerGrid()
    }


    private fun showRecyclerGrid() {
        binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        val gridUserAdapter = GridUserAdapter(list)
        binding.rvUsers.adapter = gridUserAdapter

        gridUserAdapter.setOnItemClickCallback(object : GridUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                moveToDetailsPage(data)
            }
        })
    }

    private fun moveToDetailsPage(user: User) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}