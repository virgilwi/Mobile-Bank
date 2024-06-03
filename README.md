# Mobile Bank
Проект "Mobile Bank" - это приложение, которое предоставляет пользователям возможность управлять своими учетными записями и кошельками, а также осуществлять транзакции между кошельками.

## Оглавление
- Технологии
- Установка и запуск
- Использование
- API
- Тестирование
## Технологии
Проект использует следующие технологии:

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (в памяти для разработки и тестирования)
- Mockito (для модульного тестирования)
- JUnit 5 (для тестирования)
## Установка и запуск
### Предварительные требования
- Java 17 или новее
- Gradle
- Docker
### Шаги установки
1. Клонируйте репозиторий:

```sh
git clone https://github.com/virgilwi/mobile-bank.git
cd mobile-bank
```
2.1. Использование Docker

2.1.1. Создайте образ Docker
```sh
docker build -t mobile-bank .
```
2.1.2. Запустите Docker-контейнер
```sh
docker run -d --rm -p 8080:8080 mobile-bank
```

2.2. Соберите проект с помощью Gradle:
```sh
./gradlew build
```
2.2.1. Запустите приложение:

```sh
./gradlew bootRun
```
Приложение будет доступно по адресу http://localhost:8080.



## Использование
### Регистрация пользователя
Чтобы зарегистрировать нового пользователя, отправьте POST-запрос на /api/users с JSON-телом, содержащим email и имя пользователя.

Пример запроса:

```sh
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"email":"test@example.com", "name":"John Doe"}'
```
### Создание кошелька
Чтобы создать новый кошелек, отправьте POST-запрос на /api/wallets с JSON-телом, содержащим баланс и идентификатор пользователя.

Пример запроса:

```sh
curl -X POST http://localhost:8080/api/wallets -H "Content-Type: application/json" -d '{"balance":100.0, "user":{"id":1}}'
```
### Перевод средств
Чтобы перевести средства с одного кошелька на другой, отправьте POST-запрос на /api/transactions/transfer с параметрами fromWalletId, toWalletId и amount.

Пример запроса:

```sh
curl -X POST "http://localhost:8080/api/transactions/transfer?fromWalletId=1&toWalletId=2&amount=50.00"
```

## API
### Пользователи
- GET /api/users - Получить всех пользователей
- GET /api/users/{id} - Получить пользователя по ID
- POST /api/users - Создать нового пользователя
- PUT /api/users/{id} - Обновить данные пользователя
- DELETE /api/users/{id} - Удалить пользователя
### Кошельки
- GET /api/wallets - Получить все кошельки
- GET /api/wallets/{id} - Получить кошелек по ID
- POST /api/wallets - Создать новый кошелек
- PUT /api/wallets/{id} - Обновить кошелек
- DELETE /api/wallets/{id} - Удалить кошелек
- GET /api/wallets/{id}/balance - Получить баланс кошелька по ID
### Транзакции
- GET /api/transactions/wallet/{walletId} - Получить все транзакции по ID кошелька
- POST /api/transactions/transfer - Перевести средства между кошельками
## Тестирование
Проект включает модульные тесты, написанные с использованием JUnit 5 и Mockito.

### Запуск тестов
Чтобы запустить тесты, выполните команду:

```sh
./gradlew test
```