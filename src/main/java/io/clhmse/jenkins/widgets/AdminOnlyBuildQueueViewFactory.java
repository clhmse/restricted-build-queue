package io.clhmse.jenkins.widgets;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.View;
import jenkins.model.Jenkins;
import jenkins.widgets.WidgetFactory;
import jenkins.widgets.BuildQueueWidget;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Extension(ordinal = 300)
public class AdminOnlyBuildQueueViewFactory extends WidgetFactory<View, AdminOnlyBuildQueueWidget> {

    @Override
    public Class<View> type() {
        return View.class;
    }

    @Override
    public Class<AdminOnlyBuildQueueWidget> widgetType() {
        return AdminOnlyBuildQueueWidget.class;
    }

    @Override
    public Collection<AdminOnlyBuildQueueWidget> createFor(View target) {

        Jenkins j = Jenkins.get();
        j.getWidgets().removeIf(w -> w instanceof BuildQueueWidget);

        if (!Jenkins.get().hasPermission(Jenkins.ADMINISTER)) {
            return List.of();
        }

        return List.of(new AdminOnlyBuildQueueWidget(
            target.getUrl(),
            new ArrayList<>(target.getQueueItems()),
            target.isFilterQueue()
        ));
    }
}
