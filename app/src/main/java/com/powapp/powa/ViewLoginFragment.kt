package com.powapp.powa

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        binding.editTitleText.setText("")

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

        //Gets the data from the database
        viewModel.injectLoginById(args.loginId)
        viewModel.getLastSavedSite(args.loginId)

        //Sets data to object data passed in from users click
        //and maintains application data through device updates such as rotation
        viewModel.currentLoginData.observe(viewLifecycleOwner, Observer {
            //Controls app screen heading
            requireActivity().title =
                if (it.title == "")
                    "New account"
                else
                    it.title

            val savedLoginData = savedInstanceState?.getString(EDIT_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0

            //Inserts all the data into the form for viewing / editing
            binding.run {
                editTitleText.setText(savedLoginData ?: it.title)
                editTargetText.setText(savedLoginData ?: it.target)
                editTargetNameText.setText(savedLoginData ?: it.target_name)
                editPasswordText.setText(savedLoginData ?: it.password)
                editEmailText.setText(savedLoginData ?: it.username)

                //Sets the cursor back to the position it was before view update
                editTitleText.setSelection(cursorPosition)
            }
        })

        //Passing in icon data through the fragments using the database in a background thread
        viewModel.savedSite.observe(viewLifecycleOwner, Observer {
            viewModel.savedSite.value?.toString()?.let { it1 -> Log.i("iconPass", it1) }

            //Load favicon for this site
            Glide.with(binding.loginFaviconView.context)
                .load("https://www.google.com/s2/favicons?sz=128&domain_url=" + viewModel.savedSite.value?.toString())
                //Sets the icon to loading animation while
                //waiting for the app to load the favicon or error out
                .thumbnail(Glide.with(binding.loginFaviconView.context).load(R.drawable.loading))
                .apply(
                    RequestOptions().override(150, 150)
                        .error(R.drawable.unfound)
                )
                .into(binding.loginFaviconView)
        })


        //Custom password visibility toggle - hides input on button click
        binding.toggleButton.setButtonDrawable(R.drawable.ic_not_visible)
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.i("passwordVisibilityClick", "enabled - ${binding.editPasswordText.inputType}")
                binding.toggleButton.setButtonDrawable(R.drawable.ic_visible)
                binding.editPasswordText.inputType = 524433
            } else {
                Log.i("passwordVisibilityClick", "disabled - ${binding.editPasswordText.inputType}")
                binding.toggleButton.setButtonDrawable(R.drawable.ic_not_visible)
                binding.editPasswordText.inputType = 524417
            }
        }

        //Submit button click listener
        binding.submitButton.setOnClickListener {
            saveAndNavigateBack()
        }


        return binding.root
    }

    //Handles the navigate back button on the view account screen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndNavigateBack()
            R.id.delete_account -> deleteAccount()
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Delete the account currently being viewed
    private fun deleteAccount(): Boolean {
        viewModel.deleteLoginData(args.loginId)
        discardAndNavigateBack()
        return true
    }

    //Saves what was changed and navigates backwards
    private fun saveAndNavigateBack(): Boolean {
        collapseKeyboard()

        //Updating the data
        viewModel.currentLoginData.value?.title = binding.editTitleText.text.toString()
        viewModel.currentLoginData.value?.run {
            binding.run {
                title = editTitleText.text.toString()
                target = editTargetText.text.toString()
                target_name = editTargetNameText.text.toString()
                if (password == "")
                    password = null

                if (username == "")
                    username = null

                password = editPasswordText.text.toString()
                username = editEmailText.text.toString()
            }
        }
        viewModel.updateLoginData()

        //Toast to let user know the data has saved
        Toast.makeText(context, "Saved account", Toast.LENGTH_SHORT).show()

        //Navigating backwards
        findNavController().navigateUp()
        return true
    }

    //For navigating backwards without saving data
    private fun discardAndNavigateBack(): Boolean {
        collapseKeyboard()
        findNavController().navigateUp()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.editTitleText) {
            outState.putString(EDIT_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun collapseKeyboard() {
        //For collapsing the keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}