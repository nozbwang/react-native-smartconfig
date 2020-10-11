## Description

[react-native](https://github.com/facebook/react-native) module for [ESP8266 ESPTOUCH Smart config](https://github.com/EspressifApp)

- Forked from [tuanpmt/react-native-smartconfig](https://github.com/tuanpmt/react-native-smartconfig).

- Improvements are made to increase its stablity and become compatible with higher versions of react-native (e.g. 6.3.1)

- Example app is provided if you want to test or improve this repo again.

## Features
* Support both IOS and Android
* React Native Promise support
* Fast way to do configure wifi network for IOT device
* Log can be sent to js module

## Getting started
### Mostly automatic install (RN >= 0.60 )
1.  yarn add react-native-smartconfig-iot
2.  yarn android or yarn ios

### Manually install
please refer to [this part](https://github.com/tuanpmt/react-native-smartconfig#manual-install)

### Test this repo locally
```bash
cd example
yarn && yarn android

// if you want to update this repo and retest, execute the following command after every change
rm -rf node_modules\react-native-smartconfig-iot && yarn --force && yarn android
```

## Usage

```javascript
import Smartconfig from 'react-native-smartconfig-iot';

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
