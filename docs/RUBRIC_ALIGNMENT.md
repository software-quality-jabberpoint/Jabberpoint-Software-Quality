# Rubric Alignment Checklist

## Analyse
- Analysis is documented with three individual UML diagrams:
- Use case diagram
- Class diagram
- Activity diagram
- Diagrams are submitted as course deliverables outside this GitHub repository.

## Advise
- Advice report is provided as a course deliverable outside this GitHub repository.

## Designs
- Redesign document is provided as a course deliverable outside this GitHub repository.
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
