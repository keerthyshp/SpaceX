package com.keerthy.spacex.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.keerthy.spacex.databinding.FragmentItemDetailBinding
import com.keerthy.spacex.model.SpaceXDto
import com.keerthy.spacex.viewmodel.SharedViewModel


class ItemDetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: SpaceXDto? = null
    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val lp = TableRow.LayoutParams(
        TableRow.LayoutParams.MATCH_PARENT,
        TableRow.LayoutParams.WRAP_CONTENT
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            sharedViewModel.selectedMission.collect {
                item = it
                updateContent()
            }
        }
    }

    private fun updateContent() {
        item?.let {
            createHeader("Mission Details")
            createRow("Flight No", it.flightNumber?.toString())
            createRow("Mission", it.missionName)
            createRow("Launch Year", it.launchYear)
            createRow("Launch Site", it.launchSite?.siteName)
            it.launchSuccess?.let { launchSuccess ->
                if (!launchSuccess) {
                    createRow("Launch Status", "Failed")
                    createRow("Failure Details", it.launchFailureDetails?.reason)
                }
            }
            createRow("Details", it.details)

            createHeader("Rocket")
            createRow("Name", it.rocket?.rocketName)
            createRow("Type", it.rocket?.rocketType)

            createHeader("First Stage")
            it.rocket?.firstStage?.cores?.map { core ->
                createRow("Core Serial", core.coreSerial)
            }

            createHeader("Second Stage")
            it.rocket?.secondStage?.payloads?.map { payload ->
                createRow("Payload Id", payload.payloadId)
                createRow("Customers", payload.customers.joinToString())
                createRow("Nationality", payload.nationality)
                createRow("Type", payload.payloadType)
                createRow("Mass(Kg)", payload.payloadMassKg?.toString())
                createRow("Mass(lb)", payload.payloadMassLbs?.toString())
                createRow("Orbit", payload.orbit)
            }

            createHeader("Fairings")
            createRow("Reused", it.rocket?.fairings?.reused?.toString())
            createRow("Recovery Attempt", it.rocket?.fairings?.recoveryAttempt?.toString())
            createRow("Recovered", it.rocket?.fairings?.recovered?.toString())
        }
    }

    private fun createHeader(heading: String) {

        val tblRow = TableRow(requireContext())
        tblRow.layoutParams = lp
        val tvHeader = TextView(requireContext())
        val params: TableRow.LayoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10, 30, 10, 30)
        tvHeader.layoutParams = params
        tvHeader.textSize = 20.0f
        tvHeader.text = heading
        tvHeader.paintFlags = tvHeader.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        tblRow.addView(tvHeader)
        binding.tblParent.addView(tblRow)
    }

    private fun createRow(label: String, value: String?) {
        if (!value.isNullOrEmpty()) {
            val tblRow = TableRow(requireContext())
            tblRow.layoutParams = lp
            val params: TableRow.LayoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(6, 6, 6, 6)
            tblRow.weightSum = 10f
            val tvLabel = TextView(requireContext())

            val params1: TableRow.LayoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f)
            tvLabel.layoutParams = params1
            tvLabel.textSize = 16.0f
            tvLabel.text = label
            tvLabel.gravity = Gravity.END
            val tvSeparate = TextView(requireContext())

            val params2: TableRow.LayoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            tvSeparate.layoutParams = params2
            tvSeparate.textSize = 16.0f
            tvSeparate.text = ":"
            tvSeparate.gravity = Gravity.CENTER

            val tvValue = TextView(requireContext())
            val params3: TableRow.LayoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 6f)
            tvValue.layoutParams = params3
            tvValue.textSize = 16.0f
            tvValue.text = value
            tvValue.gravity = Gravity.START

            tblRow.addView(tvLabel)
            tblRow.addView(tvSeparate)
            tblRow.addView(tvValue)
            binding.tblParent.addView(tblRow)
        }
    }
}