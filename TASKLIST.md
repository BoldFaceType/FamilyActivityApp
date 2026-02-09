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

### Task 1.1: Dependency Injection Setup
- **Description:** Set up Hilt or Koin for the project. Create the main `AppModule` to provide the `Context` and `AppConfig`.
- **VSA Context:** Place in `core/di`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Input for Model:** "Create a Hilt module in `core/di` that provides a singleton `Context` and reads the `config.json` asset into an `AppConfig` data class."

### Task 1.2: LLM Client Implementation (Local - Ollama)
- **Description:** Implement the `LlmClient` interface for the local Ollama provider.
- **VSA Context:** `core/llm/OllamaClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Input for Model:** "Implement `OllamaClient` class. It should take a `baseUrl` and `modelName`. Use Ktor or Retrofit to POST a JSON body to the endpoint and return the `response` string."

### Task 1.3: LLM Client Implementation (Cloud - Generic)
- **Description:** Implement the `LlmClient` interface for OpenAI/Gemini.
- **VSA Context:** `core/llm/CloudClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Input for Model:** "Implement `CloudClient`. It needs an `apiKey` and `modelName`. Ensure it handles the specific JSON request format for [Insert Provider Here]."

---

## PHASE 2: Feature - Family Preferences (The "Inputs")
*The User Constraints slice. Toggles and dropdowns for the family's needs.*

### Task 2.1: Persistence Layer (DataStore)
- **Description:** Create a repository to save/load `ActivityConstraints` (Bathroom, Walking Distance, etc.) using Jetpack DataStore.
- **VSA Context:** `features/preferences/data/PreferencesRepository.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Input for Model:** "Create a repository that saves the fields defined in `ActivityConstraints.kt` to DataStore. Provide a `Flow<ActivityConstraints>`."

### Task 2.2: Preferences ViewModel
- **Description:** Create a ViewModel that exposes the current constraints and methods to update them.
- **VSA Context:** `features/preferences/PreferencesViewModel.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Input for Model:** "Create a ViewModel taking `PreferencesRepository`. Expose a UI State object containing the constraints, and functions like `updateWalkingDistance(int)`."

### Task 2.3: Preferences UI (Compose)
- **Description:** Build the Screen with switches, sliders, and dropdowns.
- **VSA Context:** `features/preferences/ui/PreferencesScreen.kt`.
- **Difficulty:** Medium (UI boilerplate is high volume)
- **Rec. Model:** **[7B]**
- **Input for Model:** "Create a Composable `PreferencesScreen`. Use a Switch for `requiresBathroom`, a Slider for `maxWalkingDistance`, and Chips for `EnergyLevel`. Bind to the ViewModel state."

---

## PHASE 3: Feature - Map & Location Services
*Getting context for the LLM.*

### Task 3.1: Location Manager Wrapper
- **Description:** Create a wrapper around Android's `FusedLocationProvider` to get a one-time coarse location.
- **VSA Context:** `features/map_integration/LocationService.kt`.
- **Difficulty:** Medium (Requires permissions handling)
- **Rec. Model:** **[7B]**
- **Input for Model:** "Create a class that checks for `ACCESS_COARSE_LOCATION` permission. If granted, fetch the current lat/long using `suspendCancellableCoroutine`."

### Task 3.2: Places API Client (Tool)
- **Description:** Create a simple client to query Google Places API for "nearby parks", "museums", etc., to validate LLM suggestions or feed context.
- **VSA Context:** `features/map_integration/PlacesClient.kt`.
- **Difficulty:** Medium
- **Rec. Model:** **[7B]**
- **Input for Model:** "Create a Retrofit service for Google Places Text Search. It should take a query string, location bias, and API key."

---

## PHASE 4: Feature - Suggestions Engine (The "Brain")
*Orchestrating the LLM with context.*

### Task 4.1: Prompt Engineering Builder
- **Description:** Create a `PromptBuilder` that takes `ActivityConstraints`, `Location`, and `Weather` and formats the system prompt.
- **VSA Context:** `features/suggestions/logic/PromptBuilder.kt`.
- **Difficulty:** High (Nuanced logic)
- **Rec. Model:** **[14B+]**
- **Input for Model:** "Write a function that accepts `ActivityConstraints` and constructs a strict system prompt. It must tell the LLM: 'You are a planning assistant for a family with a 5 and 6 year old. The user requires bathroom access: [Bool]. Max walk: [N] meters.' Force JSON output."

### Task 4.2: Suggestions Repository (The Orchestrator)
- **Description:** The glue code. It calls `LocationService`, gets the Prompt from `PromptBuilder`, calls `LlmClient`, and parses the result.
- **VSA Context:** `features/suggestions/data/SuggestionsRepository.kt`.
- **Difficulty:** High
- **Rec. Model:** **[14B+]**
- **Input for Model:** "Create `SuggestionsRepository`. Flow: 1. Get Location. 2. Build Prompt. 3. Call LLM. 4. Try-catch JSON parsing errors. Return a `Result<List<ActivitySuggestion>>`."

### Task 4.3: Suggestions UI (Card List)
- **Description:** Display the results.
- **VSA Context:** `features/suggestions/ui/SuggestionsList.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Input for Model:** "Create a Composable `ActivityCard`. It needs to show: Title, Description, 'Why it fits your kids', and a 'Navigate' button."

---

## PHASE 5: Polish & "Family Specifics"
*Adding the suggested "Meltdown Preventer" and "Pack Your Bags" features.*

### Task 5.1: "Pack Your Bags" Logic
- **Description:** Simple logic mapping. If weather = rain, add "Umbrella". If activity = park, add "Sunscreen".
- **VSA Context:** `features/suggestions/logic/PackingListGenerator.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Input for Model:** "Create a pure function `generatePackingList(activityType, weather)`. Return a list of strings like 'Water bottles', 'Change of socks'."

### Task 5.2: "Escape Hatch" FAB
- **Description:** Floating Action Button on the active itinerary screen that deep links to "Restrooms near me" on Maps.
- **VSA Context:** `features/itinerary_planner/ui/EscapeHatch.kt`.
- **Difficulty:** Low
- **Rec. Model:** **[3B]**
- **Input for Model:** "Create a FAB with a poop emoji 💩 (or similar). On click, launch an Intent with `Uri.parse('geo:0,0?q=restroom')`."
