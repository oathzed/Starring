package fr.flyingsquirrels.starring.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieResponse(


        @SerializedName("page")
        val page: Int? = null,

        @SerializedName("results")
        val results: List<Movie>?

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(Movie)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(page)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TVShowResponse> {
        override fun createFromParcel(parcel: Parcel): TVShowResponse {
            return TVShowResponse(parcel)
        }

        override fun newArray(size: Int): Array<TVShowResponse?> {
            return arrayOfNulls(size)
        }

        const val TOP_RATED= "movie_top_rated"
        const val POPULAR= "movie_popular"
        const val NOW_PLAYING= "movie_now_playing"
        const val UPCOMING= "movie_upcoming"
    }
}