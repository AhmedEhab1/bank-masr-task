package com.stc.ahmedehabtask.ui

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.stc.ahmedehabtask.adapters.CurrencySpinnerAdapter
import com.stc.ahmedehabtask.databinding.ConvertCurrencyFragmentBinding
import com.stc.ahmedehabtask.viewModels.ConvertCurrencyViewModel
import com.stc.ahmedehabtask.utilities.Constants.accessKey
import com.stc.ahmedehabtask.utilities.Loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {
    private var selectedValueFrom: Double? = 1.0
    private var selectedValueTo: Double? = 1.0
    private var keyFrom : String = "EUR"
    private var keyTo : String = "EUR"
    private lateinit var currencyCodes: List<String>
    private lateinit var binding: ConvertCurrencyFragmentBinding
    private val viewModel: ConvertCurrencyViewModel by viewModels()
    private val loading = Loading()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConvertCurrencyFragmentBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        getPosts()
        apiErrorHandler()
        onConvertedValueChanged()
        onChangeConvertedValueClicked()
    }

    private fun getPosts() {
        loading.show(requireActivity().supportFragmentManager, "loading")
        viewModel.getLatest(accessKey)
        lifecycleScope.launch {
            viewModel.response.collect {
                if (it != null) {
                    loading.dismiss()
                    initSpinner(binding.spinnerFrom, it.rates, "EUR")
                    initSpinner(binding.spinnerTo, it.rates, "EUR")
                }
            }
        }
    }


    private fun apiErrorHandler() {
        lifecycleScope.launch {
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                loading.dismiss()
                Toast.makeText(
                    requireActivity(),
                    "error please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initSpinner(
        spinner: Spinner,
        map: Map<String, Double>,
        defaultCurrency: String
    ) {
        currencyCodes = map.keys.toList()
        val adapter =
            CurrencySpinnerAdapter(requireActivity(), R.layout.simple_spinner_item, currencyCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(currencyCodes.indexOf(defaultCurrency))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = currencyCodes[position]
                val selectedValue = map[selectedCurrency]
                if (spinner == binding.spinnerFrom) {
                    selectedValueFrom = selectedValue
                    keyFrom = selectedCurrency
                } else if (spinner == binding.spinnerTo) {
                    selectedValueTo = selectedValue
                    keyTo = selectedCurrency
                }
                setConvertedValue()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireActivity(), "Nothing is selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onConvertedValueChanged() {
        binding.from.addTextChangedListener {
            setConvertedValue()
        }
    }

    private fun setConvertedValue() {
        val valueText = binding.from.text?.toString()
        binding.to.text = viewModel.getConvertedValue(valueText, selectedValueFrom, selectedValueTo)
    }

    private fun changeConvertedValue(){
        binding.spinnerFrom.setSelection(currencyCodes.indexOf(keyTo))
        binding.spinnerTo.setSelection(currencyCodes.indexOf(keyFrom))
    }

    private fun onChangeConvertedValueClicked(){
        binding.swapValues.setOnClickListener{
            changeConvertedValue()
        }
    }
}