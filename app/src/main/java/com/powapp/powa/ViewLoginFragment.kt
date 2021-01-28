package com.powapp.powa

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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



        //Creates ViewLoginViewModel file as a ViewModel that can be referenced further on
        viewModel = ViewModelProvider(this).get(ViewLoginViewModel::class.java)

        //Inflates the fragment with the data passed in from the LandingFragment
        binding = ViewLoginFragmentBinding.inflate(inflater, container, false)
        binding.testText.setText("")

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

        //Sets data to object data passed in from users click
        //and maintains application data through device updates such as rotation
        viewModel.currentLoginData.observe(viewLifecycleOwner, Observer {
            requireActivity().title = it.title

            val savedLoginData = savedInstanceState?.getString(EDIT_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            binding.testText.setText(savedLoginData ?: it.title)
            binding.testText.setSelection(cursorPosition)
        })
        viewModel.getLoginById(args.loginId)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndNavigateBack()
            else -> super.onOptionsItemSelected(item)
        }

    }

    //Saves what was changed and navigates backwards
    private fun saveAndNavigateBack(): Boolean {
        //For collapsing the keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        //Toast to let user know the data has saved
        Toast.makeText(context, "Saved account", Toast.LENGTH_SHORT).show()

        //Updating the data
        viewModel.currentLoginData.value?.title = binding.testText.text.toString()
        viewModel.updateLoginData()

        //Navigating backwards
        findNavController().navigateUp()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with (binding.testText) {
            outState.putString(EDIT_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}