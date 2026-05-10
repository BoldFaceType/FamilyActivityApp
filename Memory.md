# Session Memory: 2026-02-09

## Project: FamilyActivityApp
**Status:** Scaffolding & Planning Complete.

### Decisions Made:
- **Architecture:** Vertical Slice Architecture (VSA). Focus on feature-based encapsulation.
- **Tech Stack:** Android (Kotlin/Compose), Google Maps API, DataStore for preferences.
- **LLM Strategy:** Dual-track orchestration.
    - Local: Ollama (Llama 3) via `LlmClient` interface.
    - Cloud: Gemini/OpenAI (Configurable in `config.json`).
- **Target Audience:** Family of 4 (2 adults, 2 kids ages 5 & 6).
- **Core Constraints:** Bathroom access, walking distance limits, weather-aware indoor/outdoor toggles.

### Accomplishments:
1. Created project root at `C:\Dev\projects\FamilyActivityApp`.
2. Scaffold directory structure according to VSA principles.
3. Created `config.json` for provider switching.
4. Implemented `LlmClient` interface and `ActivityConstraints` data model.
5. Generated a comprehensive `TASKLIST.md` with model-size suitability guide.
6. Initialized Git and pushed to `https://github.com/BoldFaceType/FamilyActivityApp`.

### Technical Debt / Notes:
- API keys in `config.json` are placeholders.
- `LocationService` and `PlacesClient` are currently skeleton files in the `TASKLIST.md` and need implementation.

---

# Session Memory: 2026-02-21

## Project: FamilyActivityApp
**Status:** Core Features Implemented (Phases 1-5).

### Decisions Made:
- **DI Framework:** Adopted Hilt (Dagger) for dependency injection.
- **LLM Client:** Implemented both `OllamaClient` (Local) and `CloudClient` (Gemini) strategies.
- **Persistence:** Used Jetpack DataStore for lightweight preference storage.
- **Location:** Wrapped `FusedLocationProvider` in a coroutine-based `LocationService`.
- **UI Architecture:** MVVM with `StateFlow` and Jetpack Compose.

### Accomplishments:
1. **Core Infrastructure:**
   - Setup Hilt DI (`AppModule`, `NetworkModule`).
   - Implemented `LlmClient` strategies and `LlmFactory`.
2. **Feature: Preferences:**
   - Created `PreferencesRepository` (DataStore).
   - Built `PreferencesViewModel` and `PreferencesScreen` (Compose).
3. **Feature: Map Integration:**
   - Implemented `LocationService` and `PlacesClient`.
4. **Feature: Suggestions Engine:**
   - Developed `PromptBuilder` for context-aware prompts.
   - Built `SuggestionsRepository` to orchestrate location, weather, and LLM calls.
   - Created `SuggestionsScreen` to display results.
5. **Feature: Polish:**
   - Added `PackingListGenerator` logic.
   - Implemented `EscapeHatchFab` for quick restroom navigation.
6. **Documentation:**
   - Updated `TASKLIST.md` to reflect completed tasks.
   - Created `GEMINI.md` with project constitution and rules.

### Technical Debt / Notes:
- **Build Files Missing:** `build.gradle.kts` files are missing dependencies (Hilt, Retrofit, Compose, etc.).
- **Manifest Missing:** `AndroidManifest.xml` needs to be updated with permissions and application class.
- **Weather Service:** Currently using hardcoded weather data in `SuggestionsViewModel`.
- **Testing:** Unit tests need to be written for the new components.
