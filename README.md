# 🪄 Yaad

Yaad is a Java Swing desktop application built with a focus on simplicity and modular design. It demonstrates how to structure a Java GUI program and launch it correctly on the Event Dispatch Thread (EDT) using SwingUtilities.invokeLater().

## 🚀 Getting Started
###📦 Prerequisites

Java 17+ (or any version compatible with your project)

A Java IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code with Java extension)

Git (optional, for cloning the repository)

##🧰 Building & Running
###🖥️ Option 1: Using compile.bat

If you’re on Windows and want to build from the command line:

./compile.bat


This will compile the project and run the application automatically.

###💻 Option 2: Running from an IDE

Open the project folder in your favorite IDE.

Add the external libraries located in the src/lib
 directory to your project’s classpath.

Locate the Yaad class (typically in src/main/java/...).

Run the main() method:

public static void main(String[] args) {
    SwingUtilities.invokeLater(Yaad::new);
}


This ensures the GUI starts correctly on the Swing Event Dispatch Thread.

##🧪 Project Structure
Yaad/
├─ src/
│  ├─ main/
│  │  └─ java/
│  │     └─ Yaad.java       # Main application entry point
│  └─ lib/                  # External libraries (add these to classpath)
├─ compile.bat              # Build script (Windows)
└─ README.md                # Project documentation

##🧠 How It Works

The main entry point of the application uses:

SwingUtilities.invokeLater(Yaad::new);


SwingUtilities.invokeLater() schedules the GUI to be created on the Event Dispatch Thread (EDT) — the proper thread for Swing operations.

Yaad::new is a constructor reference, which creates an instance of Yaad and starts the application.

##🛠️ Troubleshooting

ClassNotFoundException – Make sure the .jar files in src/lib are added to your project classpath.

Compilation errors – Ensure you’re using a compatible JDK (Java 17+ recommended).

No GUI appears – Check if the main class is being run and that the Yaad constructor sets up the Swing components.

##📜 License

--
