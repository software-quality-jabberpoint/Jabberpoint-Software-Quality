# Rubric Alignment Checklist

Reference rubric: `docs/SQ_scroingrubrics.pdf` (add this file to `docs/` for submission)

## Analyse
- System/class analysis documented with UML class diagram in `docs/Class_Diagram_Advice_Final_V0.2.asta`.

## Advise
- Advice report provided in `docs/JabberPoint_Redesign_Advice_Final.pdf`.

## Designs
- Redesign class diagram present in `docs/Class_Diagram_Advice_Final_V0.2.asta`.
- Design patterns reflected in implementation:
  - Observer: `Presentation` + `PresentationObserver`
  - Command: `Command` + concrete commands
  - Factory: `SlideItemFactory`

## Realise (Code)
- Source implementation in `src/`.
- Unit tests in `test/` and aggregator `test/AllTests.java`.
- CI pipeline compiles, tests, generates coverage, and exports static analysis reports.

## Programming Principles
- SOLID mapping documented in `docs/SOLID_EVIDENCE.md`.

## Manage & Control (Development Street / DTAP)
- CI lane definition in `.github/workflows/ci.yml`.
- DTAP/CI mapping documented in `docs/DTAP_AND_CI.md`.
