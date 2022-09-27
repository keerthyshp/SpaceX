package com.keerthy.spacex.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.keerthy.spacex.R
import com.keerthy.spacex.databinding.FragmentItemListBinding
import com.keerthy.spacex.databinding.ItemListContentBinding
import com.keerthy.spacex.model.SpaceXDto
import com.keerthy.spacex.network.Resource
import com.keerthy.spacex.utils.DateUtils
import com.keerthy.spacex.viewmodel.OnItemCLickListener
import com.keerthy.spacex.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class ItemListFragment : Fragment(), OnItemCLickListener {

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding?.let { _binding!! }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var dataAdapter: SimpleItemRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding?.itemList!!

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        sharedViewModel.getDataList()
        setupRecyclerView(recyclerView, itemDetailFragmentContainer)

        lifecycleScope.launchWhenCreated {
            sharedViewModel.missionListRequest.collect { apiState ->
                when (apiState) {
                    is Resource.Success -> {
                        //success
                        binding?.progressCircular?.visibility = View.GONE
                        binding?.tvError?.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding?.progressCircular?.visibility = View.VISIBLE
                        binding?.tvError?.visibility = View.GONE
                    }
                    else -> {
                        binding?.progressCircular?.visibility = View.GONE
                        binding?.tvError?.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            sharedViewModel.missionList.collect {
                dataAdapter?.updateItems(it)
            }
        }
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView, itemDetailFragmentContainer: View?
    ) {
        dataAdapter = SimpleItemRecyclerViewAdapter(
            arrayListOf(), itemDetailFragmentContainer, this
        )
        recyclerView.adapter = dataAdapter
    }

    inner class SimpleItemRecyclerViewAdapter(
        private var values: List<SpaceXDto>,
        private val itemDetailFragmentContainer: View?,
        private val onItemClickListener: OnItemCLickListener
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.tvMission.text = item.missionName
            holder.tvRocket.text = item.rocket?.rocketName
            holder.tvLaunchSite.text = item.launchSite?.siteName
            holder.tvLaunchDate.text = item.launchDateUtc?.let { DateUtils.formatDate(it) }
            Picasso.get().load(item.links?.missionPatchSmall).placeholder(R.drawable.placeholder)
                .into(holder.ivPatch)
            with(holder.itemView) {
                tag = item
                setOnClickListener { itemView ->
                    val itemClicked = itemView.tag as SpaceXDto

                    onItemClickListener.onItemClick(itemClicked)
                    notifyDataSetChanged()

                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_item_detail)
                    } else {
                        itemView.findNavController().navigate(R.id.show_item_detail)
                    }
                }

            }
            if (values[position] == sharedViewModel.selectedMission.value) holder.itemView.setBackgroundColor(
                Color.parseColor("#FFBB86FC")
            )
            else holder.itemView.setBackgroundColor(Color.WHITE)
        }

        fun updateItems(items: List<SpaceXDto>?) {
            values = items ?: emptyList()
            notifyDataSetChanged()
        }


        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val tvMission: TextView = binding.tvMission
            val tvRocket: TextView = binding.tvRocket
            val tvLaunchSite: TextView = binding.tvLaunchSite
            val tvLaunchDate: TextView = binding.tvLaunchDate
            val ivPatch: ImageView = binding.ivPatch
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClick(selected: SpaceXDto) {
        sharedViewModel.setSelectedMission(selected)
    }

}