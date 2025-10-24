---
layout: page
title: User Guide
---

**CareLink**’s core purpose is to **connect** people in need with the right help as quickly as possible.

By consolidating beneficiary and worker data into a single, efficient system, CareLink empowers dispatchers to:

* Create and update records for beneficiaries and social workers
* Access information quickly using **optimized find and sort commands**
* Automatically identify the **closest available social worker**
* Reduce errors and delays caused by manual lookups or outdated records

In short, CareLink **enhances coordination efforts** and supports frontline workers in delivering social assistance more effectively.

* Table of Contents
{:toc}

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

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/yishun t/volunteer` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

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

### Viewing help : `help`

Shows a message explaining how to access the help page.

**Format**:
`help`

**Expected Outcome:**
* Opens a window that allows you to copy the link to the User Guide website

**📘Examples:**
* ![help message](images/helpMessage.png)


### Adding a person: `add`

Adds a person to the address book.

**Format**:
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/REGION t/volunteer|beneficiary [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* Attributes must follow [Valid Attributes](#valid-attributes)
* A person can have any number of tags, but must have a tag that is either volunteer or beneficiary
* A person's attributes can be edited via `edit` later on, however all attributes must first be provided

</div>

**Expected Outcome:**
* Adds a new person to the list with the given details
  * e.g. `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/woodlands t/volunteer` will add John Doe to the list and set his attributes to the given attributes

**📘Examples:**
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/woodlands t/volunteer`
* `add n/Betsy Crowe e/betsycrowe@example.com a/24 Mandai Garden street r/yishun p/1234567 t/beneficiary t/senior`

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

### Editing a person : `edit`

Edits an existing person in the address book.

**Format**:
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REGION] [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* The index is affected by `find` and `sort`
* At least one of the optional fields must be provided.
* Attributes must follow [Valid Attributes](#valid-attributes)
* If tags are edited, must include a tag indicating volunteer or beneficiary.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.

</div>

**Expected Outcome:**
* Edits existing person in the list with the given details
  * e.g. `edit 5 p/98765432` will edit the phone number of the person at index `5` in the list to `98765432`

**📘Examples:**
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`

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

### Sorting persons: `sort`

Sorts persons by the fields in the order of the parameters

Format: `sort [n/] [p/] [e/] [a/] [t/]`

* Order of parameters defines the order of sorting.
  e.g. `n/ p/` sorts name then sorts phone number, `p/ n/` sorts phone number then name

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX [INDEX]...`

* Deletes the person(s) at the specified `INDEX` and `[INDEX]...`.
* Each index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1 2` deletes the 1st and 2nd persons in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Cycling between commands

Cycles between commands in the command history.

`UP Key`: Goes to the previous command in history

`DOWN Key`: Goes to the next command in history

### Exiting the program : `exit`

Exits the program.

Format: `exit`

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

## Valid Attributes
### 👤 Name

Alphanumeric + spaces only, not blank.

* `Alice Tan`
* `John Doe`
* `Li Wei`
* `Aisyah`
* `Bob 2`

### 📧 Email

Emails must follow: `local-part@domain`, allowed special chars in local part (`+_.-`), no leading/trailing special chars, domain labels alphanumeric & ≥2 letters at the end.

* `alice@example.com`
* `john_doe123@mail.sg`
* `alex+promo@sub-domain.co.uk`
* `my.email@ntu.edu.sg`
* `contact-1@tech-startup.io`
* `user123@domain99.net`

### 📞 Phone Number

Digits only, at least 3 numbers long.

* `999`
* `98765432`
* `65123456`
* `1800123`

### 🌍 Region

Regions must be a valid region (listed below):

* Woodlands
* Yishun
* Sembawang
* Mandai
* Sungei Kadut
* Punggol
* Sengkang
* Hougang
* Seletar
* Serangoon
* Pasir Ris
* Tampines
* Bedok
* Paya Lebar
* Changi
* Toa Payoh
* Bishan
* Ang Mo Kio
* Novena
* Geylang
* Marine Parade
* Kallang
* Queenstown
* Bukit Merah
* Bukit Timah
* Tanglin
* River Valley
* Jurong West
* Jurong East
* Boon Lay
* Clementi
* Bukit Batok
* Bukit Panjang
* Tuas
* Lim Chu Kang
* Western Water Catchment
* Tengah

### 🏠 Address

Address can be any non-blank string.

-   `21 Lower Kent Ridge Rd, Singapore`
-   `Block 123, #02-45 Clementi Ave 3`
-   `10 Dover Drive`
-   `Jurong West Street 42`
-   `Marina Bay Sands Tower 1`

### 🏷️ Tags

Tags are alphanumeric, at least one tag must be `volunteer` or `beneficiary`.

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
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/REGION t/volunteer|beneficiary [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 r/woodlands t/volunteer`
**Clear** | `clear`
**Delete** | `delete INDEX [INDEX]...`<br> e.g., `delete 1 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REGION] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [prefix/KEYWORDS]`<br> e.g., `find n/James p/92813321`
**Sort** | `sort [prefixes]` <br> e.g, `sort n/ p/`
**List** | `list`
**Help** | `help`
