/**
 * log channel
 */

package com.tuanpm.RCTSmartconfig;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import com.tuanpm.RCTSmartconfig.ThiefUtil;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.react.bridge.*;

import javax.annotation.Nullable;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ThiefUtil {

    public static ReactApplicationContext _reactContext;
    private static final String TAG = "RCTSmartconfigModule";
    private static final String EVENT_NAME = "uploadservice";

    public static void sendEvent(String tag, String value) {
      sendEvent(value);
    }

    public static void sendEvent(String value) {
      if (_reactContext != null) {
          WritableMap params = new WritableNativeMap();
          params.putString("type", TAG);
          params.putString("value", value);
          _reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(EVENT_NAME, params);
      }
    }
}
