package com.lenatopoleva.dictionary.di

import com.lenatopoleva.dictionary.di.modules.*
import com.lenatopoleva.dictionary.di.modules.ViewModelModule
import com.lenatopoleva.dictionary.view.main.MainActivity
import com.lenatopoleva.dictionary.view.wordslist.WordsListFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        NavigationModule::class]
)
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(wordsListFragment: WordsListFragment)

}