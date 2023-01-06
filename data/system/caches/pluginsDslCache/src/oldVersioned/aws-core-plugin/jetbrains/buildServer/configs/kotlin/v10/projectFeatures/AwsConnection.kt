package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Stores information and configuration for the access to Amazon Web Services.
 *
 *
 * @see awsConnection
 */
open class AwsConnection : ProjectFeature {
    constructor(init: AwsConnection.() -> Unit = {}, base: AwsConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "AWS")
        init()
    }

    /**
     * AWS connection display name
     */
    var name by stringParameter("displayName")

    /**
     * Region where this connection will be used.
     */
    var regionName by stringParameter("awsRegionName")

    /**
     * Custom identifier for this AWS Connection
     */
    var projectFeatureId by stringParameter()

    /**
     * The way how to obtain credentials (just provide the keys, assume IAM role or other)
     */
    var credentialsType by compoundParameter<CredentialsType>("awsCredentialsType")

    sealed class CredentialsType(value: String? = null): CompoundParam(value) {
        class Static() : CredentialsType("awsAccessKeys") {

            /**
             * Access Key ID
             */
            var accessKeyId by stringParameter("awsAccessKeyId")

            /**
             * Secret Access Key
             */
            var secretAccessKey by stringParameter("secure:awsSecretAccessKey")

            /**
             * Whether to use temporary credentials provided by STS service
             */
            var useSessionCredentials by booleanParameter("awsSessionCredentials")

            /**
             * Endpoint from where to obtain session credentials
             */
            var stsEndpoint by stringParameter("awsStsEndpoint")

        }

        class IamRole() : CredentialsType("awsAssumeIamRole") {

            /**
             * AWS IAM Role ARN
             */
            var roleArn by stringParameter("awsIamRoleArn")

            /**
             * An identifier for the assumed role session. Use the role session name to uniquely identify a session when the same role is assumed by different principals or for different reasons.
             */
            var sessionName by stringParameter("awsIamRoleSessionName")

            /**
             * Principal AWS Connection ID (Who will assume the IAM Role)
             */
            var awsConnectionId by stringParameter()

            /**
             * Endpoint from where to obtain session credentials of the assumed IAM Role
             */
            var stsEndpoint by stringParameter("awsStsEndpoint")

        }

        class Default() : CredentialsType("defaultProvider") {

        }
    }

    fun static(init: CredentialsType.Static.() -> Unit = {}) : CredentialsType.Static {
        val result = CredentialsType.Static()
        result.init()
        return result
    }

    /**
     * Uses another (principal) AWS connection to assume an IAM Role with its permissions. Please, note that the principal connection should have rights to assume the role, more: https://docs.aws.amazon.com/workdocs/latest/developerguide/wd-iam-grantdev.html
     */
    fun iamRole(init: CredentialsType.IamRole.() -> Unit = {}) : CredentialsType.IamRole {
        val result = CredentialsType.IamRole()
        result.init()
        return result
    }

    /**
     * Looks for credentials in this order:
     * Env Vars - AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
     * Java System Properties - aws.accessKeyId and aws.secretKey
     * Web Identity Token credentials from the environment or container
     * Credential profiles file at the default location (~/.aws/credentials)
     * Credentials delivered through the Amazon EC2 container service if AWS_CONTAINER_CREDENTIALS_RELATIVE_URI" environment variable is set and security manager has permission to access the variable,
     * Instance profile credentials delivered through the Amazon EC2 metadata service
     * more: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
     */
    fun default() = CredentialsType.Default()

}


/**
 *
 *
 * @see AwsConnection
 */
fun ProjectFeatures.awsConnection(base: AwsConnection? = null, init: AwsConnection.() -> Unit = {}) {
    feature(AwsConnection(init, base))
}