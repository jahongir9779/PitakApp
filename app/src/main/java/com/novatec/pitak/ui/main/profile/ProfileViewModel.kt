package com.novatec.pitak.ui.main.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.usecases.DeleteCar
import com.novatec.domain.usecases.GetCars
import com.novatec.domain.usecases.SetDefaultCar
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileViewModel @ViewModelInject constructor(val getCarList: GetCars,
                                                    val deleteCar: DeleteCar,
                                                    val setDefaultCar: SetDefaultCar) :
    BaseViewModel() {


    val carsResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()
    val deleteCarResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()
    val defaultCarResponse = SingleLiveEvent<ResultWrapper<Int>>()

    fun getCarList() {
        carsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = getCarList.execute()
            withContext(Main) {
                carsResponse.value = response
            }
        }
    }

    fun deleteCar(id: Long) {
        deleteCarResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = deleteCar.execute(id)
            withContext(Main) {
                deleteCarResponse.value = response
            }
        }
    }

    fun setDefault(id: Long, pos: Int) {
        defaultCarResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = setDefaultCar.execute(id)
            withContext(Main) {
                when (response) {
                    is ErrorWrapper.RespError -> defaultCarResponse.value = response
                    is ErrorWrapper.SystemError -> defaultCarResponse.value = response
                    is ResultWrapper.Success -> defaultCarResponse.value =
                        ResultWrapper.Success(pos)
                    ResultWrapper.InProgress -> defaultCarResponse.value = ResultWrapper.InProgress
                }.exhaustive


            }
        }
    }

}