package com.lenatopoleva.dictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.lenatopoleva.dictionary.di.ViewModelFactory
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.datasource.network.RetrofitImpl
import com.lenatopoleva.dictionary.model.datasource.database.RoomDataBaseImpl
import com.lenatopoleva.dictionary.model.datasource.database.room.HistoryDao
import com.lenatopoleva.dictionary.model.datasource.database.room.HistoryDatabase
import com.lenatopoleva.dictionary.model.repository.Repository
import com.lenatopoleva.dictionary.model.repository.RepositoryImpl
import com.lenatopoleva.dictionary.model.repository.RepositoryLocal
import com.lenatopoleva.dictionary.model.repository.RepositoryLocalImpl
import com.lenatopoleva.dictionary.view.main.MainActivityViewModel
import com.lenatopoleva.dictionary.view.wordslist.WordsListInteractor
import com.lenatopoleva.dictionary.view.wordslist.WordsListViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Provider


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, viewModelModule, navigation, mainActivity, wordsListScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImpl(RetrofitImpl()) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryLocalImpl(RoomDataBaseImpl(get())) }
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(qualifier = named("appViewModelMap")) {
        var map =
            mutableMapOf(
                MainActivityViewModel::class.java to Provider<ViewModel>{MainActivityViewModel(get<Router>())},
                WordsListViewModel::class.java to Provider<ViewModel>{WordsListViewModel(get<WordsListInteractor>(), get<Router>()) })
        map
    }
    single<ViewModelProvider.Factory>(qualifier = named("appViewModelProvider")) {
        ViewModelFactory(get<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(
            qualifier = named("appViewModelMap")))}
}

val navigation = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    factory<NavigatorHolder> { cicerone.navigatorHolder }
    factory<Router> { cicerone.router }
}

val mainActivity = module {
    factory { MainActivityViewModel(get<Router>()) }
}

val wordsListScreen = module {
    factory { WordsListInteractor(get(), get()) }
    factory { WordsListViewModel(get<WordsListInteractor>(), get<Router>()) }
}


