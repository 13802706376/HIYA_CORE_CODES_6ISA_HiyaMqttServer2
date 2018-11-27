package com.hiya.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 
 * Title:Server Description: �����������ͻ����������⣬����ͬ�ͻ��˿��������������ͬ����
 * 
 */
public class MqttServer
{

    public static final String HOST = "tcp://192.168.9.42:1883";
    public static final String TOPIC = "test-topic";
    private static final String clientid = "server";

    private MqttClient client;
    private MqttTopic topic;
    private String userName = "admin";
    private String passWord = "password";

    private MqttMessage message;

    public MqttServer() throws MqttException
    {
        // MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    private void connect()
    {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // ���ó�ʱʱ��
        options.setConnectionTimeout(10);
        // ���ûỰ����ʱ��
        options.setKeepAliveInterval(20);
        try
        {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException
    {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());
    }

    public static void main(String[] args) throws MqttException
    {
        MqttServer server = new MqttServer();
        server.message = new MqttMessage();
        server.message.setQos(2);
        server.message.setRetained(true);
        server.message.setPayload("������abc1".getBytes());
        server.publish(server.topic, server.message);

        System.out.println(server.message.isRetained() + "------ratained״̬");
    }
}