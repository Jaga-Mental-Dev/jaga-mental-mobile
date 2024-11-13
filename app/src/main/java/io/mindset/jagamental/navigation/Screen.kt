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
        data object Journal : Screen()
    }
}
