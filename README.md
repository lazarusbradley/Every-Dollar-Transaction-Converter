# EveryDollarFreeTransactionConverter
Converts EveryDollar transactions to CSV without needing a Ramsey+ subscription. You will need to manually copy them from the website (see below for instructions), but then this program cleans up the input and stores it automatically in CSV format.

HOW TO USE
* [OBTAINING RAW TRANSACTION TEXT]
* [must be done on a desktop Browser. May be possible in other browsers, but this was done in the Chromium based Edge browser]
* Go to your EveryDollar home page, and click "Transactions" in the top right.
* Go to the "Tracked" transactions tab within the Transactions side bar.
* Scroll to bottom of transaction list, and press "Load [month] Transactions". Repeat until all are visible.
* Right click anywhere on page, and press "Inspect" on popout menu.
* In the Elements tab, right click on the line with the text "\<body data-analytics-logged-in="true"\>", and select "Copy Element". If you can't find this line, press Ctrl+F in the Elements text window, and type it out.
* Anywhere in your local file directory, create a html file, and open it in Notepad.
* Copy-paste the html elements into this file.
* Then, close Notepad and open the html file in Edge.
* On this page, search for "New" (press Ctrl + F, and type "New"). This should bring you to the plain text list of transactions.
* Starting from the line under the search bar, which should be a month ("October" it says for me), select all text until the final "Delete" text in the list
* Create a new text file titled "transactions.txt" in the same folder as the Java program from this repository.
* Copy paste the transaction data into this file (transactions.txt), and save this file.

* [PROCESSING RAW TRANSACTION TEXT INTO CSV]
* Now, run the Java file. (You will need Java installed. You can run it through the command line I guess, but I just used JGrasp to run it through there).
* You should now have a CSV file with all your transactions logged in it.
