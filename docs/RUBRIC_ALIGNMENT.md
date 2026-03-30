# Rubric Alignment Checklist

Reference rubric: `only_inside_docs/SQ_scroingrubrics.pdf`

## Analyse
- System/class analysis documented with UML class diagram in `only_inside_docs/`.

## Advise
- Advice report provided in `only_inside_docs/JabberPoint Redesign Advice (3).docx`.

## Designs
- Redesign class diagram present in `only_inside_docs/sqfinalldesign.pdf`.
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
