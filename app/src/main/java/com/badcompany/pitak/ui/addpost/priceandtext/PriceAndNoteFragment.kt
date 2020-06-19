package com.badcompany.pitak.ui.addpost.priceandtext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.domain.domainmodel.Place
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import kotlinx.android.synthetic.main.fragment_destinations.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
class PriceAndNoteFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_price_and_note) {


    private var placeFrom: Place? = null
    private var placeTo: Place? = null

    private val activityViewModel: AddPostViewModel by activityViewModels {
        viewModelFactory
    }

    //    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activityViewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()

        navController = findNavController()
//        confirm.isEnabled = true
//
//        code.setText(args.password)
//
//        confirm.setOnClickListener {
//            viewModel.confirm(args.phone, code.text.toString())
//        }


    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {
        next.setOnClickListener {
            navController.navigate(R.id.action_priceAndNoteFragment_to_carAndSeatFragment)
        }
        navBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }


    @ExperimentalSplittiesApi
    private fun setupObservers() {
//        viewModel.fromPlacesResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//
//                }
//                is ErrorWrapper.SystemError -> {
//
//                }
//                is ResultWrapper.Success -> {
//                    fromAutocompletePresenter.getAdr().clear()
//                    response.value.forEach { place ->
//                        fromAutocompletePresenter.getAdr().add(PlaceAutocompleteItemView(place,
//                                                                                        fromAutocompletePresenter))
//
//                    }
//                    fromAutocompletePresenter.getAdr().notifyDataSetChanged()
//                }
//                ResultWrapper.InProgress -> {
//                    if (fromAutocompletePresenter.getAdr().itemCount == 0 || fromAutocompletePresenter.getAdr().getItem(
//                            fromAutocompletePresenter.getAdr().itemCount - 1) !is LoadingItemSmall) {
//                        fromAutocompletePresenter.getAdr().add(LoadingItemSmall())
//                        fromAutocompletePresenter.getAdr().notifyDataSetChanged()
//                    } else {
//
//                    }
//                }
//            }.exhaustive
//
//        })

    }


    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
//        (activity as AddPostActivity).showActionBar()
    }


}




