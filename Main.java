package Entidades;

import java.util.Scanner;
import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        // --- Inicialización ---
        AuthManager auth = new AuthManager();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null; // Para almacenar el usuario actual
        boolean appRunning = true;

        // ** Simulamos algunos datos iniciales (sin mensajes en consola) **
        auth.registerUser("juan", "1234");
        auth.registerUser("maria", "abcd");
        auth.registerUser("usuario0", "pass0");
        auth.registerUser("usuario500", "pass500");
        auth.registerUser("usuarioInf", "passInf");

        // Datos para el menú
        Discount d1 = new Discount("EcoStore", "10% en productos eco", 40);
        Discount d2 = new Discount("GreenEnergy", "5€ de descuento", 100);
        Activity act1 = new Activity("Concierto Pop", "Ticket para concierto pop", 50);
        Activity act2 = new Activity("Teatro Clásico", "Ticket para obra de teatro", 40);
        Activity act3 = new Activity("Evento Deportivo", "Ticket para partido", 30);

        // Ajustes de puntos para los usuarios de ejemplo:
        User u500 = auth.login("usuario500", "pass500");
        if (u500 != null && u500.getPoints() == 0) {
            u500.addActivity(new Activity("Ajuste Inicial", "Puntos iniciales", 500));
        }
        User uInf = auth.login("usuarioInf", "passInf");
        if (uInf != null && uInf.getPoints() == 0) {
            try {
                // Usamos Reflection para establecer puntos altos (simula infinito)
                Field pointsField = User.class.getDeclaredField("points");
                pointsField.setAccessible(true);
                pointsField.setInt(uInf, 1_000_000); // 1 Millón es suficiente
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                // Silencioso
            }
        }
        // *******************************************************


        System.out.println("Iniciando aplicación..."); // Mensaje inicial


        // --- Bucle Principal de la Aplicación ---
        while (appRunning) {

            // --- Menú de Autenticación (Cuando NO hay usuario logeado) ---
            if (currentUser == null) {
                System.out.print("\nCrea cuenta (CC), Inicia sesión (IS) o Salir (S): ");
                String initialChoice = scanner.nextLine().toUpperCase();

                String username, password;

                switch (initialChoice) {
                    case "CC":
                        // Opción: Crear Cuenta
                        System.out.print("Escribe el nombre de usuario: ");
                        username = scanner.nextLine();
                        System.out.print("Escribe la contraseña: ");
                        password = scanner.nextLine();

                        if (auth.login(username, null) == null) {
                            auth.registerUser(username, password);
                            System.out.println("¡Cuenta creada con éxito! Por favor, inicia sesión.");
                        } else {
                            System.out.println("Error: El usuario ya existe.");
                        }
                        break;

                    case "IS":
                        // Opción: Iniciar Sesión
                        System.out.print("nombre de usuario: ");
                        username = scanner.nextLine();
                        System.out.print("password: ");
                        password = scanner.nextLine();

                        currentUser = auth.login(username, password);

                        if (currentUser == null) {
                            System.out.println("Error: Usuario o contraseña incorrectos. Inténtalo de nuevo.");
                        } else {
                            System.out.println("bienvenido " + currentUser.getUsername() + "! escribe help para ver qué puedes hacer");
                        }
                        break;

                    case "S":
                        // Opción: Salir completamente de la aplicación
                        appRunning = false;
                        System.out.println("Cerrando aplicación. ¡Hasta pronto!");
                        break;

                    default:
                        System.out.println("Opción no reconocida. Por favor, escribe 'CC', 'IS' o 'S'.");
                        break;
                }

            } else {
                // --- Menú Principal (Cuando SÍ hay usuario logeado) ---
                System.out.print("\n(Escribe una opción o 'help'): ");
                String choice = scanner.nextLine().toUpperCase();

                switch (choice) {
                    case "HELP":
                        System.out.println("\"Ver perfil (VP), Consultar Cartera (CC), Comprar Ticket (BT), Hacer Actividad (DA), Cerrar Sesión (S)\"");
                        break;

                    case "VP":
                        // Ver perfil
                        System.out.println("\n--- PERFIL DE USUARIO ---");
                        System.out.println("Usuario: " + currentUser.getName());
                        System.out.println("Puntos disponibles: " + currentUser.getPoints());
                        System.out.println("Actividades realizadas: " + currentUser.getCompletedActivities().size());
                        break;

                    case "CC":
                        // Consultar Cartera
                        System.out.println("\n--- CONSULTAR CARTERA ---");
                        System.out.println("Tienes " + currentUser.getPoints() + " puntos disponibles.");
                        System.out.println("\nDescuentos disponibles para canjear (Puntos requeridos):");
                        System.out.println("1. " + d1.getName() + " - " + d1.getDescription() + " (" + d1.getRequiredPoints() + " Pts)");
                        System.out.println("2. " + d2.getName() + " - " + d2.getDescription() + " (" + d2.getRequiredPoints() + " Pts)");

                        System.out.print("\n¿Deseas canjear algún descuento? (Introduce número o 'N' para no): ");
                        String canjeoChoice = scanner.nextLine();

                        Discount discountToRedeem = null;
                        if (canjeoChoice.equals("1")) {
                            discountToRedeem = d1;
                        } else if (canjeoChoice.equals("2")) {
                            discountToRedeem = d2;
                        }

                        if (discountToRedeem != null) {
                            if (currentUser.redeemPoints(discountToRedeem)) {
                                System.out.println("¡Has canjeado el descuento '" + discountToRedeem.getName() + "'!");
                                System.out.println("Saldo restante: " + currentUser.getPoints() + " puntos.");
                            } else {
                                System.out.println("No tienes suficientes puntos para canjear: " + discountToRedeem.getName());
                                System.out.println("Puntos requeridos: " + discountToRedeem.getRequiredPoints() + ". Tienes: " + currentUser.getPoints());
                            }
                        }
                        break;

                    case "BT":
                        // Comprar Ticket
                        System.out.println("\nTickets disponibles:");
                        System.out.println("1. " + act1.getName() + " - Coste: " + act1.getAwardedPoints() + " puntos");
                        System.out.println("2. " + act2.getName() + " - Coste: " + act2.getAwardedPoints() + " puntos");
                        System.out.println("3. " + act3.getName() + " - Coste: " + act3.getAwardedPoints() + " puntos");
                        System.out.println("Tu saldo actual es de: " + currentUser.getPoints() + " puntos.");


                        System.out.print("\neliges una (por ejemplo escribiendo un número): ");
                        String ticketChoice = scanner.nextLine();

                        Activity selectedTicket = null;
                        if (ticketChoice.equals("1")) {
                            selectedTicket = act1;
                        } else if (ticketChoice.equals("2")) {
                            selectedTicket = act2;
                        } else if (ticketChoice.equals("3")) {
                            selectedTicket = act3;
                        }

                        if (selectedTicket != null) {
                            int cost = selectedTicket.getAwardedPoints();
                            // Usamos Discount temporal para restar los puntos
                            Discount costDiscount = new Discount("Compra Ticket", selectedTicket.getName(), cost);

                            if (currentUser.redeemPoints(costDiscount)) {
                                System.out.println("¡Has comprado 1x " + selectedTicket.getName() + " por " + cost + " puntos!");
                                System.out.println("Saldo restante: " + currentUser.getPoints() + " puntos.");
                                System.out.println("¡Gracias!");
                            } else {
                                System.out.println("Error: No tienes suficientes puntos para comprar este ticket. Coste: " + cost + ". Tienes: " + currentUser.getPoints());
                            }
                        } else {
                            System.out.println("Opción de ticket no válida.");
                        }
                        break;

                    case "DA":
                        // Hacer Actividad (ganar puntos)
                        System.out.println("\n--- HACER ACTIVIDAD ---");
                        System.out.println("Actividades que te dan puntos:");
                        System.out.println("1. Donación - Ganas 10 puntos");
                        System.out.println("2. Rellenar Encuesta - Ganas 5 puntos");
                        System.out.print("Selecciona una para ganar puntos (1 o 2): ");
                        String activityChoice = scanner.nextLine();

                        Activity earnedActivity = null;
                        if (activityChoice.equals("1")) {
                            earnedActivity = new Activity("Donación", "Donación a caridad", 10);
                        } else if (activityChoice.equals("2")) {
                            earnedActivity = new Activity("Encuesta", "Encuesta de opinión", 5);
                        }

                        if (earnedActivity != null) {
                            currentUser.addActivity(earnedActivity);
                            System.out.println("¡Has completado '" + earnedActivity.getName() + "' y has ganado " + earnedActivity.getAwardedPoints() + " puntos!");
                            System.out.println("Tu nuevo total de puntos es: " + currentUser.getPoints());
                        } else {
                            System.out.println("Opción de actividad no válida.");
                        }
                        break;

                    case "S":
                        // Cerrar Sesión (vuelve al menú de autenticación)
                        System.out.println("Cerrando sesión de " + currentUser.getName() + "...");
                        currentUser = null;
                        break;

                    default:
                        System.out.println("Opción no reconocida. Escribe 'help' para ver las opciones.");
                        break;
                }
            }
        }

        scanner.close();
    }
}