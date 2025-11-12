package io.yourcompany.jenkins.widgets;

import hudson.init.InitMilestone;
import hudson.init.Initializer;
import jenkins.model.Jenkins;
import io.clhmse.jenkins.widgets.AdminOnlyBuildQueueWidget;

public class ReplaceDefaultBuildQueueWidget {

    @Initializer(after = InitMilestone.PLUGINS_STARTED)
    public static void swapBuildQueueWidget() {
        Jenkins j = Jenkins.getInstanceOrNull();
        if (j == null) return; // safe for tests
        
        try {
            j.getWidgets().removeIf(widget ->
                widget.getClass().getName().equals("jenkins.widgets.BuildQueueWidget")
            );
            j.getWidgets().add(new AdminOnlyBuildQueueWidget());
        } catch (Exception e) {
            // log and ignore during test or initialization issues
            System.err.println("Failed to replace BuildQueueWidget: " + e.getMessage());
        }
    }

}
