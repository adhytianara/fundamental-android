package bangkit.adhytia.github_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.github_user.databinding.ItemRowFollowBinding
import bangkit.adhytia.github_user.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListFollowAdapter :
    RecyclerView.Adapter<ListFollowAdapter.ListViewHolder>() {

    var listFollow: List<User> = emptyList()

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(private val binding: ItemRowFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(50, 50))
                    .into(imgAvatar)
                tvUsername.text = user.username
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowFollowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFollow[holder.adapterPosition]) }
        holder.bind(listFollow[position])
    }

    override fun getItemCount(): Int {
        return listFollow.size
    }

    fun setFollowList(listUser: ArrayList<User>) {
        this.listFollow = listUser
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}