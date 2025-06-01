package hellocompose.unpas.ac.mynote.di

import dagger .Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import hellocompose.unpas.ac.mynote.dao.NoteDao
import hellocompose.unpas.ac.mynote.NoteApi
import hellocompose.unpas.ac.mynote.NoteRepository
@Module
@InstallIn(ViewModelComponent:: class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideNoteRepository(
        api: NoteApi,
        dao: NoteDao
    ): NoteRepository {
        return NoteRepository(api, dao)
        ｝
    }