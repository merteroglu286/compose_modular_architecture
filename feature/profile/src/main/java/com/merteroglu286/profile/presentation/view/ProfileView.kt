package com.merteroglu286.profile.presentation.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.merteroglu286.common.constants.MimeTypes
import com.merteroglu286.navigator.core.AppNavigator
import com.merteroglu286.presentation.ScreenState
import com.merteroglu286.profile.presentation.contract.ProfileEffect
import com.merteroglu286.profile.presentation.contract.ProfileEvent
import com.merteroglu286.profile.presentation.contract.ProfileUiState
import com.merteroglu286.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(appNavigator: AppNavigator) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val screenState by profileViewModel.screenStateFlow.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    // Galeri launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileViewModel.onEvent(ProfileEvent.ImagePicked(uri))
    }

    // Kamera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            profileViewModel.tempUri.value?.let {
                profileViewModel.onEvent(ProfileEvent.ImagePicked(it))
            }
        }
    }

    // İzin launcher'ları
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = profileViewModel.createCameraImageUri()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Kamera izni reddedildi", Toast.LENGTH_SHORT).show()
        }
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch(MimeTypes.IMAGE)
        } else {
            Toast.makeText(context, "Galeri izni reddedildi", Toast.LENGTH_SHORT).show()
        }
    }

    // Effect dinleyicisi
    LaunchedEffect(Unit) {
        profileViewModel.effectFlow.collect { effect ->
            when (effect) {
                is ProfileEffect.OpenCamera -> {
                    cameraPermissionLauncher.launch(profileViewModel.getCameraPermission())
                }

                is ProfileEffect.OpenGallery -> {
                    storagePermissionLauncher.launch(profileViewModel.getGalleryPermission())
                }

                is ProfileEffect.OpenPickerDialog -> {
                    showDialog = true
                }

                is ProfileEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Dialog göster
    if (showDialog) {
        ImagePickerDialog(
            onDismiss = { showDialog = false },
            onCameraClick = {
                showDialog = false
                profileViewModel.onEvent(ProfileEvent.PickFromCameraClicked)
            },
            onGalleryClick = {
                showDialog = false
                profileViewModel.onEvent(ProfileEvent.PickFromGalleryClicked)
            }
        )
    }

    ScreenState.of(screenState = screenState) {
        onUiState { updatedState ->
            ScreenUiContent(updatedState, profileViewModel)
        }
    }
}

@Composable
fun ScreenUiContent(uiState: ProfileUiState, viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Profil resmi göster
        if (uiState.isImageSelected && uiState.selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(uiState.selectedImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.onEvent(ProfileEvent.ClearImage) }) {
                Text("Resmi Temizle")
            }
        } else {
            Text("Henüz resim seçilmedi")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.onEvent(ProfileEvent.ShowPickerDialog) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Resim Seç")
        }
    }
}

@Composable
fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Resim Seç") },
        text = {
            Column {
                TextButton(
                    onClick = onCameraClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kamera")
                }
                TextButton(
                    onClick = onGalleryClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Galeri")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}