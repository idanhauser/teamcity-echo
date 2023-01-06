package jetbrains.buildServer.configs.kotlin.v10.buildFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [build feature](https://www.jetbrains.com/help/teamcity/?Pull+Requests) that introduces GitHub pull requests integration
 *
 * **Example.**
 * Enables watching for the GitHub pull requests.
 * In the public repositories limits the pull requests to those created by the members of the same organization and having the "refs/heads/main" as a target branch.
 * ```
 * pullRequests {
 *     provider = github {
 *         authType = token {
 *             token = "<GitHub access token>"
 *         }
 *         filterTargetBranch = "refs/heads/main"
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for the pull requests in the GitLab server with the provided URL.
 * The pull requests are limited to those having the "refs/heads/main" as a target branch.
 * ```
 * pullRequests {
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>"
 *         authType = token {
 *             token = "<GitLab personal token>"
 *         }
 *         filterTargetBranch = "refs/heads/main"
 *     }
 * }
 * ```
 *
 *
 * @see pullRequests
 */
open class PullRequests : BuildFeature {
    constructor(init: PullRequests.() -> Unit = {}, base: PullRequests? = null): super(base = base as BuildFeature?) {
        type = "pullRequests"
        init()
    }

    /**
     * Id of the VCS root to extract pull request information from.
     * Set to an empty string to extract pull request information from the first VCS root attached to a build configuration.
     */
    var vcsRootExtId by stringParameter("vcsRootId")

    var provider by compoundParameter<Provider>("providerType")

    sealed class Provider(value: String? = null): CompoundParam(value) {
        class Github() : Provider("github") {

            /**
             * GitHub/GHE server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class VcsRoot() : AuthType("vcsRoot") {

                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Filter by the role of pull request contributors
             */
            var filterAuthorRole by enumParameter<GitHubRoleFilter>()

        }

        class Gitlab() : Provider("gitlab") {

            /**
             * GitLab server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class VcsRoot() : AuthType("vcsRoot") {

                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

        }

        class BitbucketServer() : Provider("bitbucketServer") {

            /**
             * Bitbucket Server / Data Center server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class VcsRoot() : AuthType("vcsRoot") {

                }

                class Password() : AuthType("password") {

                    /**
                     * Username
                     */
                    var username by stringParameter()

                    /**
                     * Password
                     */
                    var password by stringParameter("secure:password")

                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Username and password
             */
            fun password(init: AuthType.Password.() -> Unit = {}) : AuthType.Password {
                val result = AuthType.Password()
                result.init()
                return result
            }

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Enables detection of officially unsupported pull request branches (pull-requests/N) instead of source branches.
             * This option is intended for backward compatibility only. Try to avoid using it.
             * Be careful: new builds might be triggered for changes committed within the last hour after switching.
             */
            var usePullRequestBranches by booleanParameter("useRequestBranches")

        }

        class BitbucketCloud() : Provider("bitbucketCloud") {

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class VcsRoot() : AuthType("vcsRoot") {

                }

                class Password() : AuthType("password") {

                    /**
                     * Username
                     */
                    var username by stringParameter()

                    /**
                     * Password
                     */
                    var password by stringParameter("secure:password")

                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Username and password
             */
            fun password(init: AuthType.Password.() -> Unit = {}) : AuthType.Password {
                val result = AuthType.Password()
                result.init()
                return result
            }

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

        }

        class AzureDevOps() : Provider("azureDevOps") {

            /**
             * Azure DevOps project page URL
             */
            var projectUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                }
            }

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

        }

        class JetbrainsSpace() : Provider("jetbrainsSpace") {

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("spaceCredentialsType")

            sealed class AuthType(value: String? = null): CompoundParam(value) {
                class Connection() : AuthType("spaceCredentialsConnection") {

                    /**
                     * JetBrains Space Connection project feature ID
                     */
                    var connectionId by stringParameter("spaceConnectionId")

                }
            }

            /**
             * Authentication using JetBrains Space Connection
             */
            fun connection(init: AuthType.Connection.() -> Unit = {}) : AuthType.Connection {
                val result = AuthType.Connection()
                result.init()
                return result
            }

        }
    }

    /**
     * GitHub or GitHub Enterprise
     */
    fun github(init: Provider.Github.() -> Unit = {}) : Provider.Github {
        val result = Provider.Github()
        result.init()
        return result
    }

    /**
     * GitLab.com or GitLab CE/EE
     */
    fun gitlab(init: Provider.Gitlab.() -> Unit = {}) : Provider.Gitlab {
        val result = Provider.Gitlab()
        result.init()
        return result
    }

    /**
     * Bitbucket Server / Data Center
     */
    fun bitbucketServer(init: Provider.BitbucketServer.() -> Unit = {}) : Provider.BitbucketServer {
        val result = Provider.BitbucketServer()
        result.init()
        return result
    }

    /**
     * Bitbucket Cloud
     */
    fun bitbucketCloud(init: Provider.BitbucketCloud.() -> Unit = {}) : Provider.BitbucketCloud {
        val result = Provider.BitbucketCloud()
        result.init()
        return result
    }

    /**
     * Azure DevOps Services/Server
     */
    fun azureDevOps(init: Provider.AzureDevOps.() -> Unit = {}) : Provider.AzureDevOps {
        val result = Provider.AzureDevOps()
        result.init()
        return result
    }

    /**
     * JetBrains Space
     */
    fun jetbrainsSpace(init: Provider.JetbrainsSpace.() -> Unit = {}) : Provider.JetbrainsSpace {
        val result = Provider.JetbrainsSpace()
        result.init()
        return result
    }

    /**
     * Pull request contributor role filter options
     */
    enum class GitHubRoleFilter {
        MEMBER,
        MEMBER_OR_COLLABORATOR,
        EVERYBODY;

    }
}


/**
 * Enables [pull requests integration](https://www.jetbrains.com/help/teamcity/?Pull+Requests)
 *
 * **Example.**
 * Enables watching for the GitHub pull requests.
 * In the public repositories limits the pull requests to those created by the members of the same organization and having the "refs/heads/main" as a target branch.
 * ```
 * pullRequests {
 *     provider = github {
 *         authType = token {
 *             token = "<GitHub access token>"
 *         }
 *         filterTargetBranch = "refs/heads/main"
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for the pull requests in the GitLab server with the provided URL.
 * The pull requests are limited to those having the "refs/heads/main" as a target branch.
 * ```
 * pullRequests {
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>"
 *         authType = token {
 *             token = "<GitLab personal token>"
 *         }
 *         filterTargetBranch = "refs/heads/main"
 *     }
 * }
 * ```
 *
 *
 * @see PullRequests
 */
fun BuildFeatures.pullRequests(base: PullRequests? = null, init: PullRequests.() -> Unit = {}) {
    feature(PullRequests(init, base))
}
