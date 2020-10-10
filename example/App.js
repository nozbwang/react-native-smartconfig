import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, DeviceEventEmitter,Button} from 'react-native';
import Smartconfig from 'react-native-smartconfig-esp';

const eventHandler = (message) => {
    console.log(new Date().toTimeString() + ':' + JSON.stringify(message));
}

export default class App extends Component {

  componentDidMount(){
    DeviceEventEmitter.removeListener('uploadservice', eventHandler);
    DeviceEventEmitter.addListener('uploadservice', eventHandler);
  }

  onClick(){
    console.log("onClick")
    Smartconfig.start({
      type: 'esptouch', //or airkiss, now doesn't not effect
      ssid: "biubiu",
      bssid: '', //"" if not need to filter (don't use null)
      password: "b36cb28f0aa0181d|bobomeilin123",
      timeout: 50000 //now doesn't not effect
    }).then(results => {
      alert(JSON.stringify(results))
    }).catch(error => {
      console.log(error)
    });
  }

    render() {
        return (
            <View style={{padding:10}}>
                <View>
                    <Text >ReactNative CardView for iOS and Android</Text>
                </View>
                <View style={{marginTop:200}}>
                    <Button title="start smartconfig" onPress={()=>this.onClick()}></Button>
                </View>
            </View>
        );
    }
}
