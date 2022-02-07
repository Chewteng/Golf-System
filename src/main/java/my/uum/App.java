package my.uum;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

/**
 * This class is used to connect to the database and run the Telegram bot API and register bot.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class App {

    /**
     * This is the main method that connects to the database, telegram bot API and register bot.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
        dbConnection.connect();

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new telegramBot());

        App.Menu();

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is to display the menu for managing the records of hole, golfer and tournament table database as well as logging out from the system.
     */
    public static void Menu(){
        boolean x = false;
        do {

            System.out.println("\n\033[1m*******************************************\033[0m ");
            System.out.println("\t \033[1mWELCOME TO STIW3054-A202 GOAL DIGGER\033[0m");
            System.out.println("\033[1m*******************************************\033[0m ");
            System.out.println("Which do you want to manage? : ");
            System.out.println("1. Golf Hole");
            System.out.println("2. Golfer Info");
            System.out.println("3. Tournaments Info ");
            System.out.println("4. Quit ");
            System.out.print("--> Please select: ");
            Scanner sc = new Scanner(System.in);
            int select = sc.nextInt();

            ManageHole hole = new ManageHole();
            ManageGolfer golfer = new ManageGolfer();
            ManageTournament tournament = new ManageTournament();

                switch (select) {
                    case 1:
                        System.out.println("\n*** \033[1mGolf Hole Management ***\033[0m");
                        System.out.println("You are allowed to manage info via: ");
                        System.out.println("1. Create  ");
                        System.out.println("2. Read ");
                        System.out.println("3. Update ");
                        System.out.println("4. Delete ");
                        System.out.print("Please select your action: ");
                        Scanner sc1 = new Scanner(System.in);
                        if (sc1.hasNextInt()) {
                            int selectManagement1 = sc1.nextInt();
                            if (selectManagement1 == 1) {
                                hole.createHole();
                            } else if (selectManagement1 == 2) {
                                hole.readHole();
                            } else if (selectManagement1 == 3) {
                                hole.updateHole();
                            } else if (selectManagement1 == 4) {
                                hole.deleteHole();
                            } else {
                                System.out.println("Wrong number! Please try again.");
                            }
                            break;
                        } else {
                            System.out.println("Not a valid number. Please try again.");
                            break;
                        }

                    case 2:
                        System.out.println("\n \033[1m*** Golfer Info Management ***\033[0m");
                        System.out.println("You are allowed to manage the info via: ");
                        System.out.println("1. Create ");
                        System.out.println("2. Read ");
                        System.out.println("3. Update ");
                        System.out.println("4. Delete ");
                        System.out.print("Please select your action: ");
                        Scanner sc2 = new Scanner(System.in);
                        if (sc2.hasNextInt()) {
                            int selectManagement2 = sc2.nextInt();
                            if (selectManagement2 == 1) {
                                golfer.createGolfer();
                            } else if (selectManagement2 == 2) {
                                golfer.readGolfer();
                            } else if (selectManagement2 == 3) {
                                golfer.updateGolfer();
                            } else if (selectManagement2 == 4) {
                                golfer.deleteGolfer();
                            } else {
                                System.out.println("Wrong number! Please try again.");
                            }
                            break;
                        } else {
                            System.out.println("Not a valid number. Please try again.");
                            break;
                        }

                    case 3:
                        System.out.println("\n*** Tournament Management ***");
                        System.out.println("You are allowed to manage the info via: ");
                        System.out.println("1. Create ");
                        System.out.println("2. Read ");
                        System.out.println("3. Update ");
                        System.out.println("4. Delete ");
                        System.out.print("Please select your action: ");
                        Scanner sc3 = new Scanner(System.in);
                        if (sc3.hasNextInt()) {
                            int selectManagement3 = sc3.nextInt();
                            if (selectManagement3 == 1) {
                                tournament.createTournament();
                            } else if (selectManagement3 == 2) {
                                tournament.readTournament();
                            } else if (selectManagement3 == 3) {
                                tournament.updateTournament();
                            } else if (selectManagement3 == 4) {
                                tournament.deleteTournament();
                            } else {
                                System.out.println("Wrong number! Please try again.");
                            }
                            x = true;
                            break;
                        } else {
                            System.out.println("Not a valid number. Please try again.");
                            break;
                        }

                    case 4:
                        System.exit(0);

                    default:
                        System.out.println("You entered wrong number! Please try again.");
                        break;
                }

        } while (x == false);
    }
}
