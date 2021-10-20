import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.Writer;

//README: This program takes plain-text EveryDollar transactions, ripped from the website, and converts them to CSV.
//        You must manually copy them to a "transactions.txt" file in the same folder as this program. To get them,
//        open ALL EveryDollar transactions in the web app, open the inspection tab in Chrome, and copy the body.
//        From there, save the body as HTML and open it. A plain text list of transactions will be available here.
//        From there, save the transactions as described above, run this and it will convert it to CSV. 

public class everydollar_transaction_organizer { 

   final static String cleanedFileName =  "transactions-cleaned.txt";
   final static int maxTransactions = 1000;

   public static void main (String[] args) throws IOException {          
      cleanTransactionText();
      Transaction[] transactionList = extractTransactionInfoFromText();
      String CSVoutput = createCSVOutput(transactionList);
      writeToCSVFile(CSVoutput);
   }
   
   public static String createCSVOutput (Transaction[] transactionList) {
      Transaction transaction;
      String date;
      String notes;
      String category;
      String amount;
      String output;
      String headers[] = {"Date","Notes","Category","Amounts"};
      String CSVOutput = createCSVLine(headers);
      
      for (int i = 0; i < maxTransactions; i++) {
         transaction = transactionList[i];
         date = transaction.getDate();
         if (date.equals("*ENDFILE*")) {
            break;
         }
         notes = transaction.getNotes();
         category = transaction.getCategory();
         amount = transaction.getAmount();
         String input[] = {date, notes, category, amount};
         output = createCSVLine(input);
         CSVOutput = CSVOutput + "\n" + output;
      }
      return CSVOutput;
   }
   
   public static String createCSVLine (String[] input) {
      String output = input[0];
      for (int i = 1; i < input.length; i++) {
         output += "," + input[i];
      }
      return output;
   }
   
   public static Transaction[] extractTransactionInfoFromText () throws IOException {
         Transaction[] transactionList = new Transaction[maxTransactions];
         Scanner scan = new Scanner(new File(cleanedFileName));
         String[] rawTransactionInfo = {null, null, null, null, null};
         String[] rawTransactionInfoBlank = {null, null, null, null, null};
         String currentLine;
         
         int lineCount = 0;
         for (int transactionNumber = 0; transactionNumber < maxTransactions; transactionNumber++) {
            currentLine = scan.nextLine();
            if (currentLine.equals("*ENDFILE*")) {
               rawTransactionInfo[0] = "*ENDFILE*";
               transactionList[transactionNumber] = createTransactionObject(rawTransactionInfo);
               break;
            }
            rawTransactionInfo[0] = currentLine;
            lineCount = 1;
            while (!currentLine.equals("*---*")) {
               currentLine = scan.nextLine();
               rawTransactionInfo[lineCount] = currentLine;
               lineCount++;
            }
            transactionList[transactionNumber] = createTransactionObject(rawTransactionInfo);
            rawTransactionInfo = rawTransactionInfoBlank;
         }
         return transactionList;
      }
   
   public static Transaction createTransactionObject (String[] transactionInfo) {
      Transaction transactionOutput  = new Transaction();
      transactionOutput.setDate(transactionInfo[0]);
      transactionOutput.setNotes(transactionInfo[1]);
      transactionOutput.setCategory(transactionInfo[2]);
      String formattedAmount = formatMoney(transactionInfo[3]);
      transactionOutput.setAmount(formattedAmount);    
      return transactionOutput;
   }
   
   public static String formatMoney (String moneyInput) {
      String moneyOutputString;
      char sign = moneyInput.charAt(0);
      if (sign == '+') {
         moneyOutputString = "";
      }
      else {
         moneyOutputString = "-";
      }
      
      for (int i = 2; i < moneyInput.length(); i++) {
         moneyOutputString = moneyOutputString + moneyInput.charAt(i);
      }
      return moneyOutputString;
   }
   
   public static void cleanTransactionText () throws IOException {   
      boolean fileAlreadyExists = false;       
      try {
         File myObj = new File(cleanedFileName);
         if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
         } 
         else {
            System.out.println("File already exists.");
            fileAlreadyExists = true;
         }
      } 
      catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
      if (!fileAlreadyExists) {
         Scanner scan = new Scanner(new File("transactions.txt"));
         String text;
         while (scan.hasNextLine()) {
            text = cleanLine(scan.nextLine());
            if (text != null) {
               writeToCleanFile(text);
            }
         }
         writeToCleanFile("*ENDFILE*");  
         System.out.println("Transactions added to " + cleanedFileName);
      }
   }
   
   public static String cleanLine(String input) {
      String months[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
      String garbage[] = {"Delete", " Delete", "Edit"};
      
      for (String month: months) {
         if (input.equals(month)) {
            return null;
         }
      }
      for (String misc: garbage) {
         if (input.equals(misc)) {
            return null;
         }
      }
      if (input.equals("Split")) {
         return "*---*";
      }
      return input;
   }
   
   public static void writeToCleanFile(String input) {
      try {
         String fileName = cleanedFileName;
         Writer output;
         output = new BufferedWriter(new FileWriter(fileName, true));
         output.append(input + "\n");
         output.close();
      }
      catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
   
   public static void writeToCSVFile(String CSVInput) throws IOException {
      String fileName = "every_dollar_transactions.csv";
      try {
         File myObj = new File(fileName);
         if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
         }
         else {
            System.out.println("File already exists.");
         }
      }
      catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
      
      try {
         FileWriter myWriter = new FileWriter(fileName);
         myWriter.write(CSVInput);
         myWriter.close();
         System.out.println("Successfully wrote transactions to csv.");
      }
      catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
}

class Transaction {
   private String date = "nullDate";
   private String notes = "nullNotes";
   private String category = "nullCategory";
   private String amount = "nullAmount";
   
   public String getDate() {
      return date;
   }
   
   public void setDate(String input) {
      this.date = input;
   }
   
   public String getNotes() {
      return notes;
   }
   
   public void setNotes(String input) {
      this.notes = input;
   }
   
   public String getCategory() {
      return category;
   }
   
   public void setCategory(String input) {
      this.category = input;
   }
   
   public String getAmount() {
      return amount;
   }
   
   public void setAmount(String input) {
      this.amount = input;
   }
}