package com.novatec.epitak.viewobjects

import android.os.Parcelable
import com.novatec.core.EPostStatus
import com.novatec.core.EPostType
import com.novatec.domain.domainmodel.PassengerPost
import com.novatec.remote.model.PassengerPostModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengerPostViewObj(val id: Long,
                                val from: PlaceViewObj,
                                val to: PlaceViewObj,
                                var price: Int,
                                val departureDate: String,
                                val createdDate: String,
                                val updatedDate: String,
                                val finishedDate: String? = null,
                                val timeFirstPart: Boolean,
                                val timeSecondPart: Boolean,
                                val timeThirdPart: Boolean,
                                val timeFourthPart: Boolean,
                                val airConditioner: Boolean,
                                val profileViewObj: ProfileViewObj,
                                val remark: String? = null,
                                val postStatus: EPostStatus,
                                val seat: Int,
                                val myLastOffer: MyOfferViewObj? = null,
                                val offer: AgreedOfferViewObj? = null,
                                val imageList: List<ImageViewObj> = listOf(),
                                val postType: EPostType) : Parcelable {


    companion object {
        fun mapFromPassengerPostModel(model: PassengerPost): PassengerPostViewObj {

            val profileImage = model.profile.image?.let {
                ImageViewObj(it.id, it.link)
            }

            val profileVObj = ProfileViewObj(model.profile.phoneNum,
                                             model.profile.name,
                                             model.profile.surname,
                                             model.profile.id,
                                             profileImage)


            val myLastOffer =
                if (model.myLastOffer != null) MyOfferViewObj(model.myLastOffer!!.id,
                                                              model.myLastOffer!!.id,
                                                              model.myLastOffer!!.repliedPostId,
                                                              model.myLastOffer!!.status,
                                                              model.myLastOffer!!.message,
                                                              model.myLastOffer!!.submitDate,
                                                              model.myLastOffer!!.priceInt,
                                                              model.myLastOffer!!.seat) else null
            var agreedOffer: AgreedOfferViewObj? = null
            model.agreedOffer?.let {
                agreedOffer = AgreedOfferViewObj(it.message, it.priceInt, it.seat)
            }

            val imageList = if (!model.imageList.isNullOrEmpty()) model.imageList.map {
                ImageViewObj(it.id, it.link)
            } else listOf()
            return PassengerPostViewObj(
                model.id,
                PlaceViewObj(model.from.districtId,
                             model.from.regionId,
                             model.from.lat,
                             model.from.lon,
                             model.from.regionName,
                             model.from.name),
                PlaceViewObj(model.to.districtId,
                             model.to.regionId,
                             model.to.lat,
                             model.to.lon,
                             model.to.regionName,
                             model.to.name),
                model.price,
                model.departureDate,
                model.createdDate,
                model.updatedDate,
                model.finishedDate,
                model.timeFirstPart,
                model.timeSecondPart,
                model.timeThirdPart,
                model.timeFourthPart,
                model.airConditioner,
                profileVObj,
                model.remark,
                model.postStatus,
                model.seat,
                myLastOffer,
                agreedOffer,
                imageList,
                model.postType,
            )
        }

        fun mapFromPassengerPostModel(model: PassengerPostModel): PassengerPostViewObj {

            val profileImage = model.profile.image?.let {
                ImageViewObj(it.id, it.link)
            }

            val profileModel = ProfileViewObj(model.profile.phoneNum,
                                              model.profile.name,
                                              model.profile.surname,
                                              model.profile.id,
                                              profileImage)


            val myLastOffer =
                if (model.myLastOffer != null) MyOfferViewObj(model.myLastOffer!!.id,
                                                              model.myLastOffer!!.id,
                                                              model.myLastOffer!!.repliedPostId,
                                                              model.myLastOffer!!.status,
                                                              model.myLastOffer!!.message,
                                                              model.myLastOffer!!.submitDate,
                                                              model.myLastOffer!!.priceInt,
                                                              model.myLastOffer!!.seat) else null
            var agreedOffer: AgreedOfferViewObj? = null
            model.agreedOffer?.let {
                agreedOffer = AgreedOfferViewObj(it.message, it.priceInt, it.seat)
            }

            val imageList = if (!model.imageList.isNullOrEmpty()) model.imageList.map {
                ImageViewObj(it.id, it.link)
            } else listOf()

            return PassengerPostViewObj(
                model.id,
                PlaceViewObj(model.from.districtId,
                             model.from.regionId,
                             model.from.lat,
                             model.from.lon,
                             model.from.regionName,
                             model.from.name),
                PlaceViewObj(model.to.districtId,
                             model.to.regionId,
                             model.to.lat,
                             model.to.lon,
                             model.to.regionName,
                             model.to.name),
                model.price,
                model.departureDate,
                model.createdDate,
                model.updatedDate,
                model.finishedDate,
                model.timeFirstPart,
                model.timeSecondPart,
                model.timeThirdPart,
                model.timeFourthPart,
                model.airConditioner,
                profileModel,
                model.remark,
                model.postStatus,
                model.seat,
                myLastOffer,
                agreedOffer,
                imageList,
                model.postType,
            )
        }
    }

}

@Parcelize
data class MyOfferViewObj(val id: Long,
                          val postId: Long,
                          val repliedPostId: Long,
                          val status: String,
                          val message: String,
                          val submitDate: String,
                          val priceInt: Int,
                          val seat: Int) : Parcelable

@Parcelize
data class AgreedOfferViewObj(val message: String,
                              val priceInt: Int,
                              val seat: Int) : Parcelable