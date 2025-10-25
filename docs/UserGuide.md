---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

## Table of Contents
{: .toc-plain}
- [Quick start](#quick-start)


- <details markdown = 1><summary><a href="#features">Features</a></summary>

    - <details markdown = 1><summary>👤<a href="#individual-operations">Individual Operations</a></summary>
      
      - [Adding a person: `add`](#adding-a-person-add)
      - [Editing a person : `edit`](#editing-a-person--edit)
      - [Locating persons: `find`](#locating-persons-find)
      - [Sorting persons: `sort`](#sorting-persons-sort)
      - [Deleting a person : `delete`](#deleting-a-person--delete)
      
      </details>

    - <details markdown = 1><summary>👥<a href="#group-operations">Group Operations</a></summary>
      
      - [Listing all persons : `list`](#listing-all-persons--list)
      - [Clearing all entries : `clear`](#clearing-all-entries--clear)
      
      </details>

    - <details markdown = 1><summary>🛠<a href="#support-functions">Support Functions</a></summary>
      
      - [Viewing help : `help`](#viewing-help--help)
      - [Exiting the program : `exit`](#exiting-the-program--exit)
      - [Cycling between commands : `UP_Key DOWN_Key`](#cycling-between-commands)
      - [Autocomplete : `TAB_Key`](#autocomplete)
      - [Saving the data](#saving-the-data)
      - [Editing the data file](#editing-the-data-file)
      - [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
      
      </details>
      </details>


- [FAQ](#faq)


- [Known issues](#known-issues)


- [Command summary](#command-summary)






--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/volunteer` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Feature

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items in with `|` are valid inputs.<br>
  e.g `t/volunteer|beneficiary` can be used as `t/volunteer` or as `t/beneficiary`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
  Note: For sort command, the order of parameters defines the order of sorting.
  e.g. `n/ p/` sorts name then sorts phone number, `p/ n/` sorts phone number then name

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>


### Individual Operations

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS t/volunteer|beneficiary [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags, but must have a tag that is either volunteer or beneficiary
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/volunteer`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/beneficiary`

[Table of Contents](#table-of-contents)

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* If tags are edited, must include a tag indicating volunteer or beneficiary.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`

[Table of Contents](#table-of-contents)

### Locating persons: `find`

Filters persons whose fields match the keywords.

**Format**:  
`find [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS]`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* The search is <u>case-insensitive</u> e.g `n/hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `n/Hans Bo` will match `Bo Hans`.
 Multiple keywords per field are allowed. e.g. `n/Hans Bo` will match `Hans Lee` and `Bo Bae`.
* Only full words will be matched e.g. `n/Han` will not match `Hans`, `p/94628739` will not match `9462 8739`.

</div>

**Expected Outcome:**
* Displays all persons who matches at least one keyword in each given field.
  * e.g. `find n/Hans p/1234` will return all persons whose names contain `Hans` <u>and</u> whose phone number is `1234`.

**📘Examples:**
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)  
  

* `find n/irfan bernice t/volunteer` returns `Bernice Lee` and `Irfan Ibrahim`, who are both `volunteers` <br>
    ![result for 'find irfan bernice t/volunteer'](images/findIrfanBerniceResult.png)

[Table of Contents](#table-of-contents)

### Sorting persons: `sort`

Sorts persons by the fields in the order of the parameters

Format: `sort [n/] [p/] [e/] [a/] [t/]`

* Order of parameters defines the order of sorting.
  e.g. `n/ p/` sorts name then sorts phone number, `p/ n/` sorts phone number then name

[Table of Contents](#table-of-contents)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX [INDEX]...`

* Deletes the person(s) at the specified `INDEX` and `[INDEX]...`.
* Each index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1 2` deletes the 1st and 2nd persons in the results of the `find` command.

[Table of Contents](#table-of-contents)

### Group Operations

### Listing all persons : `list`

Shows a list of all persons in the address book.

**Format**:                  
`list`

**Expected Outcome:**
* Displays all persons' contact details in the address book.
* At the panel below the command box, you can see the total number of contacts.

**📘Example**:
<br>
![result for 'find alex david'](images/list-success-screenshot.png)

[Table of Contents](#table-of-contents)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

[Table of Contents](#table-of-contents)

### Support Functions

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

[Table of Contents](#table-of-contents)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

[Table of Contents](#table-of-contents)

### Cycling between commands

Cycles between commands in the command history.

`UP Key`: Goes to the previous command in history

`DOWN Key`: Goes to the next command in history

[Table of Contents](#table-of-contents)

### Autocomplete
 
Carelink offers possible complements of your input text. It helps you autocomplete command words, and reminds you of tags of personal information, like `n/` for name.

Format: `tab`: Auto-complete with the complement that appears below command box.
> Pressing **Tab** manages spacing as well, just type as normal!


[Table of Contents](#table-of-contents)

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ
**Q**: Where are my data files stored?<br>
**A**: In a folder named `data` in the same directory of `addressbook.jar`, you can find the data file named `addressbook.json`. 
Data is only stored locally, saved automatically whenever commands that modify data are executed.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file (`addressbook.json`) it creates with the original data file.
If the data folder does not exist yet, you can simply copy over the entire `data` folder from your previous AddressBook home folder.

**Q**: What do I do if the command box says "Unknown command"?<br>
**A**: This means that the command you entered is not recognized by the application. Please ensure that you have typed the command correctly according to the formats specified in this user guide. You can type `help` to view the help window for reference.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS t/volunteer|beneficiary [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX [INDEX]...`<br> e.g., `delete 1 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [prefix/KEYWORDS]`<br> e.g., `find n/James p/92813321`
**Sort** | `sort [prefixes]` <br> e.g, `sort n/ p/`
**List** | `list`
**Help** | `help`
