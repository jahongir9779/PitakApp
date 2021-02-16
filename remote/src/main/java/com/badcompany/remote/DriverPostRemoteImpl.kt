package com.badcompany.remote

import com.badcompany.core.*
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.remote.ResponseFormatter.getFormattedResponse
import com.badcompany.remote.ResponseFormatter.getFormattedResponseNullable
import com.badcompany.remote.mapper.DriverPostMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(private val authorizedApiService: AuthorizedApiService,
                                               private val postMapper: DriverPostMapper) :
    DriverPostRemote {

    override suspend fun createDriverPost(post: DriverPostEntity): ResultWrapper<DriverPostEntity> {
        val response =
            getFormattedResponse { authorizedApiService.createPost(postMapper.mapFromEntity(post)) }

        (return when (response) {
            is ResponseError -> {
                ErrorWrapper.RespError(message = response.message, code = response.code)
            }
            is ResponseSuccess -> {
                ResultWrapper.Success(postMapper.mapToEntity(response.value))
            }
        }).exhaustive
    }


    override suspend fun deleteDriverPost(
        identifier: String): ResultWrapper<String> {
        return try {
            val response = authorizedApiService.deletePost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishDriverPost(
        identifier: String): ResultWrapper<String> {
        return try {
            val response = authorizedApiService.finishPost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getActiveDriverPosts(
    ): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = authorizedApiService.getActivePosts()
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = authorizedApiService.getHistoryPosts(page)
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPostEntity> {
        val response = getFormattedResponse { authorizedApiService.getDriverPostById(id) }
        return if (response is ResponseSuccess)
            ResponseSuccess(postMapper.mapToEntity(response.value))
        else response as ResponseError
    }

    override suspend fun startTrip(id: Long) =
        getFormattedResponseNullable { authorizedApiService.startTrip(id) }

    override suspend fun acceptOffer(id: Long) =
        getFormattedResponse { authorizedApiService.acceptOffer(id) }

    override suspend fun rejectOffer(id: Long) =
        getFormattedResponse { authorizedApiService.rejectOffer(id) }

    override suspend fun cancelMyOffer(id: Long) =
        getFormattedResponse { authorizedApiService.cancelMyOffer(id) }


}