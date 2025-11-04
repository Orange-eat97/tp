---
layout: page
title: CareLink
---

[![Java CI](https://github.com/AY2526S1-CS2103T-W13-1/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2526S1-CS2103T-W13-1/tp/actions/workflows/gradle.yml)
[![codecov](https://codecov.io/github/AY2526S1-CS2103T-W13-1/tp/graph/badge.svg?token=086BE9RVL0)](https://codecov.io/github/AY2526S1-CS2103T-W13-1/tp)

![Ui](images/Ui.png)

**CareLink** is a **record management application** built for **NGOs** to efficiently maintain information about their **beneficiaries** and **volunteers**. Designed as a **single-user desktop application**, CareLink emphasizes **speed and productivity** through a **Command Line Interface (CLI)** optimized for quick typing and minimal mouse use.

It allows NGOs to easily manage contact data, search and sort through records, and perform advanced actions like autocompletion, command cycling, and clipboard copying—all while automatically saving data securely.

## Key Features

### Contacts Management

-   **Add (`add`)** – Create new beneficiary or social worker contacts.

-   **Edit (`edit`)** – Update existing contact details.

-   **Delete (`delete`)** – Remove outdated or duplicate entries.

-   **Clear (`clear`)** – Wipe all stored contacts at once.

### Display and Searching

-   **List (`list`)** – Display all saved contacts.

-   **Find (`find`)** – Search for contacts by name, phone, email, address, or tag.

-   **Sort (`sort`)** – Organize contacts by various attributes.

-   **Closest (`closest`)** – Identify volunteers/beneficiaries nearest to a beneficiary/volunteers’s region.

### Advanced Features

-   **Command History Navigation (`↑ ↓`)** – Cycle through previously entered commands.

-   **Autocomplete (`TAB`)** – Predict and complete commands or attributes.

-   **Clipboard Copying** – Copy contact details easily via a button.

-   **Automatic Saving** – Data is saved after every command execution.

-   **Manual File Editing** – Modify the saved data file directly if needed.

-   **Data Archiving (coming in v2.0)** – Manage and store older data files safely.

### General Commands

-   **Help (`help`)** – View available commands and usage instructions.

-   **Exit (`exit`)** – Close the application safely.

## Credits
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).


**Acknowledgements**

* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)
