package io.mindset.jagamental.navigation

import kotlinx.serialization.Serializable

object Route {

    @Serializable
    object Welcome

    @Serializable
    object Onboarding

    @Serializable
    object Register

    @Serializable
    object Login

    @Serializable
    object Dashboard
}