package com.rendertom.openini.utils;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;

public class Notifier {
  private Notifier() {
    throw new IllegalStateException("Utility class");
  }

  public static void error(@NotNull Project project, @NotNull String title, @NotNull String content) {
    NotificationGroupManager.getInstance()
      .getNotificationGroup("Openini notifications")
      .createNotification(title, content, NotificationType.ERROR)
      .notify(project);
  }

  public static void error(@NotNull String title, @NotNull String content) {
    error(ProjectManager.getInstance().getOpenProjects()[0], title, content);
  }
}