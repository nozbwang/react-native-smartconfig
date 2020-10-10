
## 升级RN后，使用react-native-smartconfig遇到了很多问题，所以决定fork进行修改

### 1. No interface method pushMap(Lcom/facebook/react/bridge/WritableMap;)V in class Lcom/facebook/react/bridge/WritableArray; or its super classes (declaration of 'com.facebook.react.bridge.WritableArray' appears in /data/app/com.example-K7SKRFpCkWFuGIH_USeqlA==/base.apk)

因为高版本的RN修改了WritableArray的接口。
导致RCTSmartconfigModule.start()方法中WritableArray.pushMap(WritableMap)报错。

### 2. 多次调用Smartconfig.start方法后，APP闪退。 

因为__EsptouchTask.__listenAsyn方法会启动一个线程，线程里的方法会报错，导致闪退。给线程里的方法加上异常保护就可以了。

### 3. 本地调试RN插件，看不到日志。

因为我是直接使用命令行启动的RN应用，IDE使用的是ATOM，所以看不到logcat的日志。所以直接采用Android原生代码向JS发送消息，在JS模块中进行日志打印。


## 添加了example

1.运行 (需首先切换当前目录为example目录)： 

```bash
yarn && yarn android
```

2.如果你正在尝试修改react-native-smartconfig-esp的内容，可以在example目录下使用以下命令使其实时生效

```bash
rm -rf node_modules\react-native-smartconfig-esp8266\ && yarn --force && yarn android
```

## Description

[react-native](https://github.com/facebook/react-native) module for [ESP8266 ESPTOUCH Smart config](https://github.com/EspressifApp)

## Featues
* Support both IOS and Android
* React Native Promise support
* Fast way to do configure wifi network for IOT device

## Getting started
### Mostly automatic install
1. `npm install rnpm --global`
2. `npm install react-native-smartconfig@latest --save`
3. `rnpm link react-native-smartconfig`

### Manual install
#### iOS
- `npm install react-native-smartconfig@latest --save`
-  In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
- Go to `node_modules` ➜ `react-native-smartconfig` and add `RCTSmartconfig.xcodeproj`
- In XCode, in the project navigator, select your project. Add `libRCTSmartconfig.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
- Click `RCTSmartconfig.xcodeproj` in the project navigator and go the `Build Settings` tab. Make sure 'All' is toggled on (instead of 'Basic'). In the `Search Paths` section, look for `Header Search Paths` and make sure it contains both `$(SRCROOT)/../../react-native/React` - mark  as `recursive`.
- Run your project (`Cmd+R`)


#### Android

-  `npm install react-native-smartconfig@latest --save`
-  For older version.  Modify the ReactInstanceManager.builder() calls chain in `android/app/main/java/.../MainActivity.java` to include:

```javascript
import com.tuanpm.RCTSmartconfig; // import for older version

.addPackage(new RCTSmartconfigPackage()) //for older version
```
-  For newest version.  Modify the ReactInstanceManager.builder() calls chain in `android/app/main/java/.../MainApplication.java` to include:
```javascript
import com.tuanpm.RCTSmartconfig.RCTSmartconfigPackage; // import for newest version of react-native

new RCTSmartconfigPackage()           // for newest version of react-native
```

-  Append the following lines to `android/settings.gradle` before `include ':app'`:

```
include ':react-native-smartconfig'
project(':react-native-smartconfig').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-smartconfig/android')
```

- Insert the following lines inside the dependencies block in `android/app/build.gradle`, dont missing `apply plugin:'java'` on top:

```
compile project(':react-native-smartconfig')
```

Notes:

```
dependencies {
  compile project(':react-native-smartconfig')
}
```



## Usage

```javascript
import Smartconfig from 'react-native-smartconfig';

Smartconfig.start({
  type: 'esptouch', //or airkiss, now doesn't not effect
  ssid: 'wifi-network-ssid',
  bssid: 'filter-device', //"" if not need to filter (don't use null)
  password: 'wifi-password',
  timeout: 50000 //now doesn't not effect
}).then(function(results){
  //Array of device success do smartconfig
  console.log(results);
  /*[
    {
      'bssid': 'device-bssi1', //device bssid
      'ipv4': '192.168.1.11' //local ip address
    },
    {
      'bssid': 'device-bssi2', //device bssid
      'ipv4': '192.168.1.12' //local ip address
    },
    ...
  ]*/
}).catch(function(error) {

});

Smartconfig.stop(); //interrupt task
```

## Todo

* [ ] Support automatic get current wifi network ssid
* [ ] Set timeout effect
* [ ] Support airkiss

## LICENSE

```
  The MIT License (MIT)

  Copyright (c) 2015 <TuanPM> https://twitter.com/tuanpmt

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
```
