package com.rendertom.openini.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class NotifierTest {

  @Mock
  private Project mockProject;
  @Mock
  private NotificationGroupManager mockNotificationGroupManager;
  @Mock
  private NotificationGroup mockNotificationGroup;
  @Mock
  private Notification mockNotification;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Mockito.when(mockNotificationGroupManager.getNotificationGroup("Openini notifications")).thenReturn(mockNotificationGroup);
    Mockito.when(mockNotificationGroup.createNotification(Mockito.anyString(), Mockito.anyString(), Mockito.any(NotificationType.class))).thenReturn(mockNotification);
  }

  @Test
  void testPrivateConstructor() throws NoSuchMethodException {
    Constructor<Notifier> constructor = Notifier.class.getDeclaredConstructor();
    Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

    constructor.setAccessible(true); // Make the constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance, "Constructor invocation did not throw an IllegalStateException");

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException, "Thrown exception is not an IllegalStateException");
    Assertions.assertEquals("Utility class", exception.getCause().getMessage(), "Exception message does not match");
  }

  @Test
  void testErrorWithProject() {
    try (MockedStatic<NotificationGroupManager> mockedStatic = Mockito.mockStatic(NotificationGroupManager.class)) {
      mockedStatic.when(NotificationGroupManager::getInstance).thenReturn(mockNotificationGroupManager);

      Notifier.error(mockProject, "Test Error", "This is a test error");

      Mockito.verify(mockNotificationGroup).createNotification("Test Error", "This is a test error", NotificationType.ERROR);
      Mockito.verify(mockNotification).notify(mockProject);
    }
  }

  @Test
  void testErrorWithoutProject() {
    Project[] projects = new Project[]{mockProject};
    try (MockedStatic<ProjectManager> mockedProjectManager = Mockito.mockStatic(ProjectManager.class);
         MockedStatic<NotificationGroupManager> mockedNotificationGroupManager = Mockito.mockStatic(NotificationGroupManager.class)) {

      mockedProjectManager.when(ProjectManager::getInstance).thenReturn(Mockito.mock(ProjectManager.class));
      Mockito.when(ProjectManager.getInstance().getOpenProjects()).thenReturn(projects);
      mockedNotificationGroupManager.when(NotificationGroupManager::getInstance).thenReturn(mockNotificationGroupManager);

      Notifier.error("Test Error", "This is a test error");

      Mockito.verify(mockNotificationGroup).createNotification("Test Error", "This is a test error", NotificationType.ERROR);
      Mockito.verify(mockNotification).notify(projects[0]);
    }
  }
}
