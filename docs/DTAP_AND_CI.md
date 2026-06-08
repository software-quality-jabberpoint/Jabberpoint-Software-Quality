# DTAP and CI Evidence

The pipeline is split across two GitHub Actions workflows:
- `.github/workflows/ci.yml` — Development, Testing, Production stages
- `.github/workflows/acceptance.yml` — Acceptance stage (separate OS)

Each stage maps to an explicit, named job so the DTAP structure is directly visible
in the GitHub Actions UI.

## Development (D)
- **Branch:** `develop` (day-to-day integration); feature branches per change.
- **CI job:** `DTAP D - Development (compile)` in `ci.yml`.
- Fast compile-only build of source and tests; fails immediately on broken code.
- Runs on every push and PR to `main`, `test`, `develop`.

## Testing (T)
- **Branch:** `test`
- **CI job:** `DTAP T - Testing (unit tests + quality gate)` in `ci.yml`; only starts after Development passes (`needs: development`).
- Unit test execution via JUnit 5 (41 tests across 12 test classes).
- Coverage measured by JaCoCo on **application classes only** (not test sources).
  - GUI bootstrap classes that require a live display (`JabberPoint`, `AboutBox`, `MenuController`, `SlideViewerFrame`) are excluded; all other classes including `KeyController` and `SlideViewerComponent` are measured.
  - Achieved line coverage: ~89% (gate: ≥ 75%).
- Coverage report exported as HTML/XML/CSV artifacts.
- Static quality report (`javac -Xlint`) exported as `lint-report.txt`.

## Acceptance (A)
- **Branch:** `acceptance`
- **CI job:** `DTAP A - Acceptance Simulation (Windows)` in `acceptance.yml`.
- Runs on `windows-latest` — a different OS than the Testing stage (Linux) — to catch platform-specific issues.
- Compiles source and tests, runs the full JUnit 5 suite, builds a runnable JAR.
- Manual GUI acceptance check was also performed after the refactor:
  - Demo presentation loads.
  - XML presentation loads from `test.xml`.
  - Enter and arrow keys navigate slides.
  - `q` exits the application.
  - Menus were checked and worked.

## Production (P)
- **Branch:** `main`
- **CI job:** `DTAP P - Production (release artifact)` in `ci.yml`; only starts after Testing passes (`needs: testing`) and only on a push to `main`.
- Compiles application sources and packages a release JAR.
- Full deployment to a live environment is outside current project scope.

## CI Artifacts
| Artifact | Stage | Contents |
|---|---|---|
| `ci-reports-linux` | Testing | coverage HTML/XML/CSV, coverage-summary.txt, lint-report.txt, JabberPoint.jar |
| `acceptance-windows-artifacts` | Acceptance | JabberPoint.jar (Windows build) |
| `production-release-artifact` | Production | JabberPoint.jar (release build) |
