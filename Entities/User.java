package Entities;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String name;
    private String email;
    private String pwd;
    private ArrayList<Activity> activityHist;
    private int wallet;

    public User(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.wallet = 0;
        this.activityHist = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("If you want to change your password, write first your old password: ");
        String oldPwd = sc.nextLine();

        if (pwd != null && pwd.equals(oldPwd)) {
            System.out.println("Correct password, please enter the new one: ");
            String newPwd = sc.nextLine();
            this.pwd = newPwd;
        } else {
            System.out.println("Incorrect password, exiting...");
        }
    }

    public void addActivity(Activity activity) {
        activityHist.add(activity);
        wallet += activity.getAwardedPoints();
    }

    public boolean buyDiscountTicket(DiscountTicket d) {
        if (wallet >= d.getRequiredPoints()) {
            wallet -= d.getRequiredPoints();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " - Points: " + wallet;
    }
}
