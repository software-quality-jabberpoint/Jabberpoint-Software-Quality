# SOLID Evidence (JabberPoint)

This document maps where SOLID principles are applied in the current codebase.

## Single Responsibility Principle (SRP)
- `Presentation` handles presentation state and observer notifications.
  - Methods: `nextSlide()`, `prevSlide()`, `append(Slide)`, `addObserver(...)`, `notifyObservers(...)`
  - File: `src/Presentation.java`
- `XMLAccessor` handles XML persistence concerns only.
  - Methods: `loadFile(...)`, `saveFile(...)`, `loadSlideItem(...)`
  - File: `src/XMLAccessor.java`
- Each command class has one job (single action dispatch).
  - Methods: `execute()` in `NextSlideCommand`, `PreviousSlideCommand`, `OpenPresentationCommand`, `SavePresentationCommand`, `ExitCommand`
  - Files: `src/*Command.java`

## Open-Closed Principle (OCP)
- New actions can be added by creating another class that implements `Command` without changing existing command callers.
  - Interface: `src/Command.java`
  - Usage points: `src/MenuController.java`, `src/KeyController.java`
- New slide item types can be supported by extending `SlideItemFactory.createSlideItem(...)` while callers remain unchanged.
  - Factory: `src/SlideItemFactory.java`
  - Caller: `src/XMLAccessor.java` (`slideItemFactory.createSlideItem(...)`)

## Liskov Substitution Principle (LSP)
- `TextItem` and `BitmapItem` can be used wherever `SlideItem` is expected.
  - Base type use: `Vector<SlideItem>` and `append(SlideItem)` in `src/Slide.java`
  - Implementations: `src/TextItem.java`, `src/BitmapItem.java`
- `DemoPresentation` and `XMLAccessor` substitute for `Accessor` in `JabberPoint`.
  - Abstraction: `src/Accessor.java`
  - Implementations: `src/DemoPresentation.java`, `src/XMLAccessor.java`
  - Usage: `Accessor.getDemoAccessor().loadFile(...)` in `src/JabberPoint.java`

## Interface Segregation Principle (ISP)
- `Command` is a small focused interface (`execute()`).
  - Interface: `src/Command.java`
- `PresentationObserver` is a small focused interface (`update(Presentation)`).
  - Interface: `src/PresentationObserver.java`
  - Consumer: `src/SlideViewerComponent.java`

## Dependency Inversion Principle (DIP)
- High-level UI code delegates behavior to command objects instead of embedding use-case logic.
  - `MenuController` calls `new OpenPresentationCommand(...).execute()`, `new SavePresentationCommand(...).execute()`, etc.
  - `KeyController` calls `new NextSlideCommand(...).execute()`, `new PreviousSlideCommand(...).execute()`, `new ExitCommand(...).execute()`
  - Files: `src/MenuController.java`, `src/KeyController.java`
- Exit logic is inverted through the command and an abstracted method call (`JabberPoint.exit()`).
  - `ExitCommand.execute()` -> `jabberPoint.exit()`
  - Files: `src/ExitCommand.java`, `src/JabberPoint.java`
