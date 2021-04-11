package bangkit.adhytia.github_user.view

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.adapter.SectionsPagerAdapter
import bangkit.adhytia.github_user.databinding.ActivityDetailUserBinding
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import bangkit.adhytia.github_user.viewmodel.DetailViewModel
import bangkit.adhytia.github_user.viewmodel.DetailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var user: User
    private var isFavorite = false
    private var isInDatabase = false

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(EXTRA_USER)
        title = user.name

        val repository = Repository(this)
        val viewModelFactory = DetailViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        observeUser(viewModel)

        binding.imgFavorite.setOnClickListener(this)

        setUserData(user)
        setupViewPager(user.username)
    }


    override fun onStart() {
        super.onStart()
        viewModel.findByUsername(user.username)
    }

    private fun observeUser(viewModel: DetailViewModel) {
        viewModel.user.observe(this, { user ->
            isFavorite = user != null
            isInDatabase = user != null
            showButtonFavorite()
        })
    }

    private fun showButtonFavorite() {
        if (isFavorite) {
            binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite)
        } else {
            binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.imgFavorite.id -> {
                isFavorite = !isFavorite
                showButtonFavorite()
            }
        }
    }

    private fun setUserData(user: User) {
        if (user.name.isEmpty()) binding.tvName.visibility = View.GONE else binding.tvName.text =
            user.name
        if (user.username.isEmpty()) binding.tvUsername.visibility =
            View.GONE else binding.tvUsername.text =
            user.username
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80))
            .into(binding.imgAvatar)
        if (user.company.isEmpty()) binding.tvCompany.visibility =
            View.GONE else binding.tvCompany.text =
            user.company
        if (user.location.isEmpty()) binding.tvLocation.visibility =
            View.GONE else binding.tvLocation.text =
            user.location
        "${user.repository} repository".also { binding.tvRepository.text = it }
        "${user.followers} follower".also { binding.tvFollower.text = it }
        "${user.following} following".also { binding.tvFollowing.text = it }
    }

    private fun setupViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onPause() {
        super.onPause()
        if (isFavorite != (isInDatabase)) {
            if (isInDatabase) {
                viewModel.delete(user)
            } else {
                viewModel.insertAll(user)
            }
        }
    }
}