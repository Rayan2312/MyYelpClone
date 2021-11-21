package edu.stanford.ralbraid.myyelpclone

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_restaurant.view.*


class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurant>):
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurant){
            itemView.tvName.text = restaurant.name

            itemView.tvAddress.text = restaurant.location.address
            itemView.tvDistance.text = restaurant.displayDistance()
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.tvNumReviews.text = restaurant.numReviews.toString()
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvPrice.text = restaurant.price
            Glide.with(context).load(restaurant.ImageUrl).apply(RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(20)
            )).into(itemView.imageView3)


        }
    }


}
