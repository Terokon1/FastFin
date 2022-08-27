package com.chaev.fastfin.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaev.fastfin.R
import com.chaev.fastfin.databinding.FragmentCurrencyBinding
import com.chaev.fastfin.ui.adapter.DelegateAdapter
import com.chaev.fastfin.ui.adapter.FirstDelegates
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@RequiresApi(Build.VERSION_CODES.O)
class CurrencyFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyBinding
    private val currencyViewModel: CurrencyViewModel by viewModel()
    private val adapter = DelegateAdapter(
        FirstDelegates.headerDelegate(),
        FirstDelegates.itemDelegate()
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyViewModel.viewModelScope.launch {
            currencyViewModel.items.collect {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        }
        val rc = binding.currencyList
        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refresh -> {
                    currencyViewModel.viewModelScope.launch {
                        rc.smoothScrollToPosition(0)
                        adapter.items = listOf()
                        adapter.notifyDataSetChanged()
                        currencyViewModel.updateList()
                        currencyViewModel.items.collect {
                            adapter.items = it
                            adapter.notifyDataSetChanged()
                        }
                    }
                    true
                }
                else -> false
            }
        }

        rc.layoutManager = LinearLayoutManager(context)
        rc.adapter = adapter
        rc.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rc.canScrollVertically(1)) {
                    currencyViewModel.viewModelScope.launch {
                        currencyViewModel.loadNextPage()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}