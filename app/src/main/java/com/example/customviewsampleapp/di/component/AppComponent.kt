package com.example.customviewsampleapp.di.component

import com.example.customviewsampleapp.data.api.graph.GraphApi
import com.example.customviewsampleapp.data.rds.graph.GraphRds
import com.example.customviewsampleapp.di.module.DataModule
import com.example.customviewsampleapp.di.module.MapperModule
import com.example.customviewsampleapp.di.module.UseCaseModule
import com.example.customviewsampleapp.domain.graph.GetGraphUseCase
import com.example.customviewsampleapp.domain.repository.graph.GraphRepository
import com.example.customviewsampleapp.utils.mapper.graph.GraphDomainToUiMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDomainToUiMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDtoToDomainMapper
import com.example.customviewsampleapp.view.graph.GraphViewModel

class AppComponent : Component {
    //region Mapper Dependencies
    val graphPointDtoToDomainMapper: GraphPointDtoToDomainMapper
    val graphDtoToDomainMapper: GraphDtoToDomainMapper
    val graphPointDomainToUiMapper: GraphPointDomainToUiMapper
    val graphDomainToUiMapper: GraphDomainToUiMapper
    //endregion

    //region Data Dependencies
    val graphApi: GraphApi
    val graphRds: GraphRds
    val graphRepository: GraphRepository
    //endregion

    //region Domain Dependencies
    val getGraphUseCase: GetGraphUseCase
    //endregion

    init {
        //region Init Mapper Dependencies
        val mapperModule = MapperModule()
        graphPointDtoToDomainMapper = mapperModule.provideGraphPointDtoToDomainMapper()
        graphDtoToDomainMapper =
            mapperModule.provideGraphDtoToDomainMapper(graphPointDtoToDomainMapper)
        graphPointDomainToUiMapper = mapperModule.provideGraphPointDomainToUiMapper()
        graphDomainToUiMapper =
            mapperModule.provideGraphDomainToUiMapper(graphPointDomainToUiMapper)
        //endregion

        //region Init Data Dependencies
        val dataModule = DataModule()
        graphApi = dataModule.provideGraphApi()
        graphRds = dataModule.provideGraphRds(graphApi)
        graphRepository = dataModule.provideGraphRepository(graphRds)
        //endregion

        //region Domain Data Dependencies
        val useCaseModule = UseCaseModule()
        getGraphUseCase = useCaseModule.provideGetGraphUseCase(
            graphRepository = graphRepository,
            graphDtoToDomainMapper = graphDtoToDomainMapper,
            graphDomainToUiMapper = graphDomainToUiMapper
        )
        //endregion
    }

    fun injectGraphViewModel(): GraphViewModel {
        return GraphViewModel(
            getGraphUseCase = getGraphUseCase
        )
    }
}