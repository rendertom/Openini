package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import com.rendertom.openini.config.AppConfigSublime;
import com.rendertom.openini.config.AppConfigVSCode;
import com.rendertom.openini.config.AppConfigZed;
import com.rendertom.openini.config.IAppConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Actions extends ActionGroup {
  private static final IAppConfig sublime = new AppConfigSublime();
  private static final IAppConfig vscode = new AppConfigVSCode();
  private static final IAppConfig zed = new AppConfigZed();

  @Override
  public AnAction @NotNull [] getChildren(@Nullable AnActionEvent event) {

    return new AnAction[]{
      new OpenFile(sublime, "Open File in Sublime Text"),
      new OpenProject(sublime, "Open Project in Sublime Text"),

      new Separator(),

      new OpenFile(vscode, "Open File in VSCode"),
      new OpenProject(vscode, "Open Project in VSCode"),

      new Separator(),

      new OpenFile(zed, "Open File in Zed"),
      new OpenProject(zed, "Open Project in Zed"),
    };
  }
}
