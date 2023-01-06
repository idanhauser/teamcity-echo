package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature defining an OAuth connection settings for Google
 *
 *
 * @see googleConnection
 */
open class GoogleConnection : ProjectFeature {
    constructor(init: GoogleConnection.() -> Unit = {}, base: GoogleConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "Google")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * OAuth connection client id
     */
    var clientId by stringParameter("googleClientId")

    /**
     * OAuth connection client secret
     */
    var clientSecret by stringParameter("secure:googleClientSecret")

}


/**
 * Creates a Google OAuth connection in the current project
 *
 *
 * @see GoogleConnection
 */
fun ProjectFeatures.googleConnection(base: GoogleConnection? = null, init: GoogleConnection.() -> Unit = {}) {
    feature(GoogleConnection(init, base))
}
