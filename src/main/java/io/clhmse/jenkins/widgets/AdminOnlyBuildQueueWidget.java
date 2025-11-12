package io.clhmse.jenkins.widgets;

import jenkins.model.Jenkins;
import jenkins.widgets.BuildQueueWidget;

public class AdminOnlyBuildQueueWidget extends BuildQueueWidget {

    public AdminOnlyBuildQueueWidget() {
        // Pass the owner URL ("/") and null to let the superclass handle the queue internally
        super("/", null);
    }

    // Hide widget for non-admins
    @Override
    public String getOwnerUrl() {
        if (!Jenkins.get().hasPermission(Jenkins.ADMINISTER)) {
            return null;
        }
        return super.getOwnerUrl();
    }
}
