# Telegram X + «Я рядом»

> Полноценный Android-клиент Telegram на базе [Telegram X](https://github.com/TGX-Android/Telegram-X)  
> с встроенным сервисом поиска и размещения объявлений об аренде жилья **«Я рядом»**.

## Цель проекта

Сохранить всю функциональность оригинального Telegram X и добавить нативный раздел «Я рядом»:

- Поиск объявлений рядом с указанной точкой (`SearchChatsNearby`)
- Фильтр только объявлений «Я рядом»
- Размещение нового объявления (создание канала с геолокацией)
- Избранное
- Быстрый переход в чат с автором объявления

## Модель данных (YR1)

```
#ЯРядом
YR1|title=...|price=...|rooms=...|area=...|address=...|lat=...|lng=...|desc=...
```

Название канала: `YR · {title}`

## Что уже есть в этом репозитории

| Путь | Статус |
|------|--------|
| `app/.../YaRyadomFormat.java` | ✅ |
| `app/.../YaRyadomFavorites.java` | ✅ |
| `app/.../ids_yaryadom.xml` | ✅ |
| `docs/YA_RYADOM_INTEGRATION.md` | ✅ |
| `docs/PATCH_YARYADOM_DRAWER.md` | ✅ |
| `docs/RESTORE_AND_PATCH.sh` | ✅ |
| `YaRyadomController.java` | ⏳ в исходном форке |
| `YaRyadomCreateController.java` | ⏳ в исходном форке |
| `YaRyadomGeo.java` | ⏳ в исходном форке |
| Полный исходный код Telegram X | ⏳ в исходном форке |

## Полный перенос кода (выполнить локально)

Исходный рабочий код с историей находится в  
**https://github.com/stephanvoznyak-dot/telegram-x**

Чтобы сделать этот репозиторий полной копией:

```bash
git clone --recursive https://github.com/stephanvoznyak-dot/telegram-x.git tgx-source
cd tgx-source
git remote add clean https://github.com/stephanvoznyak-dot/telegram-x-ya-ryadom.git
git push clean main --force
```

После этого `telegram-x-ya-ryadom` станет полноценным репозиторием приложения.

## Сборка (после полного переноса)

```bash
git clone --recursive https://github.com/stephanvoznyak-dot/telegram-x-ya-ryadom.git
cd telegram-x-ya-ryadom
# Настроить local.properties (sdk.dir, telegram.api_id, telegram.api_hash)
./gradlew assembleLatestUniversalDebug
```

## Документация

- [Интеграция «Я рядом»](docs/YA_RYADOM_INTEGRATION.md)
- [Патч бокового меню](docs/PATCH_YARYADOM_DRAWER.md)
- [Инструкция по переносу](docs/HOW_TO_IMPORT_CODE.md)

## Лицензия

GNU GPL v3.0 (как у Telegram X).

## Связанные репозитории

- Рабочий форк с полной историей: [telegram-x](https://github.com/stephanvoznyak-dot/telegram-x)
- Upstream: [TGX-Android/Telegram-X](https://github.com/TGX-Android/Telegram-X)
