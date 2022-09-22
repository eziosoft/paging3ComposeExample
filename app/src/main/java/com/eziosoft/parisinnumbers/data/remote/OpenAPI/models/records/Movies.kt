package com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records

data class Movies(
    val links: List<com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.Link>,
    val records: List<com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.Record>,
    val total_count: Int
)
