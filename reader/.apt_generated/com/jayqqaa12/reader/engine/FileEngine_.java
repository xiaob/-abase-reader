//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.jayqqaa12.reader.engine;

import android.content.Context;

public final class FileEngine_
    extends FileEngine
{

    private Context context_;

    private FileEngine_(Context context) {
        context_ = context;
        init_();
    }

    public static FileEngine_ getInstance_(Context context) {
        return new FileEngine_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
