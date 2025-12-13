//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package Entities;

public class Main {

    public static void main(String[] args) {

        // --- Create AuthManager ---
        AuthManager auth = new AuthManager();

        System.out.println("=== REGISTRATION TEST ===");

        boolean r1 = auth.registerUser("Juan", "juan96@gmail.com", "1234");
        boolean r2 = auth.registerUser("Maria", "maria69@gmail.com", "abcd");
        boolean r3 = auth.registerUser("Juan2", "juan96@gmail.com", "anotherKey"); // should fail (same email)

        System.out.println("Registration Juan: " + r1);
        System.out.println("Registration Maria: " + r2);
        System.out.println("Duplicate email registration: " + r3);

        System.out.println("\n=== LOGIN TEST ===");

        User user1 = auth.login("juan96@gmail.com", "1234");
        User userFail = auth.login("juan96@gmail.com", "1111");

        System.out.println("Successful login Juan: " + (user1 != null));
        System.out.println("Failed login wrong password: " + (userFail != null));

        if (user1 == null) {
            System.out.println("Login failed, cannot continue tests.");
            return;
        }

        System.out.println("\n=== ACTIVITIES TEST ===");

        // Create activities
        Activity act1 = new Activity("Recycling", "Bring plastic to a recycling point", 20);
        Activity act2 = new Activity("Sustainable transport", "Go to work by bike", 30);

        // Add activities
        user1.addActivity(act1);
        user1.addActivity(act2);

        System.out.println("Activities completed by " + user1.getName() + ":");
        for (Activity a : user1.getActivityHist()) {
            System.out.println(" - " + a.getName() + ": +" + a.getAwardedPoints() + " points");
        }

        System.out.println("Total points: " + user1.getWallet());

        System.out.println("\n=== DISCOUNTS TEST ===");

        DiscountTicket d1 = new DiscountTicket("EcoStore", "10% off eco products", 40);
        DiscountTicket d2 = new DiscountTicket("GreenEnergy", "5€ discount", 100);

        boolean redeem1 = user1.buyDiscountTicket(d1);
        boolean redeem2 = user1.buyDiscountTicket(d2);

        System.out.println("Redeem EcoStore discount: " + redeem1);
        System.out.println("Redeem GreenEnergy discount: " + redeem2);

        System.out.println("Remaining points: " + user1.getWallet());

        System.out.println("\n=== FINAL USER STATE ===");
        System.out.println(user1);
    }
}


