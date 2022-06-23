import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BankingApplication {

    static String back, pass, dataOfAccountInFile, name, number, passPart, numPart, accountNum, dataCheck, newPassInFile;
    static Scanner c = new Scanner(System.in);
    static int i, choice, balance, deposit, withdraw;
    static boolean noError = true, hasInvalidChar, dataCheckBool;
    static File f = new File("data.txt");
    static Scanner fileScanner;
    static FileWriter fw;

    public static void main(String[] args) throws IOException {
        startApp();
        // Statements in startApp() aren't in main to avoid using
        // String[] args when we need to use the content in another class
        // meaning that in OPP you can call startApp() instead of calling
        // main() and creating a new array to pass in String[] args argument
    }

    static void startApp() throws IOException {
        String rePass = "";
        boolean accDone = false;

        do {
            System.out.println("\n\nHome\n\nWelcome.\n");
            System.out.println("1. Sign in.");
            System.out.println("2. Create a New Account.");
            System.out.println("3. Exit (deleting data).");

            outerLoop6:
            {
                do { //label used to break the nested loops
                    choice = tryCatchInt(choice); // method to catch errors
                    if (choice == 1) {
                        signIn(); // "sign in" put in method because it's repeated
                        break;
                    } else if (choice == 2) {
                        System.out.print("Type \"back\" for Home.\nCreate username: ");

                        String[] invalidChar = {"&", ",", "%", "@", "*", ";", ":", "\\", "/", " "};

                        do {
                            hasInvalidChar = false;
                            name = c.next();
                            for (String st : invalidChar) {
                                if (name.contains(st)) {
                                    System.out.print("The username can't contain \"&\", \",\", \"%\", \"@\", \"*\", \";\", \":\"," +
                                            " \"\\\\\", \"/\" or space\n" +
                                            "please try again: ");
                                    hasInvalidChar = true;
                                    break;
                                }
                            }
                        } while (hasInvalidChar);
                        if (name.equalsIgnoreCase("back")) {
                            break;
                        }
                        System.out.print("Type \"0000\" for Home.\nCreate Account Number (unique number of 4 digits): ");
                        do {
                            do {
                                number = c.next();
                                if (number.equals("0000")) {
                                    break outerLoop6;
                                }
                                if (number.length() != 4 || !checkAccNumDigits()) {
                                    System.out.print("Type \"0000\" for Home.\nYou should enter exactly 4 digits, try again: ");
                                }
                            } while (number.length() != 4 || !checkAccNumDigits());

                            if (accExistInFile(number)) {
                                System.out.println("This Account Number is already used, try another one.");
                            }
                        } while (accExistInFile(number));

                        c.nextLine();
                        // the accountNum=c.nextInt(); reads just the number not the end
                        //of line or anything after the number, when we do pass=c.nextLine();
                        //after c.nextInt(); this reads the remainder of the line with the
                        //number on it not the user's input, so we place a c.nextLine to
                        //read the line's remainder so the pass=c.nextLine(); can do its job

                        System.out.print("Type \"back\" for Home.\nCreate Password: ");
                        outerLoop1:
                        {
                            do {
                                pass = c.nextLine();
                                if (pass.equalsIgnoreCase("back")) {
                                    break outerLoop1;
                                }
                                if (pass.length() < 6) {
                                    System.out.println("Your Password is too weak (at least 6 characters).");
                                    System.out.println("1. try again.");
                                    System.out.println("2. back to main menu.");

                                    choice = tryCatchInt(choice);

                                    c.nextLine();
                                    if (choice == 1) {
                                        System.out.print("Enter your Password: ");
                                        continue;
                                    } else if (choice == 2) {
                                        break outerLoop1;
                                    } else {
                                        System.out.print("Invalid input, please try again: ");
                                    }
                                }
                            } while (pass.length() < 6);
                            do {
                                System.out.print("Re-Enter Password: ");
                                rePass = c.nextLine();
                                if (!rePass.equals(pass)) {
                                    System.out.println("Wrong password.");
                                    System.out.println("1. try again.");
                                    System.out.println("2. back to main menu.");

                                    choice = tryCatchInt(choice);
                                    c.nextLine();
                                    if (choice == 1) {
                                        continue;
                                    } else if (choice == 2) {
                                        break outerLoop1;
                                    } else {
                                        System.out.print("Invalid input, please try again. ");
                                    }
                                }
                            } while (!rePass.equals(pass));
                            do {
                                System.out.print("Enter your deposit(Mimimum $5000): ");
                                balance = tryCatchInt(balance);

                                if (balance < 5000) {
                                    System.out.println("Your deposit must be $5000 or more.");
                                    System.out.println("1. try again.");
                                    System.out.println("2. back to main menu.");

                                    choice = tryCatchInt(choice);
                                    c.nextLine();
                                    if (choice == 1) {
                                        continue;
                                    } else if (choice == 2) {
                                        break outerLoop1;
                                    } else {
                                        System.out.print("Invalid input, please try again. ");
                                    }
                                } else {
                                    accDone = true; // this boolean is created to make sure every input is correct, then create that new account
                                }
                            } while (balance < 5000);
                            if (accDone) {
                                saveData();
                                fw.close();
                                i++;
                            }
                        }
                        accDone = false;
                        break;
                    } else if (choice == 3) {
                        System.out.println("Thank you for using our service....");
                        try {
                            fw.close(); // Closing the FileWriter
                        } catch (Exception e) {
                        }
                        System.exit(0);
                    } else {
                        System.out.print("Invalid option, please try again: ");
                        continue;
                    }
                } while (i >= 0);
            } //this infinite loop is necessary to make the user back to line 34 when choosing invalid option
        } while (true);
    }

    static void signIn() throws IOException {
        do {
            System.out.print("Type \"0000\" for Home.\nEnter Account Number (unique number of 4 digits): ");
            accountNum = c.next();
            if (accountNum.equals("0000")) {
                return; //this return to break out the method
            }

            if (!accExistInFile(accountNum)) {
                System.out.println("Account doesn't exist.");
                System.out.println("1. try again.");
                System.out.println("2. back.");
                do {
                    choice = tryCatchInt(choice);
                    if (choice == 1) {
                        break;
                    } else if (choice == 2) {
                        return;
                    } else {
                        System.out.print("Invalid option, try again: ");
                        continue;
                    }
                } while (i >= 0);
            }
        } while (!accExistInFile(accountNum));
        c.nextLine();
        System.out.print("Type \"back\" for Home.\nEnter Password: ");
        pass = c.nextLine();
        if (pass.equalsIgnoreCase("back")) {
            return;
        }

        outerLoop2:
        {
            do {
                if (passCorrectInFile(accountNum)) {
                    do {
                        System.out.println("\n\nWelcome, " + getNameFromFile() + ".\n");
                        System.out.println("1. Check balance.");
                        System.out.println("2. Deposit.");
                        System.out.println("3. Withdraw.");
                        System.out.println("4. Change your password.");
                        System.out.println("5. Sign out.");
                        System.out.println("6. Exit (deleting data).");

                        do {
                            choice = tryCatchInt(choice);

                            if (choice == 1) {
                                System.out.println("Your Balance: $" + getBalance() + ".\n");
                                back();
                                break;
                            } else if (choice == 2) {
                                deposit();
                                break;
                            } else if (choice == 3) {
                                withdraw();
                                break;
                            } else if (choice == 4) {
                                System.out.print("You need to enter your old password.\nEnter your old password: ");
                                c.nextLine();
                                pass = c.nextLine();
                                if (!passCorrectInFile(accountNum)) {
                                    do {
                                        System.out.println("Wrong password, enter it again.");
                                        pass = c.nextLine();
                                    } while (!passCorrectInFile(accountNum));
                                }
                                System.out.print("Enter your new password: ");

                                changePassInFile();

                                System.out.println("You have changed your password successfully.");
                                back();
                                break;
                            } else if (choice == 5) {
                                System.out.println("Signing out..\n\n");
                                break outerLoop2;
                            } else if (choice == 6) {
                                System.out.println("Thank you for using our service....");
                                System.exit(0);
                                break;
                            } else {
                                System.out.print("Invalid option, please try again: ");
                                continue;
                            }
                        } while (i >= 0); // Just another infinite loop
                    } while (true);
                } else {
                    System.out.println("Wrong password, enter it one more time.");
                    pass = c.nextLine();
                    if (!passCorrectInFile(accountNum)) {
                        System.out.println("Sorry, you entered incorrect password 2 times, you have to sign in again.");
                    }
                }
            } while (passCorrectInFile(accountNum));
        }
    }

    static void back() {
        do {
            System.out.print("Type \"y\" to back: ");
            back = c.next();
            if (!back.equalsIgnoreCase("y")) {
                System.out.println("Invalid input, please type it correctly.");
            }
        } while (!back.equalsIgnoreCase("y"));
    }

    static int tryCatchInt(int x) {
        do {
            noError = true;
            try {
                x = c.nextInt();
            } catch (Exception e) {
                System.out.print("Something went wrong, try again: ");
                noError = false;
                c.next(); // clear scanner wrong input
                continue;
            }
        } while (!noError);
        return x;
    }

    static boolean checkAccNumDigits() {
        if (number.matches("\\d\\d\\d\\d"))
            return true;
        else
            return false;
    }

    static boolean accExistInFile(String s) throws IOException {
        dataCheckBool = false;
        f.createNewFile();
        fileScanner = new Scanner(f);
        while (fileScanner.hasNextLine()) {
            dataCheck = fileScanner.nextLine();
            try { // To avoid length 0 exception
                if (s.equals(dataCheck.substring(0, 4))) {
                    dataOfAccountInFile = dataCheck;
                    dataCheckBool = true;
                }
            } catch (Exception e) {
            }
        }
        if (dataCheckBool) {
            return true;
        } else {
            return false;
        }
    }

    static boolean passCorrectInFile(String accountNumber) {
        passPart = dataOfAccountInFile.substring(dataOfAccountInFile.indexOf("|") + 1);
        numPart = dataOfAccountInFile.substring(0, 4);
        if (pass.equals(passPart) && accountNumber.equals(numPart)) return true;
        else return false;
    }

    static String getNameFromFile() {
        return dataOfAccountInFile.substring(5, dataOfAccountInFile.indexOf("&"));
    }

    static int getBalance() {
        return Integer.valueOf(dataOfAccountInFile.substring(dataOfAccountInFile.indexOf("&") + 1, dataOfAccountInFile.indexOf("|")));
    }

    static void changePassInFile() throws IOException {
        pass = c.nextLine();
        dataOfAccountInFile = accountNum + "," + getNameFromFile() + "&" + getBalance() + "|" + pass;
        fw = new FileWriter(f,true);
        fw.write(dataOfAccountInFile+"\n");
        fw.close();
    }

    static void withdraw() throws IOException {
        System.out.print("Enter the Withdraw: ");
        do {
            withdraw = tryCatchInt(withdraw);
            if (withdraw > getBalance()) {
                System.out.print("Not enough balance to Withdraw $" + withdraw + " from your account, try again: "
                        + "\nor type \"0\" to cancel Withdraw.");
            } else if (withdraw < 0) {
                System.out.print("Your Withdraw can't be negative! try again: "
                        + "\nor type \"0\" to cancel Withdraw.");
            } else if (withdraw == 0) {
                return;
            }
        } while (withdraw > getBalance() || withdraw < 0);
        dataOfAccountInFile = accountNum + "," + getNameFromFile() + "&" + (getBalance() - withdraw) + "|" + pass;
        fw = new FileWriter(f, true);
        fw.write(dataOfAccountInFile+"\n");
        fw.close();
        System.out.println("Now your Balance is $" + getBalance() + ".");

        back();
    }

    static void deposit() throws IOException {
        System.out.print("Enter the Deposit : ");
        do {
            deposit = tryCatchInt(deposit);
            if (deposit < 0) {
                System.out.print("Your Deposit can't be negative! try again."
                        + "\nor type \"0\" to cancel Deposit: ");
            } else if (deposit == 0) {
                return;
            }
        } while (deposit < 0);
        dataOfAccountInFile = accountNum + "," + getNameFromFile() + "&" + (getBalance() + deposit) + "|" + pass ;
        fw = new FileWriter(f, true);
        fw.write(dataOfAccountInFile+"\n");
        fw.close();
        System.out.println("Now your Balance is: $" + getBalance() + ".");
        back();
    }

    static void saveData() throws IOException {
        fw = new FileWriter(f, true);
        fw.write(number + "," + name + "&" + balance + "|" + pass+"\n");
        fw.close();
    }
}