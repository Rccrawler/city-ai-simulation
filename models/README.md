# AI Models Directory 🧠

This folder is designed to store the large language models (LLMs) used by the Python server. Because these models are very large (several gigabytes), they are ignored by Git and will not be uploaded to the repository.

## Where to download the model?

For **City AI**, we highly recommend using a quantized **Qwen GGUF** model for the best balance between speed and logic on local hardware.

1. Go to HuggingFace to download the model:
   - **Recommended:** [Qwen1.5-7B-Chat-GGUF (Q4_K_M or Q5_K_M)](https://huggingface.co/Qwen/Qwen1.5-7B-Chat-GGUF/tree/main)
2. Download the `.gguf` file.
3. Place the downloaded `.gguf` file directly into this `models/` folder.
4. Update the path in your `ai-server/servidor.py` file to point to the exact name of the file you downloaded.
