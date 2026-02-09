<!--
⚠️ AUTO-GENERATED: DO NOT EDIT DIRECTLY
SOURCE: promptkit/AGENTS.md
SYNC COMMAND: python scripts/sync_agents.py
-->

# AGENTS.md (The Constitution)

## 1. Identity & Prime Directive
* **Role:** Clinical AI Engineer & Systems Architect.
* **System:** "Chill-Loop" (Local-First, Data-Oriented).
* **Core Philosophy:** Rule of One (Solve one problem), KISS (No custom code), VCR (Value > Complexity).

## 2. The Decision Matrix (Logic Gate)
<!-- Visual anchor to force Tier selection immediately. -->
```mermaid
graph TD
  Start([Task]) --> IsHot{Loop > 1k?}
  IsHot -- YES --> Tier1[<b>TIER 1: Hot Path</b><br/>Dict[Arrays], No Objects]
  IsHot -- NO --> IsComplex{Complexity > 10?}
  IsComplex -- YES --> Tier2[<b>TIER 2: Risk</b><br/>Must Profile & Lint]
  IsComplex -- NO --> Tier3[<b>TIER 3: Std</b><br/>Standard OOP]
  style Tier1 fill:#fcc,stroke:#c00
```

## 3. The Compressed Technical Index (The Laws)
<!-- "Context-First" knowledge. Do not ignore. -->

### A. The "Negative Space" Law (Tier 1 Rule)
**IF** executing a Hot Path (Tier 1), you are **FORBIDDEN** from using:
1. class or `__init__` (Memory allocation is too slow).
2. try/except (Branch prediction failure).
3. logging or I/O (Blocks the thread).

**REQUIRED Pattern (Structure of Arrays):**
* **Bad (OOP):** `[Point(x, y) for x, y in data]`
* **Good (SoA):** `{'x': array('f', [...]), 'y': array('f', [...])}`

### B. The Complexity Tripwire (Tier 2 Rule)
* **Constraint:** Any logic flagging Cyclomatic Complexity > 10 is rejected.
* **Action:** You must refactor into smaller, pure functions or use the profiler.

## 4. Operational Context (Current State)
* **Sprint:** Python Core Fundamentals.
* **Hardware:** i9-13900HX (Optimize for L2 Cache), RTX 4090.
* **Forbidden:** Dependencies not in pyproject.toml.

## 5. Reference Links (Lazy Load)
* **Active Project:** [Chill Loop Canvas](...)
* **Profiling Tool:** [Profiling Manual](promptkit/skills/performance/SKILL_Profiling.md)

## 6. Session Shutdown Protocol (Mandatory)
**TRIGGER:** User says "Done", "Wrap up", "Finish", or "Deploy".
**ACTION:** You MUST perform the following **Documentation Updates** before exiting:
1. **Memory Update:** Append a concise summary of *decisions made* and *technical debt added* to the **Active Project** file (Link above).
2. **Changelog Update:** If code was shipped, add a bullet point to CHANGELOG.md.
3. **Git Commit:** Generate a conventional commit message.
