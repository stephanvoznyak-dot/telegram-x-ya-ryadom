# Telegram X + «Я рядом»

> Модуль сервиса аренды жилья **«Я рядом»** для клиента Telegram X.

**Репозиторий:** https://github.com/stephanvoznyak-dot/telegram-x-ya-ryadom  
**Полный рабочий клиент (с историей):** https://github.com/stephanvoznyak-dot/telegram-x

## Модуль «Я рядом» — статус: полный

| Файл | Назначение |
|------|------------|
| `YaRyadomController.java` | Главный экран: поиск, фильтр YR, избранное, размещение |
| `YaRyadomCreateController.java` | Форма публикации объявления |
| `YaRyadomGeo.java` | TDLib SearchChatsNearby, create channel, кэш, избранное |
| `YaRyadomFormat.java` | Парсер/билдер формата YR1 |
| `YaRyadomFavorites.java` | In-memory хранилище избранного |
| `YaRyadomFavoritesController.java` | Экран списка избранного |
| `ids_yaryadom.xml` | `btn_yaryadom`, `controller_yaryadom` |

## Формат YR1

```
#ЯРядом
YR1|title=...|price=...|rooms=...|area=...|address=...|lat=...|lng=...|desc=...
```

Название канала: `YR · {title}`

## Документация

- [Интеграция](docs/YA_RYADOM_INTEGRATION.md)
- [Патч Drawer](docs/PATCH_YARYADOM_DRAWER.md)
- [Скрипт патча](docs/RESTORE_AND_PATCH.sh)

## Важно

Этот репозиторий содержит **модуль «Я рядом»** и документацию.

Полный исходный код Telegram X (база + submodules + история) находится в:

**https://github.com/stephanvoznyak-dot/telegram-x**

Для сборки APK клонируйте полный форк и примените модуль / используйте его напрямую.

## Лицензия

GNU GPL v3.0 (как у Telegram X).
