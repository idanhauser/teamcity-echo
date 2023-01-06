package jetbrains.teamcity;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EchoRunner extends RunType {

    private final PluginDescriptor descriptor;

    public EchoRunner(RunTypeRegistry registry, PluginDescriptor descriptor) {
        this.descriptor = descriptor;
        registry.registerRunType(this);

    }

    @NotNull
    @Override
    public String getType() {
        return EchoConstants.RUNNER_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Echo Runner";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "This is my echo runner";
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return properties -> {
            List<InvalidProperty> lst = new ArrayList<>();
//            final String message = properties.get(MESSAGE_KEY);
//            if (message == null) {
//                lst.add(new InvalidProperty(MESSAGE_KEY, "Should not be null"));
//            } else if (message.startsWith("fail")) {
//                lst.add(new InvalidProperty(MESSAGE_KEY, "Should not start with fail"));
//            }
            return lst;
        };
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return descriptor.getPluginResourcesPath("editEchoParameters.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return descriptor.getPluginResourcesPath("viewEchoParameters.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        return null;
    }

    public PluginDescriptor getDescriptor() {
        return descriptor;
    }
}