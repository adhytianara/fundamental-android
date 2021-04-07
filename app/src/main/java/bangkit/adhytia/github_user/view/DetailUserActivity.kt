package bangkit.adhytia.github_user.view

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.adapter.SectionsPagerAdapter
import bangkit.adhytia.github_user.databinding.ActivityDetailUserBinding
import bangkit.adhytia.github_user.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

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

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        title = user.name

        setUserData(user)
        setupViewPager(user.username)
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

    private fun setUserData(user: User) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.username
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80))
            .into(binding.imgAvatar)
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvCompany.text = user.company
        "${user.repository} repository".also { binding.tvRepository.text = it }
        "${user.followers} follower".also { binding.tvFollower.text = it }
        "${user.following} following".also { binding.tvFollowing.text = it }
    }
}