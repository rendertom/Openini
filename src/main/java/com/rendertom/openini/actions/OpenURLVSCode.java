package com.rendertom.openini.actions;

import com.rendertom.openini.config.AppConfigVSCode;

public class OpenURLVSCode extends OpenURL {
    OpenURLVSCode() {
        super(new AppConfigVSCode());
    }
}
