package com.badcompany.data.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverOfferEntity
import com.badcompany.data.model.FilterEntity
import com.badcompany.data.model.PassengerPostEntity
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.PassengerPost

interface PassengerPostRemote {

    suspend fun filterPassengerPost(filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>>
    suspend fun offerARide(myOffer: DriverOfferEntity): ResponseWrapper<Any>
//    suspend fun getPassengerPostById(id:Long): ResponseWrapper<PassengerPostEntity>

}