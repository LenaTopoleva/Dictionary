package com.lenatopoleva.dictionary.di.modules

import com.lenatopoleva.dictionary.di.NAME_LOCAL
import com.lenatopoleva.dictionary.di.NAME_REMOTE
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.repository.Repository
import com.lenatopoleva.dictionary.view.wordslist.WordsListInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = WordsListInteractor(repositoryRemote, repositoryLocal)
}