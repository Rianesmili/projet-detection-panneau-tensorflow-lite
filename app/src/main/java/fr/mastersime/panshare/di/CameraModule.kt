package fr.mastersime.panshare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.mastersime.panshare.data.SavePhotoToGalleryUseCase

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    fun provideSavePhotoToGalleryUseCase(@ApplicationContext context: Context): SavePhotoToGalleryUseCase {
        return SavePhotoToGalleryUseCase(context)
    }
}