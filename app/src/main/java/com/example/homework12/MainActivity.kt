package com.example.homework12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.homework12.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setListeners(viewModel = viewModel, binding = binding)
        observe(viewModel = viewModel, binding = binding)
    }


    private fun setListeners(binding: ActivityMainBinding, viewModel: MainActivityViewModel) {
        binding.buttonSearch.setOnClickListener {
            viewModel.loading(binding.inputEditText.text.toString())
        }
        binding.inputEditText.doAfterTextChanged {
            viewModel.setNumberOfLettersInRequest(binding.inputEditText.length())
        }
    }


    private fun observe(binding: ActivityMainBinding, viewModel: MainActivityViewModel) {
        viewModel.state.observe(this) {


            when (it) {
                is Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.buttonSearch.isEnabled = false
                    binding.inputEditText.isEnabled = false
                    binding.results.text = it.LOADING
                }

                is Waiting -> {
                    binding.buttonSearch.isEnabled = false
                    it.lastResult.let { lastResult -> binding.results.text = lastResult }
                }

                is Ready -> {
                    binding.buttonSearch.isEnabled = true
                    it.lastResult.let { lastResult -> binding.results.text = lastResult }
                }

                is Result -> {
                    binding.results.text = it.value
                    binding.progressBar.visibility = View.GONE
                    binding.inputEditText.isEnabled = true
                    binding.buttonSearch.isEnabled = true
                }
            }
        }
    }
}