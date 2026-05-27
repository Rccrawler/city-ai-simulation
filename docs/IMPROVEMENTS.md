# 🚀 Future Improvements & Roadmap

This document outlines the current ideas and tasks required to improve the **City AI: Valle Silencio** simulation.

## 📝 Programming Roadmap (To-Do List)

- [ ] **Prompt Engineering & Testing:** Hacer pruebas exhaustivas con la IA (Qwen) para perfeccionar el prompt y asegurar que los personajes respondan con el formato exacto (`RESRES=...`).
- [ ] **Java Class Adjustments:** Ajustar, optimizar y terminar de programar las clases de Java (especialmente la lectura y escritura de los JSONs y el multihilo).
- [ ] **NPC Memory Structure:** Implementar la lógica final para que el cliente lea y envíe el "HistorialConversaciones" correctamente a Python.
- [ ] **Error Handling:** Añadir reconexión automática si el servidor Python se cae.

## 🧠 AI Anchoring Concepts (Conceptos de Mejora Actuales)

Para evitar que el LLM alucine o se invente cosas, implementaremos estas "Anclas":

1. **Realism Anchor (Ancla de Realismo):** 
   En el archivo de Python, siempre debe haber una instrucción fija en el system prompt: *"Eres un habitante de un pueblo realista de 2026. No existe la magia, no eres un animal. Tus acciones deben ser lógicas para un ser humano."*

2. **Biography Anchor (Autocorrección por contexto):** 
   Como Java guarda la biografía en el archivo `Biografia.java`, si la IA intenta inventarse que es un astronauta, en el siguiente turno leerá su biografía de "Fontanero". El modelo mismo corregirá el rumbo automáticamente al leer el contexto correcto.
