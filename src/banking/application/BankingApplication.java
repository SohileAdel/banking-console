package banking.application;

import java.util.*;

public class BankingApplication {
    
    static String back="", pass="",toStrn="";
    static Scanner c=new Scanner(System.in);
    static String[] passList=new String[50], userNameList=new String[50];
    static int numOfAccFound=0, i=0, choice=0, deposit=0, withdraw=0, backFrom=0, accountNum=0;
    static int[] accNumList=new int[50], balance=new int[50];;
    static boolean accountExist=false, noError=true;
    
    public static void main(String[] args) {
        
        String rePass="";
        boolean used=false, accDone=false;
        
        do{
            System.out.println("\n\nHome\n\nWelcome.\n");
            System.out.println("1. Sign in.");
            System.out.println("2. Create a New Account (You can create maximum number of 50 accounts).");
            System.out.println("3. Exit (deleting data).");
            
            outerLoop6:{do{ //label used to break the nested loops
                choice=tryCatchInt(choice); // method to catch errors
                if(choice==1){
                    signIn(); // "sign in" put in method because it's repeated and has a lot of code 
                    break;
                }
                
                
                else if(choice==2){
                    System.out.print("Type \"back\" for Home.\nCreate username: ");
                    userNameList[i]=c.next();
                    if(userNameList[i].equalsIgnoreCase("back")){
                        break;
                    }
                    System.out.print("Type \"0000\" for Home.\nCreate Account Number (unique number of 4 digits): ");
                    do{
                        used=false;

                        do{
                            accNumList[i]=tryCatchInt(accNumList[i]);
                            if(accNumList[i]==000){
                                break outerLoop6;
                            }
                            toStrn=""+accNumList[i];
                            if(toStrn.length()!=4){
                                System.out.print("Type \"0000\" for Home.\nYou should enter exactly 4 digits, try again: ");
                           }
                        }while(toStrn.length()!=4);

                        for(int j=0;j<i;j++){
                            if(accNumList[i]==accNumList[j]){
                                used=true;
                                System.out.println("This Account Number is already used, try another one.");
                                break;
                            }
                        }
                    } while(used);
                    
                    c.nextLine(); 
                    // the accountNum=c.nextInt(); reads just the number not the end 
                    //of line or anything after the number, when we do pass=c.nextLine(); 
                    //after c.nextInt(); this reads the remainder of the line with the
                    //number on it not the user's input, so we place a c.nextLine to 
                    //read the line's remainder so the pass=c.nextLine(); can do its job
                    
                    System.out.print("Type \"back\" for Home.\nCreate Password: ");
                    outerLoop1:{do{
                        passList[i]=c.nextLine();
                        if(passList[i].equalsIgnoreCase("back")){
                            break outerLoop1;
                        }
                        if(passList[i].length()<6){
                            System.out.println("Your Password is too weak (at least 6 characters).");
                            System.out.println("1. try again.");
                            System.out.println("2. back to main menu.");
                            
                            choice=tryCatchInt(choice);
                            
                            c.nextLine();
                            if(choice==1){
                                continue;
                            }
                            else if(choice==2){
                                break outerLoop1;
                            }
                            else{
                                System.out.print("Invalid input, please try again: ");
                            }
                        }
                    }while(passList[i].length()<6);
                    do{
                        System.out.print("Re-Enter Password: ");
                        rePass=c.nextLine();
                        if(!rePass.equals(passList[i])){
                            System.out.println("Wrong password.");
                            System.out.println("1. try again.");
                            System.out.println("2. back to main menu.");
                            
                            choice=tryCatchInt(choice);
                            c.nextLine();
                            if(choice==1){
                                continue;
                            }
                            else if(choice==2){
                                break outerLoop1;
                            }
                            else{
                                System.out.print("Invalid input, please try again. ");
                            }
                        }
                    } while(!rePass.equals(passList[i]));
                    do{
                        System.out.print("Enter your deposit(Mimimum $5000): ");
                        
                        balance[i]=tryCatchInt(balance[i]);
                        
                        if(balance[i]<5000){
                            System.out.println("Your deposit must be $5000 or more.");
                            System.out.println("1. try again.");
                            System.out.println("2. back to main menu.");
                            
                            choice=tryCatchInt(choice);
                            c.nextLine();
                            if(choice==1){
                                continue;
                            }
                            else if(choice==2){
                                break outerLoop1;
                            }
                            else{
                                System.out.print("Invalid input, please try again. ");
                            }
                        }
                        else{
                            accDone=true; // this boolean is created to make sure every input is correct, then create that new account
                        }
                    } while(balance[i]<5000);
                    if(accDone){
                        i++;
                    }}
                    accDone=false;
                    break;
                }

                else if(choice==3){
                    System.out.println("Thank you for using our service....");
                    System.exit(0);
                    break;
                }
                else{
                    System.out.print("Invalid option, please try again: ");
                    continue;
                }
            }while(i>=0);} //this infinite loop is necessary to make the user back to line 28 instead of 22 when choosing invalid option
        } while(i<50); //maximum of 50 accounts
        
        do{
            System.out.println("\n\nWelcome\n");
            System.out.println("1. Sign in.");
            System.out.println("2. Exit (deleting data).");
            System.out.println("attention: You can't create a new account, you have reached the maximum number of accounts(50).");
            do{
                choice=tryCatchInt(choice);
               
                if(choice==1){
                    signIn();
                    break;
                }
                else if(choice==2){
                    System.out.println("Thank you for using our service....");
                    System.exit(0);
                    break;
                }
                else{
                    System.out.print("Invalid option, try again: ");
                    continue;
                }
            }while(i>=0);
        }while(true);   
    }
    
    public static void signIn(){
        int count=1;
        outerLoop5:{do{
            accountExist=false;
            System.out.print("Type \"0000\" for Home.\nEnter Account Number (unique number of 4 digits): ");
            do{
                accountNum=tryCatchInt(accountNum);
                if(accountNum==0000){
                    return; //this return to break out the method
                }
                toStrn=""+accountNum;
                if(toStrn.length()!=4){
                    System.out.print("You should enter exactly 4 digits, try again: ");
                }
            }while(toStrn.length()!=4);
            
            for(int j=0;j<=i;j++){
                if(accountNum==accNumList[j]){
                    accountExist=true;
                    numOfAccFound=j;
                }
            }
            if(!accountExist){
                System.out.println("Account doesn't exist.");
                System.out.println("1. try again.");
                System.out.println("2. back.");
                do{
                    choice=tryCatchInt(choice);
                    if(choice==1){
                        continue;
                    }
                    else if(choice==2){
                        break outerLoop5;
                    }
                    else{
                        System.out.print("Invalid option, try again: ");
                        continue;
                    }
                }while(i>=0);
            }
        }while(!accountExist);}
        c.nextLine();
        System.out.print("Type \"back\" for Home.\nEnter Password: ");
        pass=c.nextLine();
        if(pass.equalsIgnoreCase("back")){
            return;
        }

        outerLoop2:{do{
            if(pass.equals(passList[numOfAccFound])){
                do{
                    System.out.println("\n\nWelcome, "+userNameList[numOfAccFound]+".\n");
                    System.out.println("1. Check balance.");
                    System.out.println("2. Deposit.");
                    System.out.println("3. Withdraw.");
                    System.out.println("4. Change your password.");
                    System.out.println("5. Sign out.");
                    System.out.println("6. Exit (deleting data).");
                    
                    do{
                        choice=tryCatchInt(choice);

                        if(choice==1){
                            System.out.println("Your Balance: $"+balance[numOfAccFound]+".\n");
                            back();
                            break;
                        }
                        else if(choice==2){
                            outerLoop3:{
                                System.out.print("Enter the Deposit : ");
                                do{
                                    deposit=tryCatchInt(deposit);
                                    if(deposit<0){
                                        System.out.print("Your Deposit can't be negative! try again."
                                                + "\nor type \"0\" to cancel Withdraw: ");
                                    }
                                    else if(deposit==0){
                                        break outerLoop3;
                                    }
                                }while(deposit<0);    
                                balance[numOfAccFound]+=deposit;
                                System.out.println("Now your Balance is: $"+(balance[numOfAccFound]+"."));
                                back();
                            }
                            break;
                        }
                        else if(choice==3){
                            outerLoop4:{
                                System.out.print("Enter the Withdraw: ");
                                do{
                                    withdraw=tryCatchInt(withdraw);
                                    if(withdraw>balance[numOfAccFound]){
                                        System.out.print("Not enough balance to Withdraw $"+withdraw+" from your account, try again: "
                                                + "\nor type \"0\" to cancel Withdraw.");
                                    }
                                    else if(withdraw<0){
                                        System.out.print("Your Withdraw can't be negative! try again: "
                                                + "\nor type \"0\" to cancel Withdraw.");
                                    }
                                    else if(withdraw==0){
                                        break outerLoop4;
                                    }
                                }while(withdraw>balance[numOfAccFound] || withdraw<0);
                                balance[numOfAccFound]-=withdraw;
                                System.out.println("Now your Balance is $"+(balance[numOfAccFound]+"."));
                                back();
                            }
                            break;
                        }
                        else if(choice==4){
                            System.out.print("You need to enter your old password.\nEnter your old password: ");
                            c.nextLine();
                            pass=c.nextLine();
                            if(!pass.equals(passList[numOfAccFound])){
                                do{
                                    System.out.println("Wrong password, enter it again.");
                                    pass=c.nextLine();
                                }while(!pass.equals(passList[numOfAccFound]));
                            }
                            System.out.print("Enter your new password: ");
                            passList[numOfAccFound]=c.nextLine();
                            System.out.println("You have changed your password successfully.");
                            back();
                            break;
                        }
                        else if(choice==5){
                            System.out.println("Signing out..\n\n");
                            break outerLoop2;
                        }
                        else if(choice==6){
                            System.out.println("Thank you for using our service....");
                            System.exit(0);
                            break;
                        }
                        else{
                            System.out.print("Invalid option, please try again: ");
                            continue;
                        }
                    }while(i>=0);
                }while(true);
            }

            else{
                System.out.println("Wrong password, enter it one more time.");
                pass=c.nextLine();
                if(!pass.equals(passList[numOfAccFound])){
                    System.out.println("Sorry, you entered incorrect password 2 times, you have to sign in again.");
                }
            }
        }while(pass.equals(passList[numOfAccFound]));}

        accountExist=false;
    }
    
    public static void back(){
        String[] yess = {"Y","y"};
        do{
            System.out.print("Type \"y\" to back: ");
            back=c.next();
            if(!back.equalsIgnoreCase("y")){
                System.out.println("Invalid input, please type it correctly.");
            }
        }while(!(Arrays.toString(yess)).contains(back));
    }
    public static int tryCatchInt(int x){
        do{
            noError=true;
            try{
                x=c.nextInt();
            }catch(Exception e){
                System.out.print("Something went wrong, try again: ");
                noError=false;
                c.next(); // clear scanner wrong input
                continue; 
            }
        }while(!noError);
        return x;
    }    
}