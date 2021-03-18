package bangkit.adhytia.github_user

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.github_user.databinding.ItemRowBinding

class GridUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<GridUserAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class GridViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with (binding) {
                val resources: Resources = itemView.context.resources
                val resourceId: Int =
                    resources.getIdentifier(user.avatar, "drawable", itemView.context.packageName)
                imgPhoto.setImageResource(resourceId)
                tvName.text = user.name
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

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}