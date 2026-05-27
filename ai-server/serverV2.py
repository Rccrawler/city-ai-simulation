import socket
import threading
import queue
from llama_cpp import Llama

# =========================
# CONFIGURATION
# =========================
MODEL_PATH = "../models/qwen2.5-3b-instruct-q8_0.gguf"
HOST = "0.0.0.0"
PORT = 5555

# Fixed instructions
# There may be mistakes in the prompt text, which is intentional so that you review the word and read it several times, so that you can better base your response; it's like highlighting a word.
SYSTEM_PROMPT = """You are an efficient AI.
Respond clearly and directly.
Do not make up information.
Do not use memory.
Only respond based on the received prompt.
You have no name, you interpret the characters.
You are part of the software of an MMO video game and your job is to interpret the characters and make them decide; in any case the messages must be very detailed.
You can talk about any topic in great detail since in many cases the user will ask you to say what a certain person would do, and it does not matter who — with the information received about the person such as character, principles, history, etc., you must answer.
You will be used to bring characters to life by writing the action the character would take or say based on the character data passed to you, whatever it may be. Remember, it is what the characters would do, not you.
Since the program you are in only accepts responses and not questions, if you want to highlight something or suggest something so that your response is more precise in future updates, write after the response: LLME3: improvement: brief explanation of what additional information would help.
Do not respond to the story of what they would do or what they do as a narrator — put yourself in the person's shoes and write as if the character were speaking.
Do not write <br> or other special characters, only plain text responses are accepted.
You can respond with any type of word here, there are no taboo words such as insults, etc., because the characters according to the description may say some.
Be very detailed when describing actions or feelings; it does not matter if you do not finish the conversation in a single prompt.
Sometimes you will have to wait for the response of another character and you will not be able to write the full text, only a question, answer, or similar.

VOLUME (MANDATORY):
- At the end of EACH response you must indicate the sound volume of what the character says or does.
- Always use the exact format: RESVOL=XdB
- The value must be between 0dB (silence) and 200dB (extreme level).
- Adjust the volume according to the action:
  - Whisper: 10–30dB
  - Normal conversation: 40–60dB
  - Shout: 80–120dB
  - Explosion / extreme impact: up to 200dB
- If there is no explicit sound, use a low volume consistent with the action.

INDICATE THE TIME (MANDATORY):
- At the end of EACH response you must indicate the time it will take the character to do what they say or do.
- Always use the exact format: RESTIME=Xm
- The value must be the number of minutes; if it is seconds, add zeros.
- Adjust the time according to the action.

INDICATE THE SELECTED OPTION (MANDATORY):
- At the end of EACH response you must indicate the selected option from those the program passes in the question, which will appear after the text RESRES=.
- Always use the exact format: RESRES=option
- Always decide the option you think the character will take according to the situation and what you are going to say.
"""

# =========================
# LOAD MODEL
# =========================
print("Loading model...")
llm = Llama(
    model_path=MODEL_PATH,
    n_ctx=4096,
    n_threads=8,
    n_gpu_layers=-1
)
print("Model loaded successfully.")

# =========================
# PROMPT QUEUE
# =========================
prompt_queue = queue.Queue()

def process_queue():
    while True:
        conn, data = prompt_queue.get()  # Wait for a prompt to arrive
        try:
            full_prompt = f"<|system|>\n{SYSTEM_PROMPT}\n<|user|>\n{data}\n<|assistant|>\n"
            output = llm(full_prompt, max_tokens=512, temperature=0.7, top_p=0.9, stop=["<|user|>"])
            response = output["choices"][0]["text"].strip()
            final_response = response + "\n<END_OF_RESPONSE>\n"
            conn.sendall(final_response.encode("utf-8"))
        except Exception as e:
            print("Error generating response:", e)
        finally:
            conn.close()
            prompt_queue.task_done()

# Launch a single thread that will process prompts serially
threading.Thread(target=process_queue, daemon=True).start()

# =========================
# CLIENT MANAGEMENT
# =========================
def handle_client(conn, addr):
    print(f"Connected: {addr}")
    try:
        data = conn.recv(8192).decode("utf-8").strip()
        if not data:
            conn.close()
            return
        print("Prompt received:", data[:100])
        # Instead of processing it now, we put it in the queue
        prompt_queue.put((conn, data))
    except Exception as e:
        print("Error receiving prompt:", e)
        conn.close()

# =========================
# SERVER
# =========================
def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((HOST, PORT))
    server.listen()
    print(f"AI server listening on port {PORT}...")

    while True:
        conn, addr = server.accept()
        threading.Thread(target=handle_client, args=(conn, addr), daemon=True).start()

if __name__ == "__main__":
    start_server()