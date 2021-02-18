package com.lenatopoleva.dictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lenatopoleva.dictionary.di.ViewModelFactory
import com.lenatopoleva.dictionary.di.ViewModelKey
import com.lenatopoleva.dictionary.view.main.MainActivityViewModel
import com.lenatopoleva.dictionary.view.wordslist.WordsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainActivityViewModel(mainViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordsListViewModel::class)
    protected abstract fun wordsListViewModel(wordsListViewModel: WordsListViewModel): ViewModel

}