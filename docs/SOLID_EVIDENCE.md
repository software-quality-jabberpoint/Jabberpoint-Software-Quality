# SOLID Evidence (JabberPoint)

This document maps where SOLID principles are applied in the current codebase.

## Single Responsibility Principle (SRP)
- `Presentation` handles presentation state and observer notifications.
  - Methods: `nextSlide()`, `prevSlide()`, `append(Slide)`, `addObserver(...)`, `notifyObservers(...)`
  - File: `src/jabberpoint/core/Presentation.java`
- `XMLAccessor` handles XML persistence concerns only.
  - Methods: `loadFile(...)`, `saveFile(...)`, `loadSlideItem(...)`
  - File: `src/jabberpoint/io/XMLAccessor.java`
- Each command class has one job (single action dispatch).
  - Methods: `execute()` in `NextSlideCommand`, `PreviousSlideCommand`, `OpenPresentationCommand`, `SavePresentationCommand`, `ExitCommand`
  - Files: `src/jabberpoint/command/*Command.java`
- Rendering calculations are separated from slide item data.
  - Data classes: `src/jabberpoint/core/TextItem.java`, `src/jabberpoint/core/BitmapItem.java`
  - Rendering collaborators: `src/jabberpoint/core/TextItemRenderer.java`, `src/jabberpoint/core/BitmapItemRenderer.java`
- Package organization separates core model, persistence, commands, and UI responsibilities.
  - `src/jabberpoint/core`
  - `src/jabberpoint/io`
  - `src/jabberpoint/command`
  - `src/jabberpoint/ui`

## Open-Closed Principle (OCP)
- New actions can be added by creating another class that implements `Command` without changing existing command callers.
  - Interface: `src/jabberpoint/command/Command.java`
  - Usage points: `src/jabberpoint/ui/MenuController.java`, `src/jabberpoint/ui/KeyController.java`
- New slide item types can be supported by extending `SlideItemFactory.createSlideItem(...)` while callers remain unchanged.
  - Factory: `src/jabberpoint/core/SlideItemFactory.java`
  - Caller: `src/jabberpoint/io/XMLAccessor.java` (`slideItemFactory.createSlideItem(...)`)

## Liskov Substitution Principle (LSP)
- `TextItem` and `BitmapItem` can be used wherever `SlideItem` is expected.
  - Base type use: `List<SlideItem>` and `append(SlideItem)` in `src/jabberpoint/core/Slide.java`
  - Implementations: `src/jabberpoint/core/TextItem.java`, `src/jabberpoint/core/BitmapItem.java`
- `XMLAccessor` substitutes for `Accessor` because it supports both loading and saving.
  - Abstraction: `src/jabberpoint/io/Accessor.java`
  - Implementation: `src/jabberpoint/io/XMLAccessor.java`
- `DemoPresentation` no longer extends `Accessor`, because it only supports loading a built-in demo and cannot correctly save.
  - Read-only abstraction: `src/jabberpoint/io/PresentationReader.java`
  - Implementation: `src/jabberpoint/io/DemoPresentation.java`
  - Usage: `new DemoPresentation().loadFile(...)` in `src/jabberpoint/JabberPoint.java`

## Interface Segregation Principle (ISP)
- `Command` is a small focused interface (`execute()`).
  - Interface: `src/jabberpoint/command/Command.java`
- `PresentationObserver` is a small focused interface (`update(Presentation)`).
  - Interface: `src/jabberpoint/core/PresentationObserver.java`
  - Consumer: `src/jabberpoint/ui/SlideViewerComponent.java`
- `TitleView` is a single-method interface so `SlideViewerComponent` only depends on the ability to set a title, not on the whole `JFrame` API.
  - Interface: `src/jabberpoint/ui/TitleView.java`
  - Consumer: `src/jabberpoint/ui/SlideViewerComponent.java`
  - Provider: `src/jabberpoint/ui/SlideViewerFrame.java` (passes `this::setTitle`)
- Presentation persistence is split into small read and write interfaces.
  - Reader: `src/jabberpoint/io/PresentationReader.java`
  - Writer: `src/jabberpoint/io/PresentationWriter.java`
  - `DemoPresentation` implements only `PresentationReader`; `XMLAccessor` implements both through `Accessor`.

## Dependency Inversion Principle (DIP)
- High-level UI code delegates behavior to command objects obtained from `CommandFactory`, instead of embedding use-case logic or constructing commands directly.
  - `MenuController` calls `commandFactory.openPresentation().execute()`, `commandFactory.savePresentation().execute()`, etc.
  - `KeyController` calls `commandFactory.nextSlide().execute()`, `commandFactory.previousSlide().execute()`, `commandFactory.exit().execute()`
  - Files: `src/jabberpoint/ui/MenuController.java`, `src/jabberpoint/ui/KeyController.java`
- Exit logic is inverted through the command and an abstracted method call (`JabberPoint.exit()`).
  - `ExitCommand.execute()` -> `jabberPoint.exit()`
  - Files: `src/jabberpoint/command/ExitCommand.java`, `src/jabberpoint/JabberPoint.java`
- Open/save commands depend on `PresentationReader` and `PresentationWriter` abstractions instead of constructing XML persistence directly inside the use case logic.
  - `src/jabberpoint/command/OpenPresentationCommand.java`
  - `src/jabberpoint/command/SavePresentationCommand.java`
- `XMLAccessor` receives `SlideItemFactory` through constructor injection, so XML parsing depends on a supplied creation collaborator.
  - `src/jabberpoint/io/XMLAccessor.java`
- UI controllers no longer construct concrete commands. `KeyController` and `MenuController` depend on the `CommandFactory` abstraction and receive it via their constructor; `DefaultCommandFactory` is the single composition point that wires concrete commands.
  - Abstraction: `src/jabberpoint/command/CommandFactory.java`
  - Implementation: `src/jabberpoint/command/DefaultCommandFactory.java`
  - Consumers: `src/jabberpoint/ui/KeyController.java`, `src/jabberpoint/ui/MenuController.java`
- `SlideViewerComponent` depends on the `TitleView` abstraction instead of a concrete `JFrame`.
  - `src/jabberpoint/ui/SlideViewerComponent.java`
