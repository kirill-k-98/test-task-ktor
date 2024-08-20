
## Test task description



### CRUD API REST Dictionary<String,Int>
#### Написать сервис: HTTPS API который организует работу с объектами-счетчиками посредством следующих методов:
1. Create(String,Int) - добавляет новый счетчик, с указанным именем
2. Read(String):Int - получает значение счетчика с указанным именем
3. Delete(String) - удаляет счетчик с указанным именем
4. Increment(String):Int - увеличивает значение счетчика с указанным
именем, возвращает его новое значение.
5. GetAll - получает все счетчики (список имен и значений)
6. Написать клиента для тестирования API или добавить Swagger UI к сервису
Использовать: Kotlin, Ktor (клиент и сервер), Kotlin serialization, HTTPS, JB Exposed + любую поддерживаемую БД на ваш выбор.

### Built With
1. Run project
   ```sh
   ./gradlew build
   ./gradlew run
   ```
2. Go to swagger page(do not forgot to trust unknown https sert)
   ```sh
   https://localhost:88/swagger
   ```



