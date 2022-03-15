package com.example.draganddrop.ui.selected

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.draganddrop.R
import com.example.draganddrop.data.model.City
import com.example.draganddrop.databinding.SelectedListFragmentBinding
import com.example.draganddrop.di.AppContainer
import com.example.draganddrop.ui.CityAdapter
import com.example.draganddrop.ui.cities.CityListViewModel
import com.example.draganddrop.ui.cities.CityListViewModelFactory
import com.example.draganddrop.ui.util.CityItemEventListener
import com.example.draganddrop.ui.util.DragAndSwipeCallback
import com.example.draganddrop.util.dataBindings

class SelectedListFragment : Fragment(R.layout.selected_list_fragment), CityItemEventListener {

    private val cityViewModel by activityViewModels<CityListViewModel>()
    private val viewModel by viewModels<SelectedListViewModel> {
        CityListViewModelFactory(AppContainer.provideCityRepository())
    }
    private val binding by dataBindings(SelectedListFragmentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityAdapter = CityAdapter(this)
        binding.cityList.apply {
            val itemTouchHelper = ItemTouchHelper(DragAndSwipeCallback())
            itemTouchHelper.attachToRecyclerView(this)
            adapter = cityAdapter
        }

        viewModel.selectedCities.observe(viewLifecycleOwner) { selectedList ->
            selectedList?.let { cityAdapter.submitList(it) }
        }

        cityViewModel.selectedIds.observe(viewLifecycleOwner) { ids ->
            ids?.let { viewModel.filterSelectedItems(it) }
        }
    }

    override fun onItemClicked(city: City) {
        // do nothing
    }

    override fun onItemLongClicked(city: City): Boolean {
        return false
    }

    override fun onItemRemoved(city: City) {
        cityViewModel.toggleCitySelection(city.id)
    }
}