package com.yofhi.storyapp.ui.maps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yofhi.storyapp.R
import com.yofhi.storyapp.databinding.FragmentMapOptionBinding
import com.yofhi.storyapp.ui.factory.FactoryUserViewModel
import com.yofhi.storyapp.ui.maps.MapStyle
import com.yofhi.storyapp.ui.maps.MapType
import com.yofhi.storyapp.ui.maps.MapViewStoryViewModel

class MapOptionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMapOptionBinding? = null
    private lateinit var factory: FactoryUserViewModel
    private val mapViewStoryViewModel: MapViewStoryViewModel by activityViewModels { factory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        onClickListener()
        initObserver()
        hideMapStyleGroup()
    }

    private fun initObserver() {
        mapViewStoryViewModel.getMapType().observe(viewLifecycleOwner) {
            when (it) {
                MapType.NORMAL -> {
                    highlightMapTypeSwitcher(MapType.NORMAL)
                    hideMapStyleGroup(false)
                }
                MapType.SATELLITE -> highlightMapTypeSwitcher(MapType.SATELLITE)
                MapType.TERRAIN -> highlightMapTypeSwitcher(MapType.TERRAIN)
                else -> highlightMapTypeSwitcher(MapType.NORMAL)
            }
        }
        mapViewStoryViewModel.getMapStyle().observe(viewLifecycleOwner) {
            when (it) {
                MapStyle.NORMAL -> highlightMapStyleSwitcher(MapStyle.NORMAL)
                MapStyle.NIGHT -> highlightMapStyleSwitcher(MapStyle.NIGHT)
                MapStyle.SILVER -> highlightMapStyleSwitcher(MapStyle.SILVER)
                else -> highlightMapStyleSwitcher(MapStyle.NORMAL)
            }
        }
    }

    private fun hideMapStyleGroup(isHide: Boolean = true) {
        if (isHide) {
            binding.mapStyleGroup.visibility = View.GONE
        } else {
            binding.mapStyleGroup.visibility = View.VISIBLE
        }
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Map Type
        binding.cvMapDefault.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.NORMAL)
            Toast.makeText(context, getString(R.string.map_type_normal), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapSatellite.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.SATELLITE)
            Toast.makeText(context, getString(R.string.map_type_satellite), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapTerrain.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.TERRAIN)
            Toast.makeText(context, getString(R.string.map_type_terrain), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        // Map Style
        binding.cvMapStyleDefault.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.NORMAL)
            Toast.makeText(context, getString(R.string.map_style_normal), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapStyleNight.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.NIGHT)
            Toast.makeText(context, getString(R.string.map_style_night), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapStyleSilver.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.SILVER)
            Toast.makeText(context, getString(R.string.map_style_silver), Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun changeColor(name: String): Int {
        return when (name) {
            ACCENT_COLOR -> ContextCompat.getColor(requireContext(), R.color.colorAccent)
            else -> ContextCompat.getColor(requireContext(), R.color.colorTextSecondary)
        }
    }

    private fun highlightMapTypeSwitcher(type: MapType) {
        when (type) {
            MapType.NORMAL -> {
                // Normal
                binding.ivMapDefault.setPadding(1, 1, 1, 1)
                binding.tvMapDefault.setTextColor(changeColor(ACCENT_COLOR))

                // Satellite
                binding.ivMapSatellite.setPadding(0, 0, 0, 0)
                binding.tvMapSatellite.setTextColor(changeColor(SECONDARY_COLOR))

                // Terrain
                binding.ivMapTerrain.setPadding(0, 0, 0, 0)
                binding.tvMapTerrain.setTextColor(changeColor(SECONDARY_COLOR))
            }
            MapType.SATELLITE -> {
                // Normal
                binding.ivMapDefault.setPadding(0, 0, 0, 0)
                binding.tvMapDefault.setTextColor(changeColor(SECONDARY_COLOR))

                // Satellite
                binding.ivMapSatellite.setPadding(1, 1, 1, 1)
                binding.tvMapSatellite.setTextColor(changeColor(ACCENT_COLOR))

                // Terrain
                binding.ivMapTerrain.setPadding(0, 0, 0, 0)
                binding.tvMapTerrain.setTextColor(changeColor(SECONDARY_COLOR))
            }
            MapType.TERRAIN -> {
                // Normal
                binding.ivMapDefault.setPadding(0, 0, 0, 0)
                binding.tvMapDefault.setTextColor(changeColor(SECONDARY_COLOR))

                // Satellite
                binding.ivMapSatellite.setPadding(0, 0, 0, 0)
                binding.tvMapSatellite.setTextColor(changeColor(SECONDARY_COLOR))

                // Terrain
                binding.ivMapTerrain.setPadding(1, 1, 1, 1)
                binding.tvMapTerrain.setTextColor(changeColor(ACCENT_COLOR))
            }
        }
    }

    private fun highlightMapStyleSwitcher(style: MapStyle) {
        when (style) {
            MapStyle.NORMAL -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(1, 1, 1, 1)
                binding.tvMapStyleNormal.setTextColor(changeColor(ACCENT_COLOR))

                // Night
                binding.ivMapStyleNight.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNight.setTextColor(changeColor(SECONDARY_COLOR))

                // Silver
                binding.ivMapStyleSilver.setPadding(0, 0, 0, 0)
                binding.tvMapStyleSilver.setTextColor(changeColor(SECONDARY_COLOR))
            }
            MapStyle.NIGHT -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNormal.setTextColor(changeColor(SECONDARY_COLOR))

                // Night
                binding.ivMapStyleNight.setPadding(1, 1, 1, 1)
                binding.tvMapStyleNight.setTextColor(changeColor(ACCENT_COLOR))

                // Silver
                binding.ivMapStyleSilver.setPadding(0, 0, 0, 0)
                binding.tvMapStyleSilver.setTextColor(changeColor(SECONDARY_COLOR))
            }
            MapStyle.SILVER -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNormal.setTextColor(changeColor(SECONDARY_COLOR))

                // Night
                binding.ivMapStyleNight.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNight.setTextColor(changeColor(SECONDARY_COLOR))

                // Terrain
                binding.ivMapStyleSilver.setPadding(1, 1, 1, 1)
                binding.tvMapStyleSilver.setTextColor(changeColor(ACCENT_COLOR))
            }
        }
    }

    companion object {
        private const val ACCENT_COLOR = "ACCENT_COLOR"
        private const val SECONDARY_COLOR = "SECONDARY_COLOR"
    }

}