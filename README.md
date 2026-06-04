# Jabberpoint

## Packages

- `jabberpoint.core`: presentation model, slides, slide items, styles, and rendering collaborators.
- `jabberpoint.io`: presentation loading and saving.
- `jabberpoint.command`: command objects for user actions.
- `jabberpoint.ui`: Swing/AWT user interface.

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
