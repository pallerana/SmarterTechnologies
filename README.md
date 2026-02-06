## SmarterTechnologies – Package Sorting Challenge

This project contains a production-grade Java implementation of the **Core Engineering Technical Screen** from Smarter Technologies: given a package's dimensions (cm) and mass (kg), decide whether it goes to the **STANDARD**, **SPECIAL**, or **REJECTED** stack.

Core logic lives in `org.example.PackageSorter`, with JUnit 5 tests in `org.example.PackageSorterTest`.

---

### 1. Prerequisites

- **Java**: JDK 8 or later (11+ recommended)
- **Gradle wrapper**: already included (`gradlew`, `gradlew.bat`), no separate Gradle install needed

All commands below assume you are in the project root (the directory with `build.gradle`).

---

### 2. Running the main class

The demo entrypoint is `org.example.Main`. It invokes `PackageSorter.sort(...)` with a few sample inputs and prints the resulting stack names.

#### 2.1. From an IDE (IntelliJ IDEA, VS Code, Eclipse)

1. **Import the project** as a Gradle project.
2. Locate `src/main/java/org/example/Main.java`.
3. Right-click the `main` method and choose **Run 'Main.main()'**.

#### 2.2. From the command line (Mac / Linux)

```bash
./gradlew clean build

# Run the compiled main class
java -cp build-out/classes/java/main org.example.Main
```

#### 2.3. From the command line (Windows)

```bat
gradlew.bat clean build

REM Run the compiled main class
java -cp build-out\classes\java\main org.example.Main
```

> Note: This project configures Gradle's build directory as `build-out` instead of the default `build`, to avoid file-lock issues on some environments.

---

### 3. Running the tests

JUnit 5 tests validate:
- STANDARD vs SPECIAL vs REJECTED at and around the thresholds
- Negative input handling (exceptions)
- Volume overflow handling

#### 3.1. From the command line (Mac / Linux)

```bash
./gradlew test
```

#### 3.2. From the command line (Windows)

```bat
gradlew.bat test
```

#### 3.3. From an IDE

1. Open `src/test/java/org/example/PackageSorterTest.java`.
2. Right-click the class or individual test methods.
3. Choose **Run 'PackageSorterTest'** (or the specific test).

---

### 4. Git workflow (local)

If you want to commit changes locally:

```bash
git status
git add .
git commit -m "Update package sorter logic/tests"
git push
```

Adjust the commit message as appropriate for your changes.

