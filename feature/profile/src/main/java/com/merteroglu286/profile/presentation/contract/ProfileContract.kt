package com.merteroglu286.profile.presentation.contract

import android.net.Uri

sealed class ProfileEvent {
    data object PickFromGalleryClicked : ProfileEvent()
    data object PickFromCameraClicked : ProfileEvent()
    data object ShowPickerDialog : ProfileEvent()
    data class ImagePicked(val uri: Uri?) : ProfileEvent()
    data object ClearImage : ProfileEvent()
}

sealed class ProfileEffect {
    data object OpenGallery : ProfileEffect()
    data object OpenCamera : ProfileEffect()
    data object OpenPickerDialog : ProfileEffect()
    data class ShowError(val message: String) : ProfileEffect()
}

data class ProfileUiState(
    val selectedImageUri: Uri? = null,
    val isImageSelected: Boolean = false
)