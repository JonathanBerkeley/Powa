package com.powapp.powa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.powapp.powa.databinding.LandingFragmentBinding

class LandingFragment : Fragment() {

    private lateinit var viewModel: LandingViewModel
    private lateinit var binding: LandingFragmentBinding
    private lateinit var adapter: LoginListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            Log.i("dataLogging", it.toString())
            adapter = LoginListAdapter(it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        })
        return binding.root
    }

}