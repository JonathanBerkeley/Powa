package com.powapp.powa

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.powapp.powa.databinding.ViewLoginFragmentBinding

class ViewLoginFragment : Fragment() {
    private lateinit var viewModel: ViewLoginViewModel
    //Lazy evaluation of args passed in from LandingFragment
    private val args: ViewLoginFragmentArgs by navArgs()
    private lateinit var binding: ViewLoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //For navigating backwards up the navigation chain - adds back button
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        //Gives the icon an options menu
        setHasOptionsMenu(true)

        //Inflates the fragment with the data passed in from the LandingFragment
        binding = ViewLoginFragmentBinding.inflate(inflater, container, false)
        binding.testText.text = "${args.loginId}"

        //Uses the same backward navigation for the back button or hand gestures, so that navigation back with
        //the SystemUI is handled the same way
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndNavigateBack()
                }
            }
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewLoginViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndNavigateBack()
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun saveAndNavigateBack(): Boolean {
        findNavController().navigateUp()
        return true
    }

}