package fr.flyingsquirrels.starring.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TVShow(
        @SerializedName("original_language")
        var originalLanguage: String? = null,

        @SerializedName("number_of_episodes")
        var numberOfEpisodes: Int? = null,

        @SerializedName("type")
        var type: String? = null,

        @SerializedName("backdrop_path")
        var backdropPath: String? = null,

        @SerializedName("credits")
        var credits: PeopleCredits? = null,

        @SerializedName("popularity")
        var popularity: Double? = null,

        @PrimaryKey
        @SerializedName("id")
        var id: Int? = null,

        @SerializedName("number_of_seasons")
        var numberOfSeasons: Int? = null,

        @SerializedName("vote_count")
        var voteCount: Int? = null,

        @SerializedName("first_air_date")
        var firstAirDate: String? = null,

        @SerializedName("overview")
        var overview: String? = null,

        @SerializedName("seasons")
        var seasons: List<Season?>? = null,

        @SerializedName("images")
        var images: Images? = null,

        @SerializedName("created_by")
        var createdBy: List<CreatedByItem?>? = null,

        @SerializedName("networks")
        var networks: List<TVNetwork?>? = null,

        @SerializedName("poster_path")
        var posterPath: String? = null,

        @SerializedName("origin_country")
        var originCountry: List<String?>? = null,

        @SerializedName("original_name")
        var originalName: String? = null,

        @SerializedName("vote_average")
        var voteAverage: Double? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("episode_run_time")
        var episodeRunTime: List<Int?>? = null,

        @SerializedName("in_production")
        var inProduction: Boolean? = null,

        @SerializedName("last_air_date")
        var lastAirDate: String? = null,

        @SerializedName("homepage")
        var homepage: String? = null,

        @SerializedName("status")
        var status: String? = null

) : Parcelable, Searchable(TV){
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            null,//parcel.readParcelable(PeopleCredits::class.java.classLoader),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            null,//parcel.createTypedArrayList(Season),
            null,//parcel.readParcelable(Images::class.java.classLoader),
            parcel.createTypedArrayList(CreatedByItem),
            parcel.createTypedArrayList(TVNetwork),
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.createIntArray()?.asList(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.run {
            writeString(originalLanguage)
            writeValue(numberOfEpisodes)
            writeString(type)
            writeString(backdropPath)
            //writeParcelable(credits, flags)
            writeValue(popularity)
            writeValue(id)
            writeValue(numberOfSeasons)
            writeValue(voteCount)
            writeString(firstAirDate)
            writeString(overview)
            //writeTypedList(seasons)
            //writeParcelable(images, flags)
            writeTypedList(createdBy)
            writeTypedList(networks)
            writeString(posterPath)
            writeStringList(originCountry)
            writeString(originalName)
            writeValue(voteAverage)
            writeString(name)
            writeIntArray(arrayOf(0).toIntArray())
            writeValue(inProduction)
            writeString(lastAirDate)
            writeString(homepage)
            writeString(status)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TVShow> {
        override fun createFromParcel(parcel: Parcel): TVShow {
            return TVShow(parcel)
        }

        override fun newArray(size: Int): Array<TVShow?> {
            return arrayOfNulls(size)
        }
    }

}