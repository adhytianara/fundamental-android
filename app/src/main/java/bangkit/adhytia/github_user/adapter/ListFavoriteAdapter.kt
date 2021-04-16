package bangkit.adhytia.github_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.github_user.databinding.ItemRowFavoriteBinding
import bangkit.adhytia.github_user.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListFavoriteAdapter :
    RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {

    private var listFavorite: List<User> = emptyList()

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(private val binding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(80, 80))
                    .into(imgAvatar)
                tvUsernameFav.text = user.username
                if (user.name.isEmpty()) tvNameFav.visibility = View.GONE else tvNameFav.text =
                    user.name
                if (user.location.isEmpty()) tvLocationFav.visibility =
                    View.GONE else tvLocationFav.text = user.location
                if (user.company.isEmpty()) tvCompanyFav.visibility =
                    View.GONE else tvCompanyFav.text = user.company
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition]) }
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    fun setFavoriteList(listUser: ArrayList<User>) {
        this.listFavorite = listUser
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}