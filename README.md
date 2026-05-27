# City AI: Valle Silencio 🏘️🤖

**City AI** is an open-source, LLM-powered autonomous NPC simulation game. Set in the isolated, fictional town of "Valle Silencio" (Silence Valley), this project explores what happens when town inhabitants are driven by local AI models, possessing their own routines, relationships, and memories.

## 🌟 The Concept

Founded in 1885 by two rival families, Valle Silencio lives by one unwritten rule: *Nobody comes from outside, nobody leaves forever.* 

The simulation separates logic into two layers:
1. **The Game Client (Java):** Manages the world state, time, character locations, and triggers interactions. It stores the histories and biographies of the inhabitants.
2. **The AI Server (Python):** Uses a locally hosted LLM (like Qwen GGUF) via `llama-cpp-python` to process contextual JSON data and generate character decisions, dialogues, and actions.

## 🏗️ Architecture

```text
[ Java Game Client ]
         ↓  (TCP Sockets)
[ Python AI Server ]
         ↓
[ Qwen GGUF Model (RAM/GPU) ]
```

The Java client sends the current context (time of day, character bio, recent events, physical state). The Python server appends system instructions to anchor the AI in reality and returns precise, parseable actions, including dialogue, physical actions, time spent, and voice volume (`RESVOL`).

## 📚 Documentation

Detailed documentation can be found in the `docs/` folder:
- [Architecture & Lore](docs/ARCHITECTURE.md)
- [Future Improvements & Roadmap](docs/IMPROVEMENTS.md)

## 🚀 Getting Started

*(Instructions for setting up the Python inference server and compiling the Java client will be added here as the project evolves.)*

### Prerequisites
- Python 3.10+
- Java (JDK 17+)
- `llama-cpp-python` (with GPU support recommended)

## 🤝 Contributing

We are actively looking for collaborators! Whether you are a Java developer, a Python/AI enthusiast, or a game designer, your help is welcome.

1. **Join our Discord:** Come chat with us about the project, prompt engineering, and architecture on [our Discord server](https://discord.gg/PQsactCfr).
2. Check our **Issues** tab for tasks labeled `good first issue`.
3. Fork the repository and create your feature branch.
4. Submit a Pull Request!

For more details on how to contribute, please read our [Contributing Guidelines](docs/CONTRIBUTING.md).

## 📜 License

This project is licensed under the MIT License.
