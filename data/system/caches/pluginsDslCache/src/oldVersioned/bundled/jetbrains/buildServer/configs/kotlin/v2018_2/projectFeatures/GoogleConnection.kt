package jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.*

/**
 * Project feature defining an OAuth connection settings for Google
 *
 *
 * @see googleConnection
 */
open class GoogleConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "Google")
    }

    constructor(init: GoogleConnection.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (clientId == null && !hasParam("googleClientId")) {
            consumer.consumePropertyError("clientId", "mandatory 'clientId' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:googleClientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
    }
}


/**
 * Creates a Google OAuth connection in the current project
 *
 *
 * @see GoogleConnection
 */
fun ProjectFeatures.googleConnection(init: GoogleConnection.() -> Unit): GoogleConnection {
    val result = GoogleConnection(init)
    feature(result)
    return result
}
