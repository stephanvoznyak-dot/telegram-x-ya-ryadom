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

Объявление хранится как **supergroup-канал** с `ChatLocation` и описанием в формате:

```
#ЯРядом
YR1|title=...|price=...|rooms=...|area=...|address=...|lat=...|lng=...|desc=...
```

Название канала: `YR · {title}`

## Основные компоненты

| Файл | Назначение |
|------|------------|
| `YaRyadomController.java` | Главный экран поиска и списка |
| `YaRyadomCreateController.java` | Форма размещения объявления |
| `YaRyadomFavoritesController.java` | Экран избранного |
| `YaRyadomFormat.java` | Парсер и билдер формата YR1 |
| `YaRyadomGeo.java` | TDLib nearby, создание канала, кэш, избранное |

## Статус

**Рабочий MVP** (октябрь 2026).

Реализовано:
- Пункт «Я рядом» в боковом меню
- Поиск каналов с геолокацией + кэш 90 с
- Фильтр «только YR»
- Форма создания объявления с fallback без geo
- Избранное через сообщение в Saved Messages (debounce)
- Кнопка «Написать» автору

Известные ограничения:
- Каждое объявление = отдельный канал (ограничение текущей модели)
- Nearby зависит от политики TDLib и радиуса сервера Telegram
- UI пока на стандартных Android View (планируется переход на компоненты TGX)

## Сборка

Требования совпадают с оригинальным Telegram X:

- JDK 17+ (рекомендуется 21/25)
- Android SDK + NDK
- Клонирование с `--recursive`

```bash
git clone --recursive https://github.com/stephanvoznyak-dot/telegram-x-ya-ryadom.git
cd telegram-x-ya-ryadom
# Настроить local.properties (sdk.dir, telegram.api_id, telegram.api_hash)
./gradlew assembleLatestUniversalDebug
```

Подробные инструкции по сборке — в оригинальном README Telegram X.

## Документация

- [Интеграция «Я рядом»](docs/YA_RYADOM_INTEGRATION.md)
- [Патч бокового меню](docs/PATCH_YARYADOM_DRAWER.md)

## Лицензия

Код Telegram X распространяется под **GNU GPL v3.0**.  
Все изменения, связанные с «Я рядом», также подчиняются этой лицензии.

## Связанные репозитории

- Исходный форк с историей разработки: [telegram-x](https://github.com/stephanvoznyak-dot/telegram-x)
- Официальный upstream: [TGX-Android/Telegram-X](https://github.com/TGX-Android/Telegram-X)
