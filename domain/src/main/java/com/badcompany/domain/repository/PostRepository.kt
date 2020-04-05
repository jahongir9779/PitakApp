package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.PassengerPost

interface PostRepository {

    fun getPassengerPosts(from: String, to: String): ResultWrapper<Exception, List<PassengerPost>>
    fun getDriverPosts(from: String, to: String): ResultWrapper<Exception, List<PassengerPost>>






}