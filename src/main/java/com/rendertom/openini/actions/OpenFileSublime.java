package com.rendertom.openini.actions;

import com.rendertom.openini.config.AppConfigSublime;

public class OpenFileSublime extends OpenFile {
    OpenFileSublime() {
        super(new AppConfigSublime());
    }
}
