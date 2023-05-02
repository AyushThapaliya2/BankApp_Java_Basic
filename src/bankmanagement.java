
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class bankmanagement {
    Scanner s = new Scanner(System.in);
    static Connection con = connection.getConnection();
    String sql = "";


    public boolean FindLogin(String email, String password){

        try {
            boolean quit = false;
            int total_number = 0;
            int amount = 0;
            sql = "select * from customer WHERE cname=? and pass_code=?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, email);
            pt.setString(2, password);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                System.out.println("You are now logged-in-----\nBelow are your options: ");
                while (!quit) {
                    System.out.println("*******************************************");
                    System.out.println("Press 0 to exit");
                    System.out.println("press 1 to Deposit Money");
                    System.out.println("press 2 to Widthdraw Money");
                    System.out.println("press 3 to transfer Money");
                    System.out.println("*******************************************");
                    int choice = s.nextInt();

                    switch (choice) {
                        case 0:
                            System.out.println("Exititng to return to main option");
                            quit = true;
                            break;

                        case 1:
                            System.out.println("Amount you want to Deposite: ");
                            amount = s.nextInt();
                            if(amount< 0){
                                System.out.println("Invalid amount");
                            }
                            else{
                                sql ="SELECT balance FROM customer WHERE cname =?";
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setString(1,email);
                                ResultSet rst = ps.executeQuery();
                                if(rst.next()) {
                                    int balance = rst.getInt("balance");
                                    sql = "UPDATE customer SET balance = balance + ? WHERE cname =?";
                                    PreparedStatement pst = con.prepareStatement(sql);
                                    pst.setInt(1, amount);
                                    pst.setString(2, email);
                                    int row = pst.executeUpdate();
                                    if (row > 0) {
                                        System.out.println("your amount of: " + amount + " has been sucessfully deposited");
                                        System.out.println("Total amount: " + (balance + amount));


                                    }
                                }
                                else {
                                    System.out.println("Invalid customer email entered.");
                                }
                            }
                            break;

                        case 2:
                            System.out.println("Amount you want to widthdraw: ");
                            amount = s.nextInt();
                            if(amount< 0){
                                System.out.println("Invalid amount");
                                return false;
                            }
                            else{
                                sql ="SELECT balance FROM customer WHERE cname =?";
                                PreparedStatement psr = con.prepareStatement(sql);
                                psr.setString(1,email);
                                ResultSet rst = psr.executeQuery();
                                if(rst.next()){
                                    int balance = rst.getInt("balance");
                                    if(amount < balance){
                                        System.out.println("Insuffcient balance");
                                        return false;
                                    }
                                    else {
                                        sql = "UPDATE customer SET balance = balance - ? WHERE cname =?";
                                        PreparedStatement psl = con.prepareStatement(sql);
                                        psl.setInt(1, amount);
                                        psl.setString(2, email);
                                        int row = psl.executeUpdate();
                                        if (row > 0) {
                                            System.out.println("your amount of: " + amount + " has been sucessfully widthdrawn");
                                            System.out.println("Total amount: " + (balance- amount));
                                            return true;

                                        }
                                    }
                                }
                                else {
                                    System.out.println("Invalid customer email entered.");
                                    return false;
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Enter the amount you want to transfer: ");
                            int transferAmount = s.nextInt();
                            s.nextLine();
                            System.out.println("Enter the your Email for conformation: ");
                            String senderEmail = s.nextLine();
                            System.out.println("Enter the Reciever Email: ");
                            String ReceiverEmail = s.nextLine();
                            transferMoney(senderEmail,ReceiverEmail,transferAmount);
                            break;


                    }
                }
            }
        }
           catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Username Not Available!");
            }

        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean RegisterAcc(String email, String password) {

        try {
            sql = "INSERT INTO customer (cname,balance,pass_code) VALUES ('" + email + "',0,'" + password + "')";
            PreparedStatement pt = con.prepareStatement(sql);

            int rowInserted = pt.executeUpdate(sql);

            if (rowInserted > 0) {
                System.out.println("You are now registred in");
                return true;
            } else {
                System.out.println("failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void transferMoney(String senderEmail, String receiverEmail, int amount) throws SQLException {
        // Check if sender has enough balance
        int senderBalance = getBalance(senderEmail);
        if (senderBalance < amount) {
            System.out.println("Insufficient balance");
            return;
        }

        // Deduct amount from sender's account
        updateBalance(senderEmail, senderBalance - amount);

        // Add amount to receiver's account
        int receiverBalance = getBalance(receiverEmail);
        updateBalance(receiverEmail, receiverBalance + amount);

        System.out.println("Transfer successful");
    }

    public int getBalance(String email) throws SQLException {
        int balance = 0;
        String sql = "SELECT balance FROM customer WHERE cname = ?";
        PreparedStatement s = con.prepareStatement(sql);
        s.setString(1, email);
        ResultSet set = s.executeQuery();
        if (set.next()) {
            balance = set.getInt("balance");
        }
        return balance;
    }
    public void updateBalance(String email, int balance) throws SQLException {
        String sql = "UPDATE customer SET balance = ? WHERE cname = ?";
        PreparedStatement s = con.prepareStatement(sql);
        s.setInt(1, balance);
        s.setString(2, email);
        s.executeUpdate();
    }


}










