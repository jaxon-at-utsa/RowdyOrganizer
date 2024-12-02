# Rowdy Organizer

Rowdy Organizer is a simple and intuitive app designed to help you organize your daily tasks efficiently. With features like task management, a calendar view, and the ability to mark tasks as completed, Rowdy Organizer ensures you stay on top of your schedule.

---

## Table of Contents
1. [Features](#features)
2. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
3. [Current Functionality](#current-functionality)
4. [Known Limitations](#known-limitations)
5. [Team Members](#team-members)

---

## Features

- Add, edit, and delete tasks with ease.
- View tasks for the current day in the **Daily Tasks** section.
- Manage tasks for specific dates using the **Calendar View**.
- Mark tasks as completed and visually distinguish them.
- Automatically handle invalid task inputs like incorrect dates or missing fields.

---

## Getting Started

### Prerequisites

To clone and run the Rowdy Organizer application, you will need:

1. **Java Development Kit (JDK)**: Version 11 or higher.
2. **Android Studio**: Latest version.
3. **Android SDK Tools:**
    - **Compile SDK Version:** 35 (Android 14)
    - **Minimum SDK Version:** 26 (Android 8.0)
    - **Target SDK Version:** 34 (Android 13)

4. **Kotlin:** Ensure your Android Studio supports Kotlin (latest stable version).

### Installation

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/jaxon-at-utsa/RowdyOrganizer.git
   ```
2. Open the project in Android Studio:
    - Open Android Studio.
    - Select **File > Open** and navigate to the cloned repository folder.
    - Click **Open**.

3. Sync the project with Gradle:
    - Allow Android Studio to automatically sync dependencies.

4. Run the application:
    - Connect an Android device or start an emulator.
    - Click the green **Run** button in Android Studio.

---

## Current Functionality

The following functionality is implemented and works as intended:

- **Task Management:**
    - Add tasks with valid date and time inputs using a convenient date and time picker.
    - Tasks can be marked as completed and are visually updated in the task list.
    - Delete tasks when they are no longer needed.

- **Daily Tasks Page:**
    - Displays tasks scheduled for the current day.
    - Allows adding new tasks directly from the page.

- **Calendar Page:**
    - View tasks for specific dates.
    - Easily navigate between dates using the integrated calendar view.

---

## Known Limitations

The following features are not implemented in the current version or have known issues:

- Tasks cannot be edited after creation.
- Limited recurring task functionality.
- No notification or reminder system is available for tasks.

---

## Team Members

- **Melissa Martinez**
- **Jaxon Gopffarth**
- **Carlos Hernandez**
