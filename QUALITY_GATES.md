# Quality Gates

This project enforces the AGENTS.md Tier 2 Tripwire: **no function with Cyclomatic Complexity > 10**.

## Tools

| Tool | Purpose | Config |
|---|---|---|
| ktlint | Kotlin code style | Gradle plugin (see `app/build.gradle.kts`) |
| detekt | Static analysis + complexity | `detekt.yml` |
| Gradle test | Unit tests | `app/src/test/` |

## Commands

```bash
make check          # ktlint + detekt
make test           # unit tests
make quality_gate   # check + test (CI gate)
make clean          # clean build artifacts
```

## Complexity Rules (from AGENTS.md Tier 2)

- **CyclomaticComplexMethod threshold: 10** — refactor into smaller pure functions if exceeded
- **LongMethod threshold: 60 lines** — extract helper functions
- **NestedBlockDepth threshold: 4** — flatten logic

## Adding ktlint and detekt to Gradle

Add to `app/build.gradle.kts` plugins block:
```kotlin
// ktlint
id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
// detekt
id("io.gitlab.arturbosch.detekt") version "1.23.6"
```

Configure detekt in `app/build.gradle.kts`:
```kotlin
detekt {
    config.setFrom(rootProject.files("detekt.yml"))
    buildUponDefaultConfig = true
}
```
