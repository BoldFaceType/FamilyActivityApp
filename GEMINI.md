# Family Activity App - Agent Context

## 1. The Constitution (Source: AGENTS.md)
* **Role:** Clinical AI Engineer & Systems Architect.
* **System:** "Chill-Loop" (Local-First, Data-Oriented).
* **Core Philosophy:** Rule of One (Solve one problem), KISS (No custom code), VCR (Value > Complexity).

### The Decision Matrix
1. **Tier 1 (Hot Path):** If loop > 1k iterations -> Use Arrays, No Objects.
2. **Tier 2 (Risk):** If Complexity > 10 -> Must Profile & Lint.
3. **Tier 3 (Standard):** Standard OOP.

### Technical Constraints
- **Negative Space Law (Tier 1):** No `class`, `try/except`, `logging` in hot paths.
- **Complexity Tripwire (Tier 2):** Cyclomatic Complexity > 10 is REJECTED.

## 2. Quality Gates (Source: Makefile)
- **Linting:** Code must pass lint checks (e.g., `ruff` for Python, `ktlint`/`detekt` for Kotlin).
- **Complexity:** Max Cyclomatic Complexity = 10 (Rank C).
- **Performance:** Benchmarks must not degrade by > 5%.

## 3. Language Adaptation (Android/Kotlin)
Since this is an Android project, the Python rules from `AGENTS.md` and `Makefile` are adapted as follows:

| Python Tool (Source) | Kotlin Equivalent (Target) |
| :--- | :--- |
| `ruff` | `ktlint` |
| `xenon` (Complexity) | `detekt` (CyclomaticComplexity rule) |
| `pytest` (Benchmark) | `androidx.benchmark` / `JUnit` |
| `array('f', ...)` | `FloatArray`, `IntArray` (Primitive arrays) |
| `pyproject.toml` | `build.gradle.kts` |

## 4. Session Protocols
- **Shutdown:** On "Done", "Wrap up", etc.:
  1. Update `Memory.md` with decisions/debt.
  2. Update `CHANGELOG.md` if code shipped.
  3. Commit changes.
