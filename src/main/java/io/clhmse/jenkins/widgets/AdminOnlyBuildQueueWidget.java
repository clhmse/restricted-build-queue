package io.clhmse.jenkins.widgets;

import hudson.model.View;
import jenkins.widgets.BuildQueueWidget;
import jenkins.model.Jenkins;
import java.util.List;                      // for List
import java.util.ArrayList;                 // if you need to copy the list
import jenkins.model.queue.QueueItem;      // for QueueItem
import hudson.init.Initializer;
import hudson.init.InitMilestone;

import hudson.Extension;
import jenkins.ExtensionFilter;
import hudson.ExtensionComponent;
import jenkins.widgets.WidgetFactory;


public class AdminOnlyBuildQueueWidget extends BuildQueueWidget {

    public AdminOnlyBuildQueueWidget(String ownerUrl, List<QueueItem> queueItems, boolean filtered) {
        super(ownerUrl, queueItems, filtered);
    }

    /**
     * Only display this widget for admins.
     */
    public boolean isVisible() {
        return Jenkins.get().hasPermission(Jenkins.ADMINISTER);
    }

    @Initializer(after = InitMilestone.PLUGINS_STARTED)
    public static void removeDefaultBuildQueueWidget() {
        Jenkins j = Jenkins.get();
        j.getWidgets().removeIf(w -> w.getClass().equals(BuildQueueWidget.class));
    }

    @Extension
    public static final class RemoveDefaultQueueWidget extends ExtensionFilter {
        @Override
        public <T> boolean allows(Class<T> type, ExtensionComponent<T> component) {
            return WidgetFactory.class != type || !BuildQueueWidget.ViewFactoryImpl.class.isInstance(component.getInstance());
        }
    }

}
