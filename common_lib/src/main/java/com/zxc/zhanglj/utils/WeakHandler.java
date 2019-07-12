package com.zxc.zhanglj.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WeakHandler extends Handler {

    public interface IHandler {
        void handleMessage(Message msg);
    }

    private WeakReference<IHandler> mWeakRef;

    public WeakHandler(IHandler handlermessage) {
        mWeakRef = new WeakReference<>(handlermessage);
    }

    public WeakHandler(IHandler handlermessage, Looper looper) {
        super(looper);
        mWeakRef = new WeakReference<>(handlermessage);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakRef == null) {
            return;
        }
        if (mWeakRef.get() != null) {
            mWeakRef.get().handleMessage(msg);
        }
    }
}