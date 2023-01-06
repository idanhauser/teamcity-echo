package jetbrains.buildServer.configs.kotlin.v10.buildFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Provides AWS Credentials of chosen AWS Connection.
 *
 *
 * @see provideAwsCredentials
 */
open class ProvideAwsCredentials : BuildFeature {
    constructor(init: ProvideAwsCredentials.() -> Unit = {}, base: ProvideAwsCredentials? = null): super(base = base as BuildFeature?) {
        type = "PROVIDE_AWS_CREDS"
        init()
    }

    /**
     * AWS Connection ID.
     *
     *
     * @see AwsConnection
     */
    var awsConnectionId by stringParameter()

    /**
     * Session duration in minutes
     */
    var sessionDuration by stringParameter("awsSessionDuration")

}


/**
 *
 *
 * @see ProvideAwsCredentials
 */
fun BuildFeatures.provideAwsCredentials(base: ProvideAwsCredentials? = null, init: ProvideAwsCredentials.() -> Unit = {}) {
    feature(ProvideAwsCredentials(init, base))
}
