# AndroidLimia

AndroidLimia is a Kotlin + Jetpack Compose Android dialer app inspired by Nokia Lumia / Windows Phone Metro UI.

## Stack

- Kotlin, Coroutines, Flow
- Jetpack Compose + Material 3 (Metro-styled)
- MVVM + Repository pattern
- Hilt DI
- Room + DataStore
- ContactsContract + CallLog integration
- Android 10+ (API 29+)

## Implemented

- Metro-like typography-first shell with panoramic tab switching
- Dialer keypad with hold-delete, haptics, call intent, and suggestion persistence
- Contacts page with search + favorites
- Recents page with search + quick callback
- Settings for accent color, theme mode (system/light/AMOLED), font scale, vibration
- Default dialer role request on Android Q+
- Foreground call-service placeholder architecture + boot receiver

## Permissions

- `CALL_PHONE`
- `READ_CONTACTS`
- `READ_CALL_LOG`
- `POST_NOTIFICATIONS`
- `RECORD_AUDIO`
- `VIBRATE`

## Run

1. Open in Android Studio (latest stable)
2. Sync Gradle
3. Run on Android 10+ device/emulator
4. Grant requested permissions

## Notes

This repository currently provides a full functional baseline and architecture for Lumia-style dialer behavior. In-call UI, full Telecom stack handling, dual-SIM flows, spam detection, and live tile widget are scaffolded/placeholder-ready for iterative expansion.
