# Логи и метрики (ДЗ)

## Что сделано

- Добавлены осознанные логи ключевых бизнес-событий:
  - создание комнаты;
  - изменение температуры батареи;
  - отправка/ошибка отправки Kafka-события;
  - получение Kafka-сообщений.
- Подключен Prometheus endpoint через Spring Boot Actuator.
- Добавлены пользовательские метрики:
  - `app_rooms_created_total`
  - `app_battery_temperature_changed_total`
  - `app_kafka_messages_produced_total`
  - `app_kafka_messages_produce_errors_total`
  - `app_kafka_messages_consumed_total{topic="..."}`

## Как посмотреть результат

1. Запустить сервис (локально или через Docker).
2. Проверить, что приложение поднялось на `http://localhost:8080`.
3. Открыть метрики Prometheus:
   - `http://localhost:8080/actuator/prometheus`
4. Найти метрики по префиксу `app_`.
5. Сгенерировать события API:
   - создать комнату: `POST /api/rooms?name=Кухня`
   - изменить температуру: `PUT /api/devices/battery/temperature?roomId=1&value=25`
6. Повторно открыть `actuator/prometheus` и убедиться, что счетчики увеличились.

## Дополнительные эндпоинты Actuator

- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/metrics`
