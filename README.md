# JabberPoint (Software Quality)

This repository contains a redesigned version of **JabberPoint**, a small Java slide-show application, as part of a Software Quality assignment.

The focus of the redesign is to improve maintainability, extensibility, testability, and separation of concerns.

## Packages

- `jabberpoint.core`: presentation model, slides, slide items, styles, and rendering collaborators.
- `jabberpoint.io`: presentation loading and saving.
- `jabberpoint.command`: command objects for user actions.
- `jabberpoint.ui`: Swing/AWT user interface.

## Design Patterns Used

- **Observer**
  - `Presentation` notifies registered `PresentationObserver` implementations when state changes.
- **Command**
  - User actions are encapsulated as command objects.
- **Factory**
  - Slide item creation is centralized in `SlideItemFactory`.

## Requirements

- Java **JDK 17** is recommended.
- The project does not use Maven/Gradle.

## Build and Test

```bash
curl -sSL -o junit-platform-console-standalone.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar
javac -cp junit-platform-console-standalone.jar -d out @sources.txt
java -jar junit-platform-console-standalone.jar execute --class-path out --scan-class-path
```

## Run

```bash
java -cp out jabberpoint.JabberPoint
```
Run with an XML file:

```bash
java -cp out jabberpoint.JabberPoint test.xml
```

## CI / Coverage

The GitHub Actions workflow:

- compiles `src/` and `test/`
- runs JUnit 5 tests
- generates a **JaCoCo** coverage report
- uploads artifacts (coverage report + summary)

Notes:

- Some UI-related classes are difficult to unit test headlessly. Coverage reporting may be scoped to testable logic by excluding UI-heavy compiled class files in the report step.
- If you see console output like missing image warnings during tests, it can come from slide item rendering code loading sample resources.
