---
layout: page
title: User Guide
---

CareLink helps Non-Governmental Organisations (NGOs) deliver social assistance efficiently by 
connecting beneficiaries to volunteers as quickly as possible. 🤝

NGOs often serve a large number of beneficiaries with a limited pool of volunteers, 
making it challenging to respond promptly to those in need.

By consolidating beneficiary and volunteer data into a single, streamlined system 📚, CareLink enables dispatchers to:

* 📝 create and update records for beneficiaries and volunteers
* 🔍 access information quickly using **optimized find and sort commands**
* 📍 automatically identify the **closest available volunteer**
* 📈 reduce errors and delays caused by manual lookups or outdated records

In short, CareLink helps dispatchers quickly access and update beneficiary and volunteer records. 💙

## Table of Contents

- [Quick start](#quick-start)

- <a href="#features">Features</a>

    - [Command Format](#command-format)

    - [Viewing help : `help`](#viewing-help--help)

    - <a href="#contacts-management">Contacts Management</a>

      - [Adding a contact: `add`](#adding-a-contact-add)

      - [Editing a contact : `edit`](#editing-a-contact--edit)

      - [Deleting a contact : `delete`](#deleting-a-contact--delete)

      - [Clearing all entries : `clear`](#clearing-all-entries--clear)

    - <a href="#display-and-searching">Display and Searching</a>

      - [Listing all contacts : `list`](#listing-all-contacts--list)

      - [Locating contacts: `find`](#locating-contacts-find)

      - [Sorting contacts: `sort`](#sorting-contacts-sort)

    - <a href="#advanced-features">Advanced Features</a>

      - [Cycling between commands : `UP_Key DOWN_Key`](#cycling-between-commands)

      - [Autocomplete : `TAB_Key`](#autocomplete)

      - [Saving the data](#saving-the-data)

      - [Editing the data file](#editing-the-data-file)

      - [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)

    - [Exiting the program : `exit`](#exiting-the-program--exit)

    - [Valid Attributes for contact](#valid-attributes)

- [FAQ](#faq)

- [Known issues](#known-issues)

- [Command summary](#command-summary)


--------------------------------------------------------------------------------------------------------------------

## Quick start

#### 1️⃣ Install Java (if you haven’t already)

CareLink requires **Java 17 or above** to run.

* If **Java 17 or above** is not installed, please follow the specific installation instructions mentioned [here](https://se-education.org/guides/tutorials/javaInstallation.html) to avoid version issues

<div markdown="block" class="alert alert-info">

**:information_source: Follow the guide that fits your computer's operating system**<br>

</div>

#### 2️⃣ Download CareLink

Download the latest **CareLink `.jar` file** from our [Releases page](https://github.com/AY2526S1-CS2103T-W13-1/tp/releases).

#### 3️⃣ Choose a Home Folder

Move the downloaded `.jar` file to a folder where you want CareLink to store your volunteer and organization data.
You get to choose the location. Treat it as your _CareLink Home Folder_.

#### 4️⃣ Launch CareLink

1.  Open your **Command Prompt** for Windows / **Terminal** for Mac

2.  Navigate to your CareLink Home folder:

    `cd path/to/home_folder`

    For example, if CareLink Home folder is `C:/usr/folder/CareLink` and the terminal is pointing to `C:/usr`, run the command `cd folder/CareLink`.

    Your terminal should now show you are inside that folder.

3.  Run CareLink:

    `java -jar addressbook.jar`

<br>
About 10 seconds later, the CareLink interface will greet you with sample volunteer data so you can explore comfortably.

![Ui](images/Ui.png)

#### 5️⃣ Try Out Some Commands

In the command box at the bottom, type a command and hit **Enter** to execute.

Some useful examples for volunteer admin staff:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/yishun t/volunteer` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

#### 6️⃣ Learn More

Details of every feature and command can be found in the [Features](#features) section below. You will soon explore assigning roles, tracking participation, and keeping volunteer info updated effortlessly!

-----

## Features
-----

### Command Format
<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the attributes to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a attribute which can be used as `add n/John Doe`, `n/` is its attribute prefix.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items in with `|` are valid inputs.<br>
  e.g `t/volunteer|beneficiary` can be used as `t/volunteer` or as `t/beneficiary`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Attributes can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous attributes for commands that do not take in attributes (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

------

### Viewing help : `help`

Shows a message explaining how to access the help page.

**✏️ Format:**<br>
`help`

**📘 Example:**
* `help`<br>![help message](images/helpMessage.png)

Simply click the `Copy URL` button to copy the link and paste it in your web browser of choice 
(e.g. Google Chrome or Safari) to access the User Guide website.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## Contacts Management
-----

### Adding a contact: `add`

Adds a contact to the address book.

**✏️ Format:**<br>
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/REGION t/volunteer|beneficiary [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* Attributes must follow [Valid Attributes](#valid-attributes)
* A contact can have any number of tags, but must have a tag that is either volunteer or beneficiary
* A contact's attributes can be edited via `edit` later on, however all attributes must first be provided

</div>

**📘 Examples:**
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/woodlands t/volunteer`
* `add n/Betsy Crowe e/betsycrowe@example.com a/24 Mandai Garden street r/yishun p/1234567 t/beneficiary t/senior`

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Editing a contact : `edit`

Edits an existing contact in the address book.

**✏️ Format:**<br>
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REGION] [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* The index is affected by `find` and `sort`
* At least one of the optional fields must be provided.
* Attributes must follow [Valid Attributes](#valid-attributes)
* If tags are edited, must include a tag indicating volunteer or beneficiary.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the contact will be removed i.e adding of tags is not cumulative.

</div>

**📘 Examples:**
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd contact to be `Betsy Crower`

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------
### Deleting a contact : `delete`

Deletes one or more specified contacts from the address book.

**✏️ Format:**<br>
`delete INDEX [MORE_INDEXES]...`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* Each index refers to the index number shown in the displayed contact list.
* A minimum of one index must be specified
* Each index must be a **whole number greater than 0,** e.g. 1, 2, 3, …​
* The order of the indexes do not matter e.g. `delete 1 2` and `delete 2 1` will both delete the first two contacts
* Spaces must be used to separate indexes e.g. `delete 12` will delete the 12th contact instead of the first two
contacts
</div>


**📘 Examples:**
* `list` followed by `delete 2` deletes the 2nd contact in the address book.
* `find Betsy` followed by `delete 1 2` deletes the 1st and 2nd contacts in the results of the `find` command.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Clearing all entries : `clear`

Deletes all contacts in the address book.

<div markdown="block" class="alert alert-warning">

:exclamation: **Caution:** <br>
* This command will permanently delete **all** contacts in the address book.
* Please think carefully before executing this **irreversible** action.
* No extra input is needed
</div>

**✏️ Format:**<br>
`clear`

**📘 Example:**
* `clear` deletes all contacts in CareLink.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## Display and Searching
-----

### Listing all contacts : `list`

Shows a list of all contacts in the address book.

**✏️ Format:**<br>
`list`

**📘 Example:**
* `list`<br>![result for 'find alex david'](images/list-success-screenshot.png)
Displays all volunteers and beneficiaries' contact details in the address book. At the panel below the command box, 
you can see the total number of contacts.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Locating contacts: `find`

Filters contacts whose fields match the keywords.

**✏️ Format:**<br>
`find [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS]`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:** <br>
* The search is <u>case-insensitive</u> e.g `n/hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `n/Hans Bo` will match `Bo Hans`.
 Multiple keywords per field are allowed. e.g. `n/Hans Bo` will match `Hans Lee` and `Bo Bae`.
* Only full words will be matched e.g. `n/Han` will not match `Hans`, `p/94628739` will not match `9462 8739`.

</div>


**📘 Examples:**
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
Displays all volunteers and beneficiaries' contact details named Alex and David in the address book. 
At the panel below the command box, you can see the total number of contacts.
<br><br>

* `find n/irfan bernice t/volunteer` returns `Bernice Lee` and `Irfan Ibrahim`, who are both `volunteers` <br>
    ![result for 'find irfan bernice t/volunteer'](images/findIrfanBerniceResult.png)
Displays all volunteers and beneficiaries' contact details named Alex and David in the address book.
At the panel below the command box, you can see the total number of contacts.
<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Sorting contacts: `sort`

Sorts contacts by the fields in the order of the attribute prefixes

**✏️ Format:**<br>
`sort [n/] [p/] [e/] [a/] [t/]`

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:**
* Order of sorting follows the natural order. e.g. 4th comes before 10th.
* Order of attribute prefixes defines the order of sorting.
* Sorting by **tags** will group the tags by **beneficiary** and **volunteer**.
* Calling sort after any command will sort the current displayed list.

</div>

**📘 Examples:**
* `sort n/` will return all contacts sorted by their names in alphabetical order
* `sort n/ p/` will return all contacts sorted by name then phone number
* `sort p/ n/` will return all contacts sorted by phone number then name

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## Advanced Features
-----

### Cycling between commands

Cycles between commands in the command history.

<div markdown="block" class="alert alert-info">

:information_source: **Important Note:**
* When you first press the `⬆ UP Key` or `⬇ DOWN Key`, the most recent command will show up
* If you are at the most recent command, pressing the `⬇ UP Key` will retrieve the oldest command
* Likewise, if you are at the oldest command, pressing the `⬇ DOWN Key` will retrieve the most recent command

**⌨ Usage:**<br>
* Press `⬆ UP Key`: Goes to the previous command in history

* Press `⬇ DOWN Key`: Goes to the next command in history

**📘 Example:**
* Command History List <br> ![Command History List](images/CommandHistoryList.png)
The current command in the command history is prefixed with a `*`.
<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Autocomplete

Provides suggestions to autocomplete your input text. Autocompletes command words and attribute prefixes like `n/`.

**⌨ Usage:**<br>
* Press `⇥ Tab`: Autocomplete with the suggestion that appears below command box.
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Press **Tab** to complete the prefix and start typing an attribute. When you finish it, press **Space** and the next attribute prefix will be suggested automatically.
</div>

**📘 Examples:**
* Autocomplete of command `delete` <br> ![result for autoComplete](images/autoComplete-success.png)
Typing the letter 'd' in an attempt to type the `delete` command will prompt autocomplete to suggest 'elete'. Pressing
`Tab` autocompletes the command rendering 'delete' in the command box.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Saving the data

CareLink data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

CareLink data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, CareLink will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the CareLink to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

------

### Exiting the program : `exit`

Exits the program.

**✏️ Format:**<br>
`exit`


<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

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

<details>
  <summary>Click to view all regions</summary>

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

</details>

### 🏠 Address

Address can be any non-blank string.

-   `21 Lower Kent Ridge Rd, Singapore`
-   `Block 123, #02-45 Clementi Ave 3`
-   `10 Dover Drive`
-   `Jurong West Street 42`
-   `Marina Bay Sands Tower 1`

### 🏷️ Tags

Tags are alphanumeric, at least one tag must be `volunteer` or `beneficiary`.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## FAQ
**Q**: Where are my data files stored?<br>
**A**: In a folder named `data` in the same directory of `addressbook.jar`, you can find the data file named `addressbook.json`.
Data is only stored locally, saved automatically whenever commands that modify data are executed.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file (`addressbook.json`) it creates with the original data file.
If the data folder does not exist yet, you can simply copy over the entire `data` folder from your previous CareLink home folder.

**Q**: What do I do if the command box says "Unknown command"?<br>
**A**: This means that the command you entered is not recognized by the application. Please ensure that you have typed the command correctly according to the formats specified in this user guide. You can type `help` to view the help window for reference.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## :warning:Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/REGION t/volunteer|beneficiary [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 r/woodlands t/volunteer`
**Clear** | `clear`
**Delete** | `delete INDEX [MORE_INDEXES]...`<br> e.g., `delete 1 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REGION] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [prefix/KEYWORDS]...`<br> e.g., `find n/James p/92813321`
**Sort** | `sort [prefix/]...` <br> e.g., `sort n/ p/`
**List** | `list`
**Help** | `help`

<br><br>

[▲ Back to Table of Contents](#table-of-contents)

------
