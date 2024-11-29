package io.mindset.jagamental.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth : Screen() {
        @Serializable
        data object Register : Screen()

        @Serializable
        data object Login : Screen()
    }

    @Serializable
    data object App : Screen() {

        @Serializable
        data object Dashboard : Screen()

        @Serializable
        data object Profile : Screen()

        @Serializable
        data object MainJournalScreen : Screen()

        @Serializable
        data object CapturePhotoScreen : Screen()

        @Serializable
        data class ResultPreviewScreen(
            val photoUri: String
        ): Screen()

        @Serializable
        data class InputJournalScreen(
            val journalId: String
        ) : Screen()

        @Serializable
        data class PhotoResultScreen(
            val journalId: String,
            val emotion: String,
            val words: String,
            val photoUrl: String
        ): Screen()

        @Serializable
        data class JournalResultScreen(
            val journalId: String
        ) : Screen()
    }
}
