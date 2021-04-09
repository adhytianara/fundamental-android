package bangkit.adhytia.github_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.github_user.databinding.ItemRowBinding
import bangkit.adhytia.github_user.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GridUserAdapter() :
    RecyclerView.Adapter<GridUserAdapter.GridViewHolder>() {

    private var listUser: List<User> = emptyList()

    private lateinit var onItemClickCallback: OnItemClickCallback

    class GridViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(80, 80))
                    .into(imgPhoto)
                tvUsername.text = user.username
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): GridViewHolder {
        val binding =
            ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun setUserList(listUser: ArrayList<User>) {
        this.listUser = listUser
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}