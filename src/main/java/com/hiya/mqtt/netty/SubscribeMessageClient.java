package com.hiya.mqtt.netty;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscribeMessageClient implements MqttCallback
{

    private MqttClient client;

    public SubscribeMessageClient()
    {
    }

    public void doDemo(String tcpUrl, String clientId, String topicName)
    {
        try
        {
            client = new MqttClient(tcpUrl, clientId);
            MqttConnectOptions mqcConf = new MqttConnectOptions();
            mqcConf.setConnectionTimeout(300);
            mqcConf.setKeepAliveInterval(1000);
            client.connect(mqcConf);
            client.setCallback(this);
            client.subscribe(topicName);
        } catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    public void connectionLost(Throwable cause)
    {
        cause.printStackTrace();
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception
    {
        System.out.println("[GOT PUBLISH MESSAGE] : " + message);
    }

    public void deliveryComplete(IMqttDeliveryToken token)
    {
    }
}