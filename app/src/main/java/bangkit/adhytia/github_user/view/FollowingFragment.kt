package bangkit.adhytia.github_user.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)

        listFollowAdapter = ListFollowAdapter()

        val repository = Repository(requireContext())
        val followingViewModelFactory = FollowingViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, followingViewModelFactory).get(FollowingViewModel::class.java)

        observeFollowingList(viewModel)
        observeUser(viewModel)

        showLoading(true)
        username?.let { viewModel.getUserFollowing(it) }

        showRecyclerList()
    }

    private fun observeFollowingList(viewModel: FollowingViewModel) {
        viewModel.followingList.observe(viewLifecycleOwner, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                val userList = response.body() as ArrayList<User>
                listFollowAdapter.setFollowList(userList)
                if (userList.isEmpty()) {
                    showNoDataView(true)
                }
                Log.d("FOLLOWING", response.body().toString())
                Log.d("FOLLOWING", response.headers().toString())
            } else {
                Log.e("Error FOLLOWING", response.errorBody().toString())
            }
        })
    }

    private fun observeUser(viewModel: FollowingViewModel) {
        viewModel.user.observe(viewLifecycleOwner, { response ->
            showLoading(false)
            if (response.isSuccessful) {
                val user = User.verifyUserData(response.body())
                moveToDetailsPage(user!!)
                Log.d("FOLLOWING", response.body().toString())
            } else {
                Log.e("Error FOLLOWING", response.errorBody().toString())
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.adapter = listFollowAdapter

        listFollowAdapter.setOnItemClickCallback(object : ListFollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showLoading(true)
                viewModel.getUserDetailsByUsername(data.username)
            }
        })
    }

    private fun moveToDetailsPage(user: User) {
        val intent = Intent(activity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    private fun showNoDataView(state: Boolean) {
        if (state) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }
}