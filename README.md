# JabberPoint (Software Quality)

This repository contains a redesigned version of **JabberPoint**, a small Java slide-show application, as part of a Software Quality assignment.

The focus of the redesign is to improve maintainability and extensibility by applying common design patterns and improving separation of concerns.

## Repository structure

- **`src/`**
  - Application source code
- **`test/`**
  - Lightweight unit tests (no external test framework)
  - Entry point: `AllTests`
- **`.github/workflows/ci.yml`**
  - CI pipeline (compile, run tests, JaCoCo coverage report, artifact upload)
- **`test.xml`**
  - Sample presentation used by tests / demos

## Design patterns used

- **Observer**
  - `Presentation` notifies registered `PresentationObserver` implementations when state changes.
- **Command**
  - User actions (next/previous/open/save/exit) are encapsulated as command objects.
- **Factory Method**
  - Centralized creation of slide items via `SlideItemFactory`.

## Requirements

- Java **JDK 17** is recommended (matches CI).
- The project does not use Maven/Gradle.

## Build (command line)

From the repository root:

```bash
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

## Run the application (command line)

```bash
java -cp out JabberPoint
```

Run with an XML file:

```bash
java -cp out JabberPoint test.xml
```

## Run tests

Tests are plain Java and are executed via `AllTests`.

### Command line

```bash
mkdir -p out
find src -name "*.java" > sources.txt
find test -name "*.java" > tests.txt
javac -d out @sources.txt @tests.txt
java -cp out AllTests
```

### IntelliJ IDEA

- Mark `src/` as **Sources Root** and `test/` as **Test Sources Root**.
- Run `AllTests.main()`.
- To view coverage locally, run **with coverage**.

## CI / Coverage

The GitHub Actions workflow:

- compiles `src/` and `test/`
- runs `AllTests`
- generates a **JaCoCo** coverage report
- uploads artifacts (coverage report + summary)

Notes:

- Some UI-related classes are difficult to unit test headlessly. Coverage reporting may be scoped to testable logic by excluding UI-heavy compiled class files in the report step.

## Notes

- If you see console output like missing image warnings during tests, it can come from slide item rendering code loading sample resources.
