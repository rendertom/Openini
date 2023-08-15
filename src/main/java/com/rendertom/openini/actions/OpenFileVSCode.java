package com.rendertom.openini.actions;

import com.rendertom.openini.config.AppConfigVSCode;

public class OpenFileVSCode extends OpenFile {
  OpenFileVSCode() {
    super(new AppConfigVSCode());
  }
}
