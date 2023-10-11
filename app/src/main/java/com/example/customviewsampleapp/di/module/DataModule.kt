package com.example.customviewsampleapp.di.module

import com.example.customviewsampleapp.data.api.graph.GraphApi
import com.example.customviewsampleapp.data.api.graph.GraphApiImpl
import com.example.customviewsampleapp.data.rds.graph.GraphRds
import com.example.customviewsampleapp.data.rds.graph.GraphRdsImpl
import com.example.customviewsampleapp.data.repository.graph.GraphRepositoryImpl
import com.example.customviewsampleapp.domain.repository.graph.GraphRepository

class DataModule : Module {
    fun provideGraphApi(): GraphApi {
        return GraphApiImpl()
    }

    fun provideGraphRds(graphApi: GraphApi): GraphRds {
        return GraphRdsImpl(graphApi)
    }

    fun provideGraphRepository(graphRds: GraphRds): GraphRepository {
        return GraphRepositoryImpl(graphRds)
    }
}