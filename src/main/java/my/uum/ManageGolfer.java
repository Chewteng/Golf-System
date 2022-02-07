package my.uum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is to manage (create, read, update, delete) the records of the golfer table in the database.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class ManageGolfer {

    Scanner input = new Scanner(System.in);
    Scanner input1 = new Scanner(System.in);

    //These array lists are used to store the data. It has no size limit.
    List<String> arrayGolfer = new ArrayList<>();
    List<String> displayArrayGolfer = new ArrayList<>();
    List<String> readArrayGolfer = new ArrayList<>();
    List<String> readUpdatedArrayGolfer = new ArrayList<>();
    List<String> displayUpdatedArrayGolfer = new ArrayList<>();

    static String golferTile = "\n| Golfer ID. | Golfer Name                        | Golfer Handicap | Telegram ID               |\n" + "|------------|------------------------------------|-----------------|---------------------------|";


    /**
     * This method is to create new golfer details such as golfer ID, golfer name and golfer telegram ID in the database.
     */
    public void createGolfer() {
        ManageGolfer createGolferDB = new ManageGolfer();
        String insertGolferID, insertGolferName, insertTelegramID;
        char ansInsertAnotherGolferDetails;

        do {

            System.out.println("--> Please insert the -->  ");
            System.out.print("1. Golfer ID (1234): ");
            insertGolferID = input.next();

            String regexID = "(\\d{4})";
            boolean resultID = insertGolferID.matches(regexID);

            if (resultID == true)
            {

                boolean checkResult = checkGolferDB(insertGolferID);

                if (checkResult == true) {
                    System.out.println("\033[1mWARNING: Golfer ID already exists in the database\033[0m \u2639\uFE0F");
                    App.Menu();
                }

                else {
                    System.out.print("2. Golfer Name: ");
                    insertGolferName = input1.nextLine().toLowerCase();

                    // Stores each characters of the name to a char array
                    char[] charArray = insertGolferName.toCharArray();
                    boolean foundSpace = true;

                    for (int i = 0; i < charArray.length; i++) {

                        // If the array element is a letter
                        if (Character.isLetter(charArray[i])) {

                            // Check space is present before the letter
                            if (foundSpace) {

                                // Change the letter into uppercase
                                charArray[i] = Character.toUpperCase(charArray[i]);
                                foundSpace = false;
                            }
                        } else {
                            // If the new character is not character
                            foundSpace = true;
                        }
                    }

                    // Convert the char array to the string
                    insertGolferName = String.valueOf(charArray);

                    System.out.print("3. Golfer Telegram ID: ");
                    insertTelegramID = input.next();

                    arrayGolfer.add(insertGolferID);
                    arrayGolfer.add(insertGolferName);
                    arrayGolfer.add(insertTelegramID);
                }
            }
            else {
                System.out.printf("\033[1mWrong format. Please retry via menu.\033[0m" + "\n");
            }

            System.out.print("\033[1mDo you want to create another golfer details? (Y/N):\033[0m ");
            ansInsertAnotherGolferDetails = input.next().charAt(0);

        } while ((ansInsertAnotherGolferDetails == 'Y') || (ansInsertAnotherGolferDetails == 'y'));

        for (int x = 0; x < arrayGolfer.size(); x += 3) {
            createGolferDB.insertGolferDB(arrayGolfer.get(x), arrayGolfer.get(x + 1), arrayGolfer.get(x + 2));
        }

        System.out.println("\033[1mCreated new golfer info.\033[0m");
    }

    /**
     * This method is to read all golfer details such as golfer ID, golfer name, golfer handicap and golfer telegram ID from the database.
     */
    public void readGolfer() {

        ManageGolfer readGolferData = new ManageGolfer();
        readArrayGolfer = readGolferData.readGolferDB();
        System.out.println(golferTile);
        if (readArrayGolfer.size() > 0) {
            for (int y = 0; y < readArrayGolfer.size(); y += 4) {
                System.out.println(String.format("| %-10s | %-34s | %-15s | %-25s |", readArrayGolfer.get(y), readArrayGolfer.get(y + 1), readArrayGolfer.get(y + 2), readArrayGolfer.get(y + 3)));
            }
        } else {
            System.out.println("\033[1mNo data in golfer DB\033[0m");
        }
        readArrayGolfer.clear();
        displayArrayGolfer.clear();
    }

    /**
     * This method is to update the selected golfer details such as golfer name, golfer handicap and golfer telegram ID in the database by inserting the corresponding golfer ID.
     */
    public void updateGolfer() {

        ManageGolfer readUpdatedGolferData = new ManageGolfer();

        System.out.print("Please insert the golfer ID to update: ");
        String golferIDToUpdate = input.next();

        boolean checkResult = checkGolferDB(golferIDToUpdate);

        if (checkResult == true) {

            /* Golfer ID is fixed */
            System.out.println("\033[1mPlease select one option:\033[0m \n" +
                    "\033[1m1. Golfer name\033[0m \n" +
                    "\033[1m2. Golfer handicap\033[0m \n" +
                    "\033[1m3. Golfer telegram ID\033[0m \n");
            System.out.print("Please input your option: ");
            int optionUpdate = input.nextInt();

            if (optionUpdate == 1) {
                System.out.print("Please insert the new golfer name: ");
                String newGolferName = input1.nextLine().toLowerCase();

                // Stores each characters of the name to a char array
                char[] charArray = newGolferName.toCharArray();
                boolean foundSpace = true;

                for (int i = 0; i < charArray.length; i++) {

                    // If the array element is a letter
                    if (Character.isLetter(charArray[i])) {

                        // Check space is present before the letter
                        if (foundSpace) {

                            // Change the letter into uppercase
                            charArray[i] = Character.toUpperCase(charArray[i]);
                            foundSpace = false;
                        }
                    } else {
                        // If the new character is not character
                        foundSpace = true;
                    }
                }

                // Convert the char array to the string
                newGolferName = String.valueOf(charArray);

                ManageGolfer updateGolferData = new ManageGolfer();
                updateGolferData.updateGolferDB(golferIDToUpdate, newGolferName);
                readUpdatedArrayGolfer = readUpdatedGolferData.readUpdatedGolferDB(golferIDToUpdate);

            } else if (optionUpdate == 2) {
                System.out.print("Please insert the new golfer handicap: ");
                int newGolferHandicap = input.nextInt();
                //System.out.println("\033[1mUpdated succesfully\033[0m \uD83D\uDE0A \n");

                ManageGolfer updateGolferHandicapData = new ManageGolfer();
                if (newGolferHandicap >= 0 && newGolferHandicap <= 24) {
                    updateGolferHandicapData.updateGolferHandicap(golferIDToUpdate, newGolferHandicap);
                    readUpdatedArrayGolfer = readUpdatedGolferData.readUpdatedGolferDB(golferIDToUpdate);
                } else {
                    System.out.println("Value is out of range. Please retry via menu.");
                }

            } else if (optionUpdate == 3) {
                System.out.print("Please insert the new golfer telegram ID: ");
                String newGolferTelegramID = input.next();

                ManageGolfer updateGolferTelegramIDData = new ManageGolfer();
                updateGolferTelegramIDData.updateGolferTelegramID(golferIDToUpdate, newGolferTelegramID);
                readUpdatedArrayGolfer = readUpdatedGolferData.readUpdatedGolferDB(golferIDToUpdate);

            } else {
                System.out.println("\033[1mWrong input\033[0m");
            }
        } else {
            System.out.print("\033[1mGolfer ID not found. Please try again.\033[0m \n");
        }

        //Display the update golfer details in a certain format after the new input information has been saved into the database.
        if (checkResult == true) {
            System.out.println("\nThe updated golfer info is shown as below: ");
            System.out.println(golferTile);
            if (readUpdatedArrayGolfer.size() > 0) {
                for (int z = 0; z < readUpdatedArrayGolfer.size(); z += 4) {
                    System.out.println(String.format("| %-10s | %-34s | %-15s | %-25s |", readUpdatedArrayGolfer.get(z), readUpdatedArrayGolfer.get(z + 1), readUpdatedArrayGolfer.get(z + 2), readUpdatedArrayGolfer.get(z + 3)));
                }
            } else {
                System.out.println("\033[1mERROR\033[0m");
            }
        }

        readUpdatedArrayGolfer.clear();
        displayUpdatedArrayGolfer.clear();
    }

    /**
     * This method is to delete the golfer details by inserting the golfer ID you plan to remove it from the database.
     */
    public void deleteGolfer() {
        System.out.print("Please insert the golfer ID to delete: ");
        String golferInfoToDelete = input.next();
        boolean checkResult = checkGolferDB(golferInfoToDelete);

        if (checkResult == true) {
            ManageGolfer deleteGolferInfo = new ManageGolfer();
            ManageGolfer readGolferDataAfterDelete = new ManageGolfer();
            deleteGolferInfo.deleteGolferDB(golferInfoToDelete);
            readArrayGolfer = readGolferDataAfterDelete.readGolferDB();

            if (!readArrayGolfer.contains(golferInfoToDelete)) {
                System.out.println("Deleted succesfully");
            } else {
                System.out.println("Delete failed");
            }
        } else {
            System.out.print("\033[1m Golfer ID not found. Please try again.\033[0m \n");
        }
        readArrayGolfer.clear();
        displayArrayGolfer.clear();
    }

    /**
     * This method is to insert the corresponding input from the administrator to the database.
     *
     * @param golfer_id The golfer ID with the format of four digit numbers only.
     * @param golfer_name The full name which including the first name and the last name.
     * @param telegram_id The name registered in the Telegram.
     */
    public void insertGolferDB(String golfer_id, String golfer_name, String telegram_id) {
        String sqlInsertGolferInfo = "INSERT INTO golfer (golfer_id, golfer_name, telegram_id) VALUES (?,?,?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertGolferInfo)) {
            stmt.setString(1, golfer_id);
            stmt.setString(2, golfer_name);
            stmt.setString(3, telegram_id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to select the golfer database table in order to read all the corresponding data.
     *
     * @return All golfer details from the database.
     */
    public List<String> readGolferDB() {
        String sqlSelectData = "SELECT * FROM golfer";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectData)) {

            while (rs.next()) {
                String golferId = rs.getString("golfer_id");
                String golferName = rs.getString("golfer_name");
                String golferHandicap = rs.getString("golfer_handicap");
                String telegramId = rs.getString("telegram_id");

                displayArrayGolfer.add(golferId);
                displayArrayGolfer.add(golferName);
                displayArrayGolfer.add(golferHandicap);
                displayArrayGolfer.add(telegramId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayArrayGolfer;
    }

    /**
     * This method is to check whether the entered golfer ID from the administrator is in the database.
     *
     * @param checkGolferID The ID of the golfer.
     * @return The result of the golfer ID whether is in the database.
     */
    public boolean checkGolferDB(String checkGolferID) {
        String sqlSelectData = "SELECT * FROM golfer WHERE golfer_id = " + checkGolferID;
        boolean result = true;

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectData)) {

            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }

    /**
     * This method is to update new golfer name entered by the administrator to the database.
     *
     * @param golferIDForUpdate The selected golfer ID that plans to update his or her golfer name.
     * @param newGolferName The new golfer name which including the first name and the last name.
     */
    public void updateGolferDB(String golferIDForUpdate, String newGolferName) {
        String sqlDelete = "UPDATE golfer SET golfer_name = ? " + "WHERE golfer_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setString(1, newGolferName);
            stmt.setString(2, golferIDForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to update new handicap of the golfer entered by the administrator to the database.
     *
     * @param golferIDForUpdate The selected golfer ID that plans to update his or her golfer handicap.
     * @param newGolferHandicap The new golfer handicap with the limit from 0 to 24 only.
     */
    public void updateGolferHandicap(String golferIDForUpdate, int newGolferHandicap) {
        String sqlDelete = "UPDATE golfer SET golfer_handicap = ? " + "WHERE golfer_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setInt(1, newGolferHandicap);
            stmt.setString(2, golferIDForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to update new telegram ID of the golfer entered by the administrator to the database.
     *
     * @param golferIDForUpdate The selected golfer ID that plans to update his or her telegram ID.
     * @param newGolferTelegramID The new name registered in the Telegram.
     */
    public void updateGolferTelegramID(String golferIDForUpdate, String newGolferTelegramID) {
        String sqlDelete = "UPDATE golfer SET telegram_id = ? " + "WHERE golfer_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setString(1, newGolferTelegramID);
            stmt.setString(2, golferIDForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to display the corresponding update golfer details from the golfer table database with the selected golfer ID.
     *
     * @param currentInfo To retrieve what details have been updated with the selected golfer ID.
     * @return The update golfer details with the selected golfer ID.
     */
    public List<String> readUpdatedGolferDB(String currentInfo) {
        String sqlSelectUpdatedData = "SELECT * FROM golfer WHERE golfer_id = " + currentInfo;

        try (Connection conn1 = dbConnection.connect();
             Statement stmt = conn1.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectUpdatedData)) {

            while (rs.next()) {
                String golferId = rs.getString("golfer_id");
                String golferName = rs.getString("golfer_name");
                String golferHandicap = rs.getString("golfer_handicap");
                String telegramId = rs.getString("telegram_id");

                displayUpdatedArrayGolfer.add(golferId);
                displayUpdatedArrayGolfer.add(golferName);
                displayUpdatedArrayGolfer.add(golferHandicap);
                displayUpdatedArrayGolfer.add(telegramId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayUpdatedArrayGolfer;
    }

    /**
     * This method is to delete the golfer details with selected golfer ID.
     *
     * @param golferInfoToDelete The detailed of the selected golfer ID will be deleted.
     */
    public void deleteGolferDB(String golferInfoToDelete) {
        String sqlDelete = "DELETE FROM golfer WHERE golfer_id = " + golferInfoToDelete;

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}