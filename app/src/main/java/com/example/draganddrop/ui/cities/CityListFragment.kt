package com.example.draganddrop.ui.cities

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.draganddrop.R
import com.example.draganddrop.data.model.City
import com.example.draganddrop.databinding.CityListFragmentBinding
import com.example.draganddrop.di.AppContainer
import com.example.draganddrop.ui.CityAdapter
import com.example.draganddrop.ui.util.*
import com.example.draganddrop.util.dataBindings

class CityListFragment : Fragment(R.layout.city_list_fragment), CityItemEventListener {

    private val viewModel by activityViewModels<CityListViewModel>() {
        CityListViewModelFactory(AppContainer.provideCityRepository())
    }
    private val binding by dataBindings(CityListFragmentBinding::bind)
    private val swipeCallback = DragAndSwipeCallback(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        val cityAdapter = CityAdapter(this)
        binding.cityList.apply {

            val itemTouchHelper = ItemTouchHelper(swipeCallback)
            itemTouchHelper.attachToRecyclerView(this)
            adapter = cityAdapter
        }


        viewModel.cities.observe(viewLifecycleOwner) {
            it?.let { cities ->
                cityAdapter.submitList(cities)
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                if (viewModel.hasSelectedItem()) {
                    viewModel.clearSelection()
                }
            }
        }
        viewModel.selectedCount.observe(viewLifecycleOwner) {
            onBackPressedCallback.isEnabled = it > 0
            swipeCallback.isEnabled = it == 0
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)


        binding.bottomBar.setNavigationOnClickListener {
            viewModel.clearSelection()
        }

        binding.bottomBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_check_selection) {
                findNavController().navigate(R.id.action_cityListFragment_to_selectedListFragment)
                true
            } else false
        }

    }

    override fun onItemClicked(city: City) {
        viewModel.toggleCitySelection(city.id)

    }

    override fun onItemLongClicked(city: City): Boolean {
        return viewModel.onLongSelect(city)
    }

    override fun onItemRemoved(city: City) {
        viewModel.removeCity(city)
    }

}