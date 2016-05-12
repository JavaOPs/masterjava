####  Написание с нуля полнофункционального многомодульного Maven проекта: 
веб приложения (Tomcat, JSP, jQuery), 
многопоточного почтового сервиса (JavaMail, java.util.concurrent.*) и вспомогательных модулей связанных по Веб и REST сервисам (SOAP, JAX-WS, Axis, JAX-RS)
c сохранением данных в RMDBS и динамическим конфигурирование модулей по JMX.

## Сервис-ориентированная архитектура, Микросервисы
- JMS, альтернативы
- Варианты разворачивания сервисов. Работа с базой. Связывание сервисов.

## Maven. Многомодульный Maven проект
- Build Lifecycle
- Dependency Mechanism
- Зависимости, профили, написание плагина
- The Reactor. Snapshots

## Создание/тестирование веб-приложения. 
- Сборка, запуск, локальный и удаленный debug проекта, способы деплоя в Tomcat
- tomcat7-maven-plugin

### Веб-сервисы
- Веб-сервисы. SOAP. Преимущества/недостатки веб-сервисов. Расширения.
- Реализация веб-сервисов в Java. JAX-RPC, JAX-WS, CFX, Axis. Стили WSDL
- Создание API и реализации веб-сервиса MailService.
- Деплой и тестирование через SoapUI.

## Доработка веб-сервиса. Кастомизация WSDL.
- Работа с JAXB.
- Передача по SOAP Exception
- Включение wsdl в сервис для публикации.
- Генерация java кода по WSDL

## Реализация клиент веб-сервиса.
- Публикация веб сервиса из main(). Дабавление wsdl
- Выделение из wsdl общей части
- Создание клиента почтового сервиса.
- Тестирование с помощью JUnit 4
- Интеграционное тестирование, maven-failsafe-plugin

## JAX-WS Handlers
- Logical/protocol handlers.
- Логирование SOAP на стороне клиента.
- Логирование и статистика трафика опубликованного веб-сервиса.
- wsimport binding.
- SoapHandler аутентификация.
Добавляем файлы вложения. Mail-Service.

## Создаем вложения почты
- Генерация обновленного WSDL через wsgen
- Веб-сервисы: JAX-WS attachment with MTOM
- Тестирование вложений через SoapUi.

## Загрузка файлов.
- Стандарт MIME. Обрабатываем вложения на форме: commons-fileupload
- Загрузка файла вместе в полями формы.
- Вызов клиента с вложениями.

## Персистентность.
- NoSQL or RDBMS. Обзор NoSQL систем. CAP
- Обзор Java persistence solution: commons-dbutils, Spring JdbcTemplate, MyBatis, JOOQ, ORM (Hibernate, TopLink, ElipseLink, EBean used in Playframework). JPA. JPA Performance Benchmark
- Работа с базой: создание базы, настройка IDEA Database.
- Работа с DB через DataSource, настройка tomcat. HikariCP
- Настройка работы с DataSource из JUnit.

## REST веб сервис.
- JAX-RS. Интеграция с Jersey
- Поддержка Json. Jackson

## Асинхронность.
- @OneWay vs Java Execution framework
- Добавление в клиенте асинхронных вызовов.
- Асинхронные сервлеты 3.x в Tomcat

## Динамическое конфигурирование. JMX
- Maven Groovy cкрптинг.  groovy-maven-plugin
- Настройка Tomcat на удаленное администрирование по JMX

## Отправка email в многопоточном приложении
- Initialization on demand holder / Double-checked locking
- java.util.concurrent.*: Executors , Synchronizers, Concurrent Collections, Lock

## Проблема MemoryLeak. Поиск утечки памяти.
