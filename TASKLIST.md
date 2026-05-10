# Family Activity App - End-to-End Task Manifest

## Project Context
- **Architecture:** Vertical Slice Architecture (VSA). Code is organized by **Feature** (e.g., `features/suggestions`, `features/preferences`), not by technical layer.
- **Goal:** Android App for a family of 4 (2 adults, 2 kids ages 5 & 6).
- **Key Tech:** Kotlin, Jetpack Compose, Local/Cloud LLM Toggles, Google Maps API.

## Model Size Guide
This manifest categorizes tasks by the recommended "Model Intelligence" required.
- **[<3B] (Micro):** Boilerplate, simple data classes, strictly defined UI components.
- **[7B] (Standard):** Business logic, ViewModels, Repository implementations, unit tests.
- **[14B+] (Advanced):** Complex architectural integration, prompt engineering, generic abstractions.

---

## PHASE 1: Core Infrastructure & Configuration
*Foundational plumbing required before features can be built.*

### Task 1.1: Dependency Injection Setup (COMPLETED)
- **Description:** Set up Hilt or Koin for the project. Create the main `AppModule` to provide the `Context` and `AppConfig`.
- **VSA Context:** Place in `core/di`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Status:** Done. `AppModule`, `AppConfig`, `FamilyActivityApp` created.

### Task 1.2: LLM Client Implementation (Local - Ollama) (COMPLETED)
- **Description:** Implement the `LlmClient` interface for the local Ollama provider.
- **VSA Context:** `core/llm/OllamaClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Status:** Done. `OllamaClient` implemented using `HttpURLConnection`.

### Task 1.3: LLM Client Implementation (Cloud - Generic) (COMPLETED)
- **Description:** Implement the `LlmClient` interface for OpenAI/Gemini.
- **VSA Context:** `core/llm/CloudClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Status:** Done. `CloudClient` implemented for Gemini API.

---

## PHASE 2: Feature - Family Preferences (The "Inputs")
*The User Constraints slice. Toggles and dropdowns for the family's needs.*

### Task 2.1: Persistence Layer (DataStore) (COMPLETED)
- **Description:** Create a repository to save/load `ActivityConstraints` (Bathroom, Walking Distance, etc.) using Jetpack DataStore.
- **VSA Context:** `features/preferences/data/PreferencesRepository.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Status:** Done. `PreferencesRepository` implemented with DataStore.

### Task 2.2: Preferences ViewModel (COMPLETED)
- **Description:** Create a ViewModel that exposes the current constraints and methods to update them.
- **VSA Context:** `features/preferences/PreferencesViewModel.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Status:** Done. `PreferencesViewModel` connects Repo to UI state.

### Task 2.3: Preferences UI (Compose) (COMPLETED)
- **Description:** Build the Screen with switches, sliders, and dropdowns.
- **VSA Context:** `features/preferences/ui/PreferencesScreen.kt`.
- **Difficulty:** Medium (UI boilerplate is high volume)
- **Rec. Model:** **[7B]**
- **Status:** Done. `PreferencesScreen` built with Compose Material3.

---

## PHASE 3: Feature - Map & Location Services
*Getting context for the LLM.*

### Task 3.1: Location Manager Wrapper (COMPLETED)
- **Description:** Create a wrapper around Android's `FusedLocationProvider` to get a one-time coarse location.
- **VSA Context:** `features/map_integration/LocationService.kt`.
- **Difficulty:** Medium (Requires permissions handling)
- **Rec. Model:** **[7B]**
- **Status:** Done. `LocationService` implemented with suspend function.

### Task 3.2: Places API Client (Tool) (COMPLETED)
- **Description:** Create a simple client to query Google Places API for "nearby parks", "museums", etc., to validate LLM suggestions or feed context.
- **VSA Context:** `features/map_integration/PlacesClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Status:** Done. `PlacesClient` and `PlacesService` created. `NetworkModule` added for DI.

---

## PHASE 4: Feature - Suggestions Engine (The "Brain")
*Orchestrating the LLM with context.*

### Task 4.1: Prompt Engineering Builder (COMPLETED)
- **Description:** Create a `PromptBuilder` that takes `ActivityConstraints`, `Location`, and `Weather` and formats the system prompt.
- **VSA Context:** `features/suggestions/logic/PromptBuilder.kt`.
- **Difficulty:** High (Nuanced logic)
- **Rec. Model:** **[14B+]**
- **Status:** Done. `PromptBuilder` creates structured prompt for family activities.

### Task 4.2: Suggestions Repository (The Orchestrator) (COMPLETED)
- **Description:** The glue code. It calls `LocationService`, gets the Prompt from `PromptBuilder`, calls `LlmClient`, and parses the result.
- **VSA Context:** `features/suggestions/data/SuggestionsRepository.kt`.
- **Difficulty:** High
- **Rec. Model:** **[14B+]**
- **Status:** Done. `SuggestionsRepository` orchestrates the flow and parses JSON response.

### Task 4.3: Suggestions UI (Card List) (COMPLETED)
- **Description:** Display the results.
- **VSA Context:** `features/suggestions/ui/SuggestionsList.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Status:** Done. `SuggestionsScreen` displays loading state and activity cards.

---

## PHASE 5: Polish & "Family Specifics"
*Adding the suggested "Meltdown Preventer" and "Pack Your Bags" features.*

### Task 5.1: "Pack Your Bags" Logic (COMPLETED)
- **Description:** Simple logic mapping. If weather = rain, add "Umbrella". If activity = park, add "Sunscreen".
- **VSA Context:** `features/suggestions/logic/PackingListGenerator.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Status:** Done. `PackingListGenerator` creates list based on context.

### Task 5.2: "Escape Hatch" FAB (COMPLETED)
- **Description:** Floating Action Button on the active itinerary screen that deep links to "Restrooms near me" on Maps.
- **VSA Context:** `features/itinerary_planner/ui/EscapeHatch.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Status:** Done. `EscapeHatchFab` launches map intent for restrooms.
