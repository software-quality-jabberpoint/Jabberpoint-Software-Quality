# Architecture – SlideItemFactory Refactoring

## Before

`XMLAccessor.loadSlideItem()` contained an if/else chain that directly
instantiated `TextItem` and `BitmapItem` based on a type string parsed from
XML. This meant `XMLAccessor` was responsible for both XML parsing **and**
deciding which `SlideItem` subclass to create.

## What Changed

A new class `SlideItemFactory` was introduced with a single public static
method:

```java
public static SlideItem create(String type, int level, String content)
```

The if/else creation logic was moved from `XMLAccessor` into this factory.
`XMLAccessor.loadSlideItem()` now delegates to `SlideItemFactory.create()`
instead of constructing `SlideItem` subtypes directly.

## Why This Improves Maintainability and Software Quality

- **Single Responsibility Principle** – `XMLAccessor` is now only responsible
  for XML parsing and loading. Object creation is the sole responsibility of
  `SlideItemFactory`. Each class has one reason to change.
- **Open/Closed Principle** – Adding a new `SlideItem` type (e.g. `VideoItem`)
  requires only a change in `SlideItemFactory`, not in every class that creates
  slide items.
- **Reduced duplication** – Any other code that needs to create `SlideItem`
  instances can reuse the factory instead of duplicating the type-checking
  logic.
- **Testability** – The factory can be unit-tested in isolation, independent of
  XML parsing concerns.
