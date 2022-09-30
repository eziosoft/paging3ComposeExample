package com.eziosoft.parisinnumbers.data.remote.openApi.models.records

data class MoviesPage(
    val links: List<Link>,
    val records: List<Record>,
    val total_count: Int
)
