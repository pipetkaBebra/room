package com.example.demo.service;

import com.example.demo.entity.TablePerson;
import com.example.demo.repository.TablePersonRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MqttService {
    private static final String BROKER_URL = "tcp://147.232.205.176:1883";
    private static final String CLIENT_ID = "DemoApplicationClient";
    private static final String TOPIC_RFID = "kpi/endor/rfid/yi065yw";
    private static final String TOPIC_BME680 = "kpi/endor/bme680/data";

    private MqttClient client;

    @Autowired
    private TablePersonService tablePersonService;  // Додано інжекцію залежності

    @Autowired
    private TablePersonRepository tablePersonRepository;

    @Autowired
    private RoomDataService roomDataService;

    public MqttService() {
        try {
            // Директорія для збереження даних клієнта
            String persistenceDirPath = "mqtt_persistence";
            File persistenceDir = new File(persistenceDirPath);

            if (!persistenceDir.exists()) {
                persistenceDir.mkdirs();
            }

            // Ініціалізація MQTT клієнта
            client = new MqttClient(BROKER_URL, CLIENT_ID, new MqttDefaultFilePersistence(persistenceDirPath));
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName("maker");
            options.setPassword("mother.mqtt.password".toCharArray());
            options.setCleanSession(true);

            client.connect(options);

            System.out.println("Підключено до MQTT брокера: " + BROKER_URL);

            // Підписка на тему RFID
            client.subscribe(TOPIC_RFID, (topic, message) -> handleRfidMessage(new String(message.getPayload())));

            // Підписка на тему BME680
            client.subscribe(TOPIC_BME680, (topic, message) -> handleBme680Message(new String(message.getPayload())));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void handleRfidMessage(String payload) {
        System.out.println("RFID дані: " + payload);
        try {
            // Парсимо JSON
            JSONObject json = new JSONObject(payload);
            String email = json.getString("email");
            String nameSurname = json.getString("name_surname");
            String roomNumber = json.getJSONObject("room").getString("number"); // якщо це число, замініть getString() на getInt() або getLong()
            String timestamp = json.getString("timestamp");

            // Отримуємо список всіх записів для цього користувача, відсортованих за часом (від найновішого до найстарішого)
            List<TablePerson> userRecords = tablePersonRepository.findByEmailOrderByCreatedAtDesc(email);

            // Перевірка, чи є записи в базі для цього користувача
            if (!userRecords.isEmpty()) {
                // Останній запис користувача
                TablePerson lastUserRecord = userRecords.get(0);

                // Якщо останній запис має статус isActive = true, додаємо новий запис з isActive = false
                boolean newActiveStatus = !lastUserRecord.isActive(); // Змінюємо статус на протилежний
                tablePersonService.addPerson(nameSurname, email, newActiveStatus, roomNumber);
            } else {
                // Якщо це перший запис для користувача, додаємо його зі статусом true
                tablePersonService.addPerson(nameSurname, email, true, roomNumber);
            }

        } catch (Exception e) {
            System.err.println("Помилка обробки JSON: " + e.getMessage());
        }
    }




    private void handleBme680Message(String payload) {
        System.out.println("Отримано дані з BME680: " + payload);
        try {
            JSONObject json = new JSONObject(payload);
            double temperature = json.getDouble("temperature");
            double humidity = json.getDouble("humidity");
            double pressure = json.getDouble("pressure");
            double gas = json.getDouble("gas");

            System.out.printf("Температура: %.2f°C, Вологість: %.2f%%, Тиск: %.2f hPa, Газ: %.3f\n",
                    temperature, humidity, pressure, gas);

            // Збереження даних у базу
            roomDataService.saveRoomData1(101, (float) temperature, (float) humidity, (float) gas);

        } catch (Exception e) {
            System.err.println("Помилка обробки JSON: " + e.getMessage());
        }
    }
}
