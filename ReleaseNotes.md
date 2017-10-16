# MasterJava Release Notes

### Masterjava 2
- Добавил в проект 
  - [Миграция базы через liquiBase](http://www.liquibase.org/quickstart.html)
    - Реализация модели/DAO/JUnit
    - Миграция DB
  - Maven плагины copy-rename-maven-plugin, maven-antrun-plugin, liquibase-maven-plugin
  - Авторизация в контейнере Tomcat
  - Concurrent and distributed applications toolkit AKKA (отсылка почты через AKKA Actors)
  - Асинхронные сервлеты 3.0
  - [Выбор языка программирования](https://drive.google.com/file/d/0B9Ye2auQ_NsFZUVNakNxeUtGeFE)

- Pефакторинг
  - Реализация модуля export: Thymeleaf и Upload
    - Закэшировал `TemplateEngine`
    - Загрузка файлов через API Servlet 3.0
  - Сохранение в базу в batch-моде с обработкой конфликтов
    - При обновлении пользователей теперь используем `INSERT ON CONFLICT`
  - Работа со StAX (новый метод `startElement`)
