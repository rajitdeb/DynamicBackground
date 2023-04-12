package com.rajit.dynamicbackground.ui.fragments.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.rajit.dynamicbackground.adapter.ViewPagerAdapter
import com.rajit.dynamicbackground.databinding.FragmentArtistBinding
import com.rajit.dynamicbackground.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArtistViewModel by viewModels()
    private val mAdapter by lazy { ViewPagerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistBinding.inflate(layoutInflater, container, false)

        binding.apply {

            // setting viewpager properties
            viewPager.clipToPadding = false
            viewPager.setPadding(0, 0, 0, 0)
            viewPager.adapter = mAdapter

            // observing list livedata
            viewModel.list.observe(viewLifecycleOwner) {
                when(it.status) {

                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        it.data.let {result ->
                            if(result != null) {
                                mAdapter.submitList(result)
                            } else {
                                view?.let { it1 ->
                                    Snackbar.make(it1, "Status = false", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        view?.let { it1 ->
                            Snackbar.make(it1, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    Status.LOADING -> progressBar.visibility = View.VISIBLE

                }
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}