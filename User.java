import java.nio.charset.Charset;
import java.util.ArrayList;
import Activity.java;
import java.util.Scanner;
public class User {
    String name;
    String email;
    String pwd;
    ArrayList<Activity> activities;
    Integer wallet;

    public User(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPwd() {
        Scanner old = new Scanner(System.in, Charset.defaultCharset());
        System.out.println("If you want to change your password, write first your old password: ");
        String oldPwd = old.nextLine();
        if (oldPwd == this.pwd) {
            Scanner newp = new Scanner(System.in);
            System.out.println("That is your correct password, please, introduce the new one: ");
            String newPwd = newp.nextLine();
            this.pwd = newPwd;
        } else {
            System.out.println("Incorrect password, exiting...");
        }
    }
}
