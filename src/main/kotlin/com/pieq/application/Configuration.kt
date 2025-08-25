package com.pieq.application

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.core.Configuration
import io.dropwizard.db.DataSourceFactory

class Configuration : Configuration() {
    @JsonProperty("database")
    val database: DataSourceFactory = DataSourceFactory()
}
