# Changelog

## [1.1.0] - 2026-02-21
### Added
- **Core Infrastructure:**
    - Set up Hilt Dependency Injection (`AppModule`, `NetworkModule`).
    - Implemented `LlmClient` strategies for Ollama (Local) and Gemini (Cloud).
    - Created `AppConfig` and `LlmProviderConfig` data classes.
- **Feature: Preferences:**
    - Built `PreferencesRepository` using Jetpack DataStore.
    - Implemented `PreferencesViewModel` and `PreferencesScreen` (Compose).
    - Added UI for toggles (bathroom access, weather) and sliders (walking distance).
- **Feature: Map Integration:**
    - Created `LocationService` wrapper for FusedLocationProvider.
    - Added `PlacesClient` for Google Places API interaction.
- **Feature: Suggestions Engine:**
    - Developed `PromptBuilder` for creating context-aware prompts.
    - Built `SuggestionsRepository` to orchestrate location, weather, and LLM calls.
    - Created `SuggestionsScreen` to display activity suggestions.
- **Feature: Polish:**
    - Added `PackingListGenerator` logic for weather/activity-specific items.
    - Implemented `EscapeHatchFab` for quick restroom navigation.
- **Documentation:**
    - Updated `TASKLIST.md` to reflect completed tasks.
    - Created `GEMINI.md` with project constitution and rules.

## [1.0.0] - 2026-02-09
- Initial project scaffold with Vertical Slice Architecture.
- LLM Orchestration layer drafted (Local/Cloud toggle).
- Firebase initialization for Hosting and Firestore.
- Integrated PromptKit standards via PR #1.
