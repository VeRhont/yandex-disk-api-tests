# Yandex Disk API Tests

Интеграционные автотесты для API Яндекс.Диска на Kotlin с использованием JUnit 5 и RestAssured

API docs: 
https://yandex.ru/dev/disk/poligon/

## Стэк
- Kotlin
- JUnit 5
- RestAssured
- AssertJ

## Покрыто
- Disk info
- Resources (files, folders)
- Upload / Download
- Trash
- Public API

## Запуск

### 1. Установка OAuth токена

##### - Linux / MacOS:
`export token=YOUR_TOKEN`

##### - Windows (Powershell):
`$env:TOKEN="YOUR_TOKEN"`

### 2. Запуск тестов
`./gradlew test`



---

