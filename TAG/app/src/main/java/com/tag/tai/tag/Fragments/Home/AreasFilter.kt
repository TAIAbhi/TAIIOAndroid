package com.tag.tai.tag.Fragments.Home

import android.widget.Filter
import com.tag.tai.tag.Services.Responses.GetAreas.AreaData

class AreasFilter: Filter() {
    var areasAdapter: AreaListAdapter? = null

    override fun performFiltering(cityId: CharSequence?): FilterResults {
        val areasCopy = areasAdapter?.areas?.filter {it.cityId in listOf(cityId, "all") }
        return FilterResults().apply {
            values = areasCopy
            count = areasCopy?.size?:0
        }
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        areasAdapter?.data = results?.values as List<AreaData>
        areasAdapter?.notifyDataSetChanged()
    }
}