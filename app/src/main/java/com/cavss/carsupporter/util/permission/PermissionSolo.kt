package com.cavss.carsupporter.util.permission


import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SoloRequestPermission(
    setPermission : String,
    whenAccept : @Composable (() -> Unit),
    whenDenied : @Composable (() -> Unit)
) {
    val permissionState = rememberPermissionState(setPermission)
    when {
        permissionState.hasPermission -> {
            whenAccept()
        }
        permissionState.shouldShowRationale || !permissionState.permissionRequested -> {
            whenDenied()
        }
        else -> {
            whenDenied()
        }
    }
}



/*


@Composable
private fun Rationale(
    onDoNotShowRationale: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Column {
        Text("The camera is important for this app. Please grant the permission.")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = onRequestPermission) {
                Text("Request permission")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onDoNotShowRationale) {
                Text("Don't show rationale again")
            }
        }
    }
}

@Composable
private fun PermissionDenied(
    navigateToSettingsScreen: () -> Unit
) {
    Column {
        Text(
            "Camera permission denied. See this FAQ with information about why we " +
                    "need this permission. Please, grant us access on the Settings screen."
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Button(onClick = navigateToSettingsScreen) {
            Text("Open Settings")
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SoloRequestSample(
    setPermission : String,
    navigateToSettingsScreen: () -> Unit
) {
    // Track if the user doesn't want to see the rationale any more.
    var dontShowRequest by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberPermissionState(setPermission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            if (dontShowRequest) {
                Text("기능을 못사용함 // Feature not available")
            } else {
                Rationale(
                    onDoNotShowRationale = { dontShowRequest = true },
                    onRequestPermission = { permissionState.launchPermissionRequest() }
                )
            }
        },
        permissionNotAvailableContent = {
            PermissionDenied(navigateToSettingsScreen)
        }
    ) {
        Text("Camera permission Granted")
    }
}
 */