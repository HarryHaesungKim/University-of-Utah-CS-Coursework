package com.cs4530project.lifestyleapp

import org.json.JSONException
import org.json.JSONObject

object JSONMapUtils {
    fun getMapsData(data: String?): ArrayList<MapsData> {

        // list to hold the hikes
        var hikes = ArrayList<MapsData>()

        try {
            // parse string as JSON
            val arr = JSONObject(data!!).getJSONArray("results")

            // iterate through all items in JSON array
            for (i in 0 until arr.length()) {

                val item = arr.getJSONObject(i)
                val loc = item.getJSONObject("geometry").getJSONObject("location")

                // create a MapsData object
                val mapsData = MapsData()
                mapsData.name = item.getString("name")
                mapsData.placeId = item.getString("place_id")
                mapsData.lat = loc.get("lat") as Double?
                mapsData.long = loc.get("lng") as Double?

                hikes.add(mapsData)
            }
        } catch (e: JSONException) {}

        return hikes
    }
}
