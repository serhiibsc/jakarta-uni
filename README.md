+ Тема: Веб-сайт з інформацією про курси валют
+ Сутності: Валюта, дата, курс обміну
+ Актори: Адміністратор, Гість
  Сценарії використання:
  Гість:
+ Перегляд курсу обміну усіх валют на поточний день
+ Перегляд курсу певної валюти за заданий проміжок часу у вигляді таблиці
+ Адміністратор: + Створення/редагування/видалення назви валюти, Введення курсу певної валюти на заданий день

+ 2.3) щонайменше один раз має бути використаний цикл (елементи forEach);
+ 2.1) щонайменше один раз має бути використане умовне форматування (елемент JSTL if);
+ 2.2) щонайменше один раз має бути використаний елемент вибору (елемент JSTL choose/when);
+ 2.4) сайт має бути стійким до XSS-атак.

=====================================================
Сторінка список валют:
- список валют
- кнопка додати

Адмін сторінка валюти:
- редагування назви
- видалення
- Введення курсу на заданий день
========

- створення валюти
- видалення валюти
- редагування назви валюти
- створення ексченд рейту для певної валюти на заданий день
- великий список мапінгів усіх валют "на сьогодні"
- список мапінгів двох валют за заданий проміжок часу
- використовувати CMT (Container-Managed Transactions)
- JAX-RS для створення RESTful вебсервісів
- До проекту з попередньої лабораторної роботи додайте компоненти, які будуть забезпечувати доступ за до застосунку за допомогою RESTful API.


===
- має бути присутнім що найменше один ресурс, для якого реалізовані усі чотири CRUD-операції (create, read, update, delete);
- щонайменше для одного ресурсу мають бути реалізовані функції фільтрації та пагінації
- усі операції мають повертати відповідний код стану HTTP в залежності від успішності чи неуспішності операції.


- має бути присутня хоча б одна бізнес-операція, на прикладі якої можна буде продемонструвати підтвердження транзакції у випадку успіху, та відкат транзакції у випадку помилки;
