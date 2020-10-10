/**
 * Created by TuanPM (tuanpm@live.com) on 1/4/16.
 */

package com.tuanpm.RCTSmartconfig;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.tuanpm.RCTSmartconfig.ThiefUtil;

public class RCTSmartconfigModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RCTSmartconfigModule";
    private final ReactApplicationContext _reactContext;
    private IEsptouchTask mEsptouchTask;

    public RCTSmartconfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        _reactContext = reactContext;
        ThiefUtil._reactContext= reactContext;
    }

    @Override
    public String getName() {
        return "Smartconfig";
    }

    @ReactMethod
    public void stop() {
      if (mEsptouchTask != null) {
        ThiefUtil.sendEvent(TAG, "cancel task");
        mEsptouchTask.interrupt();
      }
    }

    @ReactMethod
    public void start(final ReadableMap options, final Promise promise) {
      String ssid = options.getString("ssid");
      String pass = options.getString("password");
      Boolean hidden = false;
      //Int taskResultCountStr = 1;
      ThiefUtil.sendEvent(TAG, "ssid " + ssid + ":pass " + pass);
    try{
      stop();
      new EsptouchAsyncTask(new TaskListener() {
        @Override
        public void onFinished(List<IEsptouchResult> result) {
            // Do Something after the task has finished
            WritableArray ret = Arguments.createArray();

            Boolean resolved = false;
            for (IEsptouchResult resultInList : result) {
              if(!resultInList.isCancelled() &&resultInList.getBssid() != null) {
                ret.pushString(resultInList.getBssid());
                ret.pushString(resultInList.getInetAddress().getHostAddress());
                      // ret.pushMap(map);

                resolved = true;
                if (!resultInList.isSuc())
                  break;

            }
          }

            if(resolved) {
              ThiefUtil.sendEvent("Success run smartconfig");
              promise.resolve(ret);
            } else {
              ThiefUtil.sendEvent("Error run smartconfig");
              promise.reject("new IllegalViewOperationException()");
            }
      }
      }).execute(ssid, new String(""), pass, "YES", "1");
    }
    catch(Throwable e){
      ThiefUtil.sendEvent(e.getMessage());
    }
    }


    public interface TaskListener {
        public void onFinished(List<IEsptouchResult> result);
    }

    private class EsptouchAsyncTask extends AsyncTask<String, Void, List<IEsptouchResult>> {

      //
      // public interface TaskListener {
      //     public void onFinished(List<IEsptouchResult> result);
      // }
      private final TaskListener taskListener;

      public EsptouchAsyncTask(TaskListener listener) {
        // The listener reference is passed in through the constructor
        this.taskListener = listener;
      }


      // without the lock, if the user tap confirm and cancel quickly enough,
      // the bug will arise. the reason is follows:
      // 0. task is starting created, but not finished
      // 1. the task is cancel for the task hasn't been created, it do nothing
      // 2. task is created
      // 3. Oops, the task should be cancelled, but it is running
      private final Object mLock = new Object();

      @Override
      protected void onPreExecute() {
        ThiefUtil.sendEvent("Begin task");
      }
      @Override
      protected List<IEsptouchResult> doInBackground(String... params) {
        ThiefUtil.sendEvent("doing task");
        int taskResultCount = -1;
      try{
        synchronized (mLock) {
          String apSsid = params[0];
          String apBssid =  params[1];
          String apPassword = params[2];
          String isSsidHiddenStr = params[3];
          String taskResultCountStr = params[4];
          boolean isSsidHidden = false;
          if (isSsidHiddenStr.equals("YES")) {
            isSsidHidden = true;
          }
          taskResultCount = Integer.parseInt(taskResultCountStr);
          mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,
              isSsidHidden, getCurrentActivity());

          //mEsptouchTask.setEsptouchListener(myListener);
        }
        List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
        return resultList;
      }
      catch(Throwable e){
        ThiefUtil.sendEvent(e.getMessage());
      }
        return null;
      }

      @Override
      protected void onPostExecute(List<IEsptouchResult> result) {
      try{
        IEsptouchResult firstResult = result.get(0);
        // check whether the task is cancelled and no results received
        if (!firstResult.isCancelled()) {
          if(this.taskListener != null) {

           // And if it is we call the callback function on it.
           this.taskListener.onFinished(result);
         }
        }
      }
      catch(Throwable e){
        ThiefUtil.sendEvent(e.getMessage());
      }
      }
    }
}
