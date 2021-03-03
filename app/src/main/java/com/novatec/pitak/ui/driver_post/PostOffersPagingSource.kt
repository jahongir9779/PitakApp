package com.novatec.pitak.ui.driver_post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novatec.core.EOfferStatus
import com.novatec.pitak.viewobjects.OfferViewObj
import com.novatec.remote.AuthorizedApiService
import com.novatec.remote.model.OfferDTO
import com.bumptech.glide.load.HttpException
import java.io.IOException

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class PostOffersPagingSource(
    private val authorizedApiService: AuthorizedApiService,
    private val id: Long
) : PagingSource<Int, OfferDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OfferDTO> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX

        return try {
            val response = authorizedApiService.getOffersForPost(id, position, params.loadSize)
            var offers = response.data
            if (!offers.isNullOrEmpty()) {
                offers = offers.filter {
                    it.status != EOfferStatus.REJECTED
                }.sortedBy {
                    it.status
                }
            }
            LoadResult.Page(
                data = offers!!,
                prevKey = if (position == POST_OFFER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (offers.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, OfferDTO>): Int? {
        return state.anchorPosition
    }
}