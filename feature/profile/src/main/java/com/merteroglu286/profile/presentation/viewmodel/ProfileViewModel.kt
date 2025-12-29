package com.merteroglu286.profile.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merteroglu286.common.image.ImageFileManager
import com.merteroglu286.common.permission.PermissionManager
import com.merteroglu286.presentation.ScreenState
import com.merteroglu286.profile.presentation.contract.ProfileEffect
import com.merteroglu286.profile.presentation.contract.ProfileEvent
import com.merteroglu286.profile.presentation.contract.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val permissionManager: PermissionManager,
    private val imageFileManager: ImageFileManager
) : ViewModel() {

    private var profileUiState = ProfileUiState()

    private val _screenStateFlow = MutableStateFlow<ScreenState<ProfileUiState, Unit>>(
        ScreenState.ScreenContent(profileUiState)
    )
    val screenStateFlow: StateFlow<ScreenState<ProfileUiState, Unit>> get() = _screenStateFlow

    private val _effectFlow = MutableSharedFlow<ProfileEffect>(replay = 0)
    val effectFlow: SharedFlow<ProfileEffect> = _effectFlow.asSharedFlow()

    private val _tempImageUri = MutableStateFlow<Uri?>(null)
    val tempUri = _tempImageUri

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ShowPickerDialog -> sendEffect(ProfileEffect.OpenPickerDialog)
            is ProfileEvent.PickFromGalleryClicked -> sendEffect(ProfileEffect.OpenGallery)
            is ProfileEvent.PickFromCameraClicked -> sendEffect(ProfileEffect.OpenCamera)
            is ProfileEvent.ImagePicked -> handleImagePicked(event.uri)
            is ProfileEvent.ClearImage -> clearImage()
        }
    }

    fun createCameraImageUri(): Uri{
        val uri = imageFileManager.getTempImageUri()
        _tempImageUri.value = uri
        return uri
    }

    private fun handleImagePicked(uri: Uri?) {
        if (uri != null) {
            updateState {
                copy(
                    selectedImageUri = uri,
                    isImageSelected = true
                )
            }
        } else {
            sendEffect(ProfileEffect.ShowError("Resim seçilemedi"))
        }
    }

    private fun clearImage() {
        updateState {
            copy(
                selectedImageUri = null,
                isImageSelected = false
            )
        }
    }

    private fun updateState(state: ProfileUiState.() -> ProfileUiState) {
        profileUiState = profileUiState.state()
        _screenStateFlow.value = ScreenState.ScreenContent(profileUiState)
    }

    private fun sendEffect(effect: ProfileEffect) {
        viewModelScope.launch { _effectFlow.emit(effect) }
    }

    fun getGalleryPermission(): String = permissionManager.getGalleryPermission()
    fun getCameraPermission(): String = permissionManager.getCameraPermission()
}