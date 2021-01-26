package com.powapp.powa

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.powapp.powa.data.DataEntity
import com.powapp.powa.databinding.ListItemBinding
import java.lang.Exception

class LoginListAdapter(private val loginList: List<DataEntity>):
    //Calls constructor for inner class
    RecyclerView.Adapter<LoginListAdapter.ViewHolder>() {

    //Gets a reference to the root view of the list_view.xml file
    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //Gets reference to the root of the layout file
        val view = inflater.inflate(R.layout.list_item, parent, false)

        //Initialize and return an instance of the ViewHolder class
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val login = loginList[position]
        val baseFaviconUrl: String = "https://www.google.com/s2/favicons?sz=128&domain_url="
        with (holder.binding) {
            //Set the data for the list_item layout
            loginTitle.text = login.title
            loginTarget.text = login.target_name
            try {
                Glide.with(loginFavicon.context)
                    .load(baseFaviconUrl + loginTarget.text)
                    .apply(
                        RequestOptions().override(150, 150)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                    )
                    .dontAnimate()
                    .into(loginFavicon)
            } catch (ex: Exception) {
                Log.e("Glide exception", "$ex")
            }

        }
    }

    override fun getItemCount() = loginList.size
}