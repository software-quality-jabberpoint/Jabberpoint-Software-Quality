# JabberPoint Class Diagram

UML class diagram of the JabberPoint system, grouped by package. Renders in any
Mermaid-aware Markdown viewer (GitHub, IntelliJ, VS Code).

## Overview

The system applies several design patterns:

- **Command** (`command` package): UI controllers trigger actions via `Command` objects supplied by a `CommandFactory`.
- **Abstract Factory / DIP**: `CommandFactory` and `SlideItemFactory` decouple clients from concrete construction.
- **Observer**: `Presentation` notifies `PresentationObserver`s (the `SlideViewerComponent`) of state changes.
- **Strategy / Bridge**: `SlideItem` subclasses delegate drawing to dedicated renderer classes.
- **Template (Accessor)**: `Accessor` unifies read/write of presentations; `XMLAccessor` implements XML I/O.

## Diagram

```mermaid
classDiagram
    direction TB

    %% ===================== core =====================
    class Presentation:::observer {
        -String showTitle
        -List~Slide~ showList
        -int currentSlideNumber
        -List~PresentationObserver~ observers
        +getSize() int
        +getTitle() String
        +setTitle(String)
        +getSlideNumber() int
        +setSlideNumber(int)
        +prevSlide()
        +nextSlide()
        +clear()
        +append(Slide)
        +getSlide(int) Slide
        +getCurrentSlide() Slide
        +addObserver(PresentationObserver)
        +removeObserver(PresentationObserver)
        +notifyObservers()
    }

    class Slide:::nonpattern {
        +int WIDTH
        +int HEIGHT
        #String title
        #List~SlideItem~ items
        +append(SlideItem)
        +append(int, String)
        +getTitle() String
        +setTitle(String)
        +getSlideItem(int) SlideItem
        +getSlideItems() List~SlideItem~
        +getSize() int
        +draw(Graphics, Rectangle, ImageObserver)
        +getScale(Rectangle) float
    }

    class SlideItem:::nonpattern {
        <<abstract>>
        -int level
        +getLevel() int
        +getBoundingBox(Graphics, ImageObserver, float, Style)* Rectangle
        +draw(int, int, float, Graphics, Style, ImageObserver)*
    }

    class TextItem:::nonpattern {
        -String text
        -TextItemRenderer renderer
        +getText() String
        +getAttributedString(Style, float) AttributedString
        +getBoundingBox(...) Rectangle
        +draw(...)
    }

    class BitmapItem:::nonpattern {
        -BufferedImage bufferedImage
        -String imageName
        -BitmapItemRenderer renderer
        +getName() String
        +getBufferedImage() BufferedImage
        +getBoundingBox(...) Rectangle
        +draw(...)
    }

    class TextItemRenderer:::nonpattern {
        +getBoundingBox(TextItem, ...) Rectangle
        +draw(TextItem, ...)
    }

    class BitmapItemRenderer:::nonpattern {
        +getBoundingBox(BitmapItem, ...) Rectangle
        +draw(BitmapItem, ...)
    }

    class SlideItemFactory:::factory {
        +String TEXT
        +String IMAGE
        +createSlideItem(String, int, String) SlideItem
    }

    class Style:::nonpattern {
        -Style[] styles
        +int indent
        +Color color
        +Font font
        +int fontSize
        +int leading
        +createStyles()$
        +getStyle(int)$ Style
        +getFont(float) Font
    }

    class PresentationObserver:::observer {
        <<interface>>
        +update(Presentation)
    }

    %% ===================== command =====================
    class Command:::command {
        <<interface>>
        +execute()
    }

    class CommandFactory:::factory {
        <<interface>>
        +nextSlide() Command
        +previousSlide() Command
        +openPresentation() Command
        +savePresentation() Command
        +exit() Command
    }

    class DefaultCommandFactory:::factory {
        -Presentation presentation
        -JabberPoint jabberPoint
        -PresentationReader presentationReader
        -PresentationWriter presentationWriter
        +nextSlide() Command
        +previousSlide() Command
        +openPresentation() Command
        +savePresentation() Command
        +exit() Command
    }

    class NextSlideCommand:::command {
        -Presentation presentation
        +execute()
    }
    class PreviousSlideCommand:::command {
        -Presentation presentation
        +execute()
    }
    class OpenPresentationCommand:::command {
        -Presentation presentation
        -PresentationReader presentationReader
        +execute()
    }
    class SavePresentationCommand:::command {
        -Presentation presentation
        -PresentationWriter presentationWriter
        +execute()
    }
    class ExitCommand:::command {
        -JabberPoint jabberPoint
        +execute()
    }

    %% ===================== io =====================
    class PresentationReader:::nonpattern {
        <<interface>>
        +loadFile(Presentation, String)
    }
    class PresentationWriter:::nonpattern {
        <<interface>>
        +saveFile(Presentation, String)
    }
    class Accessor:::nonpattern {
        <<abstract>>
        +String DEMO_NAME
        +String DEFAULT_EXTENSION
        +loadFile(Presentation, String)*
        +saveFile(Presentation, String)*
    }
    class XMLAccessor:::nonpattern {
        -SlideItemFactory slideItemFactory
        +getTitle(Element, String) String
        +loadFile(Presentation, String)
        +loadSlideItem(Slide, Element)
        +saveFile(Presentation, String)
    }
    class DemoPresentation:::nonpattern {
        +loadFile(Presentation, String)
    }

    %% ===================== ui =====================
    class SlideViewerFrame:::nonpattern {
        +int WIDTH
        +int HEIGHT
        +setUpWindow(SlideViewerComponent, Presentation, CommandFactory)
    }
    class SlideViewerComponent:::observer {
        -Slide slide
        -Font labelFont
        -Presentation presentation
        -TitleView titleView
        +getPreferredSize() Dimension
        +update(Presentation)
        +paintComponent(Graphics)
    }
    class MenuController:::nonpattern {
        -Frame parent
        -Presentation presentation
        -CommandFactory commandFactory
    }
    class KeyController:::nonpattern {
        -CommandFactory commandFactory
        +keyPressed(KeyEvent)
    }
    class TitleView:::nonpattern {
        <<interface>>
        +setTitle(String)
    }
    class AboutBox:::nonpattern {
        +show(Frame)$
    }

    %% ===================== app =====================
    class JabberPoint:::nonpattern {
        +exit()
        +main(String[])$
    }

    %% ===================== relationships =====================
    SlideItem <|-- TextItem
    SlideItem <|-- BitmapItem
    TextItem --> TextItemRenderer : delegates draw
    BitmapItem --> BitmapItemRenderer : delegates draw

    Presentation "1" o-- "*" Slide : contains
    Slide "1" o-- "*" SlideItem : contains
    SlideItemFactory ..> TextItem : creates
    SlideItemFactory ..> BitmapItem : creates
    Slide ..> Style : uses
    Slide ..> TextItem : title item

    Presentation o-- "*" PresentationObserver : notifies
    SlideViewerComponent ..|> PresentationObserver
    SlideViewerComponent --> Presentation : observes
    SlideViewerComponent --> TitleView : updates title
    SlideViewerComponent ..> Slide : draws

    Command <|.. NextSlideCommand
    Command <|.. PreviousSlideCommand
    Command <|.. OpenPresentationCommand
    Command <|.. SavePresentationCommand
    Command <|.. ExitCommand

    CommandFactory <|.. DefaultCommandFactory
    DefaultCommandFactory ..> XMLAccessor : creates (default ctor)
    DefaultCommandFactory ..> NextSlideCommand : creates
    DefaultCommandFactory ..> PreviousSlideCommand : creates
    DefaultCommandFactory ..> OpenPresentationCommand : creates
    DefaultCommandFactory ..> SavePresentationCommand : creates
    DefaultCommandFactory ..> ExitCommand : creates
    DefaultCommandFactory --> Presentation
    DefaultCommandFactory --> JabberPoint
    DefaultCommandFactory --> PresentationReader
    DefaultCommandFactory --> PresentationWriter

    NextSlideCommand --> Presentation
    PreviousSlideCommand --> Presentation
    OpenPresentationCommand --> Presentation
    OpenPresentationCommand --> PresentationReader
    SavePresentationCommand --> Presentation
    SavePresentationCommand --> PresentationWriter
    ExitCommand --> JabberPoint

    Accessor ..|> PresentationReader
    Accessor ..|> PresentationWriter
    Accessor <|-- XMLAccessor
    DemoPresentation ..|> PresentationReader
    XMLAccessor --> SlideItemFactory
    XMLAccessor ..> Slide : builds
    XMLAccessor ..> TextItem : reads/writes
    XMLAccessor ..> BitmapItem : reads/writes

    KeyController --> CommandFactory
    KeyController ..> DefaultCommandFactory : creates (convenience ctor)
    MenuController --> CommandFactory
    MenuController --> Presentation
    MenuController ..> AboutBox
    MenuController ..> DefaultCommandFactory : creates (convenience ctor)
    SlideViewerFrame ..> SlideViewerComponent : creates
    SlideViewerFrame ..> DefaultCommandFactory : creates
    SlideViewerFrame ..> KeyController : creates
    SlideViewerFrame ..> MenuController : creates

    JabberPoint ..> Presentation : creates
    JabberPoint ..> SlideViewerFrame : creates
    JabberPoint ..> DemoPresentation : creates
    JabberPoint ..> XMLAccessor : creates
    JabberPoint ..> Style : initialises

    %% ===================== design pattern legend =====================
    %% Blue = Observer | Green = Command | Purple = Factory Method | Yellow = Non-Pattern
    classDef observer fill:#90caf9,stroke:#1565c0,color:#000;
    classDef command fill:#a5d6a7,stroke:#2e7d32,color:#000;
    classDef factory fill:#ce93d8,stroke:#6a1b9a,color:#000;
    classDef nonpattern fill:#fff59d,stroke:#f9a825,color:#000;
```

## Design pattern legend

| Color | Pattern | Classes |
|-------|---------|---------|
| Blue | Observer | `Presentation` (subject), `PresentationObserver`, `SlideViewerComponent` |
| Green | Command | `Command`, `NextSlideCommand`, `PreviousSlideCommand`, `OpenPresentationCommand`, `SavePresentationCommand`, `ExitCommand` |
| Purple | Factory Method | `CommandFactory`, `DefaultCommandFactory`, `SlideItemFactory` |
| Yellow | Non-Pattern | All remaining domain, I/O, UI and rendering classes |

## Package legend

- **`jabberpoint`**: `JabberPoint` (entry point).
- **`jabberpoint.core`**: domain model + rendering (`Presentation`, `Slide`, `SlideItem` and subclasses, renderers, `Style`, `SlideItemFactory`, `PresentationObserver`).
- **`jabberpoint.command`**: `Command`, `CommandFactory`, `DefaultCommandFactory`, and concrete commands.
- **`jabberpoint.io`**: `PresentationReader`/`PresentationWriter` interfaces, `Accessor`, `XMLAccessor`, `DemoPresentation`.
- **`jabberpoint.ui`**: Swing frame/component and controllers (`SlideViewerFrame`, `SlideViewerComponent`, `MenuController`, `KeyController`, `TitleView`, `AboutBox`).
