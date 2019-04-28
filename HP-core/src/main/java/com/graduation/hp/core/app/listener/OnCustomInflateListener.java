package com.graduation.hp.core.app.listener;

import android.content.Context;
import android.view.View;

/**
 * Created by Ning on 2019/2/21.
 */

public interface OnCustomInflateListener{

    View onInflaterView(Context context, OnItemClickListener clickListener);

}
