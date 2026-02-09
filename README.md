# Family Activity App - Vertical Slice Architecture

## Project Structure

- **`app/src/main/kotlin/com/familyapp/core`**: Infrastructure code (Network, Database, LLM Orchestration).
- **`app/src/main/kotlin/com/familyapp/features`**: Vertical slices. Each folder contains its own UI, Logic, and Data models.
    - `suggestions`: LLM-powered activity generator.
    - `map_integration`: Google Maps API integration.
    - `preferences`: Toggle and Dropdown management for persistent constraints.
- **`config.json`**: Switch between Local LLM (Ollama/LM Studio) and Cloud LLM (Gemini/OpenAI).

## Setup
1. Update `config.json` with your preferred LLM endpoint.
2. If using local, ensure Ollama/LM Studio is running.
3. If using cloud, provide your API key.
