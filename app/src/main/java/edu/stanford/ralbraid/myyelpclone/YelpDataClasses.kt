package edu.stanford.ralbraid.myyelpclone

import com.google.gson.annotations.SerializedName


data class YelpSearchResult(
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
)

data class YelpRestaurant(
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceMeters: Double,
    @SerializedName("image_url") val ImageUrl: String,
    @SerializedName("categories") val categories: List<YelpCategory>,
    val location: YelpLocation

){
    fun displayDistance(): String{
        val milesPerMeter = 0.0062137
        val distanceInMiles = "%.2f".format(distanceMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}
data class YelpLocation(
    @SerializedName("address1") val address: String
)
data class YelpCategory(
    val title: String
)
