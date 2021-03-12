package bangkit.adhytia.github_user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bangkit.adhytia.github_user.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        title = user.name

        binding.tvName.text = user.name
        binding.tvUsername.text = user.username
        val resourceId: Int =
            resources.getIdentifier(user.avatar, "drawable", packageName)
        binding.imgAvatar.setImageResource(resourceId)
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvCompany.text = user.company
        "${user.repository} repository".also { binding.tvRepository.text = it }
        "${user.follower} follower".also { binding.tvFollower.text = it }
        "${user.following} following".also { binding.tvFollowing.text = it }
    }
}