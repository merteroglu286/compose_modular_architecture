package com.merteroglu286.profile.presentation.contract

import android.net.Uri
import com.merteroglu286.domain.model.Error
import com.merteroglu286.profile.domain.model.UploadedImage

sealed class ProfileEvent {
    data object PickFromGalleryClicked : ProfileEvent()
    data object PickFromCameraClicked : ProfileEvent()
    data object ShowPickerDialog : ProfileEvent()
    data class ImagePicked(val uri: Uri?) : ProfileEvent()
    data object ClearImage : ProfileEvent()
    data object UploadImageClicked : ProfileEvent()
}

sealed class ProfileEffect {
    data object OpenGallery : ProfileEffect()
    data object OpenCamera : ProfileEffect()
    data object OpenPickerDialog : ProfileEffect()
    data class ShowError(val message: String) : ProfileEffect()
    data class ImageUploaded(val uploadedImage: UploadedImage) : ProfileEffect()
}

data class ProfileUiState(
    val selectedImageUri: Uri? = null,
    val isImageSelected: Boolean = false,
    val isUploading: Boolean = false,
    val uploadedImageUrl: String? = null,
    val error: Error? = null
)