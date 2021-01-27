package com.powapp.powa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.powapp.powa.databinding.LandingFragmentBinding

class LandingFragment : Fragment(),
    LoginListAdapter.ListItemListener {

    private lateinit var viewModel: LandingViewModel
    private lateinit var binding: LandingFragmentBinding
    private lateinit var adapter: LoginListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Disables back navigation on main screen
        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //Inflates the fragment and returns reference to binding variable
        binding = LandingFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LandingViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.loginList.observe(viewLifecycleOwner, Observer {
            Log.i("dataLogging!", it.toString())
            adapter = LoginListAdapter(it, this@LandingFragment)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        })
        return binding.root
    }

    //LoginListAdapter interface implementation, handles clicks recycler view clicks
    override fun onItemClick(itemId: Int) {
        Log.i("itemClick", "for id $itemId")

        //Handling ID of item clicked, passing action to nav controller to navigate to the editor
        val navAction = LandingFragmentDirections.actionEditLogin(itemId)
        findNavController().navigate(navAction)
    }

}