package com.powapp.powa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.powapp.powa.data.DataEntity
import com.powapp.powa.databinding.ListItemBinding

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

        //Initialize and return an instance of the viewholder class
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val login = loginList[position]
        with (holder.binding) {
            loginText.text = login.title
        }
    }

    override fun getItemCount() = loginList.size
}