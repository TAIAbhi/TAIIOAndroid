package com.tag.tai.tag.Services.Responses.Categories

import com.tag.tai.tag.Services.Responses.SubCategories.SubCatData

data class CategoryResponse(
        var action: String,
        var message: String,
        var data: List<CategoryWithSubCategories>
)

data class CategoryWithSubCategories(
        var catId: Int,
        var name: String,
        var catCount: Int,
        var isMicroCategoryAvailable: Boolean,
        var subCategories: List<SubCatData>
)