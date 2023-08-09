package com.rendertom.openini.Utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

public class File {
    public static boolean exists(VirtualFile virtualFile) {
        return virtualFile != null && virtualFile.exists();
    }

    public static VirtualFile getProjectFileDirectory(AnActionEvent event) {
        return event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY);
    }

    public static VirtualFile getVirtualFile(AnActionEvent event) {
        return event.getData(PlatformDataKeys.VIRTUAL_FILE);
    }
}
