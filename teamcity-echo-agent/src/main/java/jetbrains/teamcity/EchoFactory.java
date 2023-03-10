package jetbrains.teamcity;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.jetbrains.annotations.NotNull;

public class EchoFactory implements CommandLineBuildServiceFactory {
    @NotNull
    @Override
    public CommandLineBuildService createService() {
        return new EchoService();
    }

    @NotNull
    @Override
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            @Override
            public String getType() {
                return EchoConstants.RUNNER_TYPE;
            }// String RUNNER_TYPE = "echoRunnerType";

            @Override
            public boolean canRun(@NotNull BuildAgentConfiguration buildAgentConfiguration) {
                return true;
            }
        };
    }
}
