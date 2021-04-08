package bangkit.adhytia.github_user.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.adapter.ListFollowAdapter
import bangkit.adhytia.github_user.databinding.FragmentFollowingBinding
import bangkit.adhytia.github_user.model.User
import bangkit.adhytia.github_user.repository.Repository
import bangkit.adhytia.github_user.viewmodel.FollowingViewModel
import bangkit.adhytia.github_user.viewmodel.FollowingViewModelFactory

class FollowingFragment : Fragment() {

    private lateinit var viewModel: FollowingViewModel
    private lateinit var listFollowAdapter: ListFollowAdapter
    private lateinit var binding: FragmentFollowingBinding


    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)

        listFollowAdapter = ListFollowAdapter()

        val repository = Repository()
        val followingViewModelFactory = FollowingViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, followingViewModelFactory).get(FollowingViewModel::class.java)

        observeFollowingList(viewModel)
        username?.let { viewModel.getUserFollowing(it) }

        showRecyclerList()
    }

    private fun observeFollowingList(viewModel: FollowingViewModel) {
        viewModel.followingList.observe(this, { response ->
//            showLoading(false)
            if (response.isSuccessful) {
                listFollowAdapter.setFollowList(response.body() as ArrayList<User>)
                Log.d("FOLLOWER", response.body().toString())
                Log.d("FOLLOWER", response.headers().toString())
            } else {
                Log.e("Error FOLLOWER", response.errorBody().toString())
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.adapter = listFollowAdapter

        listFollowAdapter.setOnItemClickCallback(object : ListFollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
//                showLoading(true)
//                viewModel.getUserDetailsByUsername(data.username)
                Toast.makeText(activity, "clicked", Toast.LENGTH_LONG).show()
            }
        })
    }
}