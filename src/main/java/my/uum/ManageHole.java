package my.uum;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class is to manage (create, read, update, delete) the records of the hole table in the database.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class ManageHole {

    Scanner scannerHole = new Scanner(System.in);
    List<Integer> arrayHole = new ArrayList<>();
    List<Integer> displayArrayHole = new ArrayList<>();
    List<Integer> displayUpdatedArrayHole = new ArrayList<>();
    List<Integer> readArrayHole = new ArrayList<>();
    List<Integer> readUpdatedArrayHole = new ArrayList<>();
    static String holeTile = "| Hole No. | Hole Index | Hole PAR | \n" + "|--------- | ---------- | -------- |";
    String evenErrorMsg = "\033[1mValue is out of range/ not an even number. Please try again.\033[0m" + "\n";
    String oddErrorMsg = "\033[1mValue is out of range/ not an odd number. Please try again.\033[0m" + "\n";
    String errorMsg = "\033[1mValue is out of range. Please try again.\033[0m";
    String invalidMsg = "\033[1mInvalid input. Please retry via menu.\033[0m";

    /**
     * This method is to create new hole details such as hole number, hole index and hole PAR in the database.
     */
    public void createHole() {
        ManageHole createHole = new ManageHole();
        int insertHoleNum, insertHoleIndex, insertHolePAR;
        char ansCreateAnotherHole = 0;

        do {

            try {
                System.out.println("\n--> Please insert the -->  ");
                System.out.print("1. Hole number (> 0): ");
                insertHoleNum = scannerHole.nextInt();

                if (insertHoleNum > 0) {
                    boolean checkExistHoleNum = checkExistHoleNumDB(insertHoleNum);

                    if (checkExistHoleNum == true) {
                        System.out.println("\033[1mWARNING: Hole number already exists in the database\033[0m \u2639\uFE0F");
                        System.out.println("\033[1mPlease retry via menu.\033[0m");
                        App.Menu();
                    } else {
                        if (insertHoleNum > 0 && insertHoleNum % 2 == 0) {
                            System.out.print("2. Hole index with even number (2 - 18): ");
                            insertHoleIndex = scannerHole.nextInt();

                            if (insertHoleIndex % 2 == 0 && insertHoleIndex >= 2 && insertHoleIndex <= 18) {
                                System.out.print("3. Hole PAR (3 - 5): ");
                                insertHolePAR = scannerHole.nextInt();

                                if (insertHolePAR >= 3 && insertHolePAR <= 5) {
                                    arrayHole.add(insertHoleNum);
                                    arrayHole.add(insertHoleIndex);
                                    arrayHole.add(insertHolePAR);
                                } else {
                                    System.out.println(errorMsg);
                                }
                            } else {
                                System.out.println(evenErrorMsg);
                            }
                        } else if (insertHoleNum > 0 && insertHoleNum % 2 != 0) {
                            System.out.print("2. Hole index with odd number (1 - 17): ");
                            insertHoleIndex = scannerHole.nextInt();

                            if (insertHoleIndex % 2 != 0 && insertHoleIndex >= 1 && insertHoleIndex <= 17) {
                                System.out.print("3. Hole PAR (3 - 5): ");
                                insertHolePAR = scannerHole.nextInt();

                                if (insertHolePAR >= 3 && insertHolePAR <= 5) {
                                    arrayHole.add(insertHoleNum);
                                    arrayHole.add(insertHoleIndex);
                                    arrayHole.add(insertHolePAR);
                                } else {
                                    System.out.println(errorMsg);
                                }
                            } else {
                                System.out.println(oddErrorMsg);
                            }
                        } else {
                            System.out.println(errorMsg);
                        }
                    }
                } else {
                    System.out.printf(errorMsg + "\n");
                }

            } catch (InputMismatchException e) {
                System.out.println(invalidMsg);
                App.Menu();
            }

            System.out.print("\033[1mDo you want to create another hole? (Y/N): \033[0m");
            ansCreateAnotherHole = scannerHole.next().charAt(0);

        } while ((ansCreateAnotherHole == 'Y') || (ansCreateAnotherHole == 'y'));

        for (int x = 0; x < arrayHole.size(); x += 3) {
            createHole.createHoleDB(arrayHole.get(x), arrayHole.get(x + 1), arrayHole.get(x + 2));
        }
        System.out.println("\033[1mCreated new hole info.\033[0m");
    }

    /**
     * This method is to read all hole details such as hole number, hole index and hole PAR from the database.
     */
    public void readHole() {

        ManageHole readHoleData = new ManageHole();
        readArrayHole = readHoleData.readHoleDB();
        System.out.println("\n" + holeTile);
        if (readArrayHole.size() > 0) {
            for (int y = 0; y < readArrayHole.size(); y += 3) {
                System.out.println(String.format("| %-8s | %-10s | %-8s |", readArrayHole.get(y), readArrayHole.get(y + 1), readArrayHole.get(y + 2)));
            }
        } else {
            System.out.println("No record.");
        }
        readArrayHole.clear();
        displayArrayHole.clear();
        App.Menu();
    }

    /**
     * This method is to update the selected hole details such as hole index and hole PAR in the database by inserting the corresponding hole number.
     */
    public void updateHole() {
        ManageHole updateHoleData = new ManageHole();
        ManageHole readUpdatedHoleData = new ManageHole();

        try {
            System.out.print("\nPlease insert the hole number to update: ");
            int holeNumToUpdate = scannerHole.nextInt();
            boolean checkResult = checkHoleDB(holeNumToUpdate);

            if (checkResult == true) {
                System.out.println("\n" + "\033[1mWhich do you want to update?: \033[0m");
                System.out.println("1. Hole index ");
                System.out.println("2. Hole PAR ");
                System.out.print("--> Please select: ");
                int option = scannerHole.nextInt();

                if (option == 1) {

                    if (holeNumToUpdate % 2 == 0) {
                        System.out.print("Please insert new hole index (2 - 18): ");
                        int newHoleIndex = scannerHole.nextInt();

                        if (newHoleIndex % 2 == 0 && newHoleIndex >= 2 && newHoleIndex <= 18) {
                            updateHoleData.updateHoleIndexDB(holeNumToUpdate, newHoleIndex);
                            readUpdatedArrayHole = readUpdatedHoleData.readUpdatedHoleDB(holeNumToUpdate);
                        } else {
                            System.out.println(evenErrorMsg);
                        }
                    } else if (holeNumToUpdate % 2 != 0) {
                        System.out.print("Please insert new hole index (1 - 17): ");
                        int newHoleIndex = scannerHole.nextInt();

                        if (newHoleIndex % 2 != 0 && newHoleIndex >= 1 && newHoleIndex <= 17) {
                            updateHoleData.updateHoleIndexDB(holeNumToUpdate, newHoleIndex);
                            readUpdatedArrayHole = readUpdatedHoleData.readUpdatedHoleDB(holeNumToUpdate);
                        } else {
                            System.out.println(oddErrorMsg);
                        }
                    } else {
                        System.out.println(errorMsg);
                    }
                } else if (option == 2) {

                    System.out.print("Please insert new hole PAR (3 - 5): ");
                    int newHolePAR = scannerHole.nextInt();
                    if (newHolePAR >= 3 && newHolePAR <= 5) {
                        updateHoleData.updateHoleParDB(holeNumToUpdate, newHolePAR);
                        readUpdatedArrayHole = readUpdatedHoleData.readUpdatedHoleDB(holeNumToUpdate);
                    } else {
                        System.out.println(errorMsg);
                    }
                } else {
                    System.out.println(errorMsg);
                }
            } else {
                System.out.println("\033[1mHole number is not found. Please try again.\033[0m");
            }

            if (checkResult == true) {
                System.out.println("The updated hole info is shown as below: ");
                System.out.println(holeTile);
                if (readUpdatedArrayHole.size() > 0) {
                    for (int z = 0; z < readUpdatedArrayHole.size(); z += 3) {
                        System.out.println(String.format("| %-8s | %-10s | %-8s |", readUpdatedArrayHole.get(z), readUpdatedArrayHole.get(z + 1), readUpdatedArrayHole.get(z + 2)));
                    }
                }
            }

        } catch (InputMismatchException e) {
            System.out.println(invalidMsg);
        }
        readUpdatedArrayHole.clear();
        displayUpdatedArrayHole.clear();
        App.Menu();
    }

    /**
     * This method is to delete the hole details by inserting the hole number you plan to remove it from the database.
     */
    public void deleteHole() {

        try {
            System.out.print("Please insert the hole number to delete: ");
            int holeNumToDelete = scannerHole.nextInt();

            boolean checkExistHoleNumDelete = checkExistHoleNumDB(holeNumToDelete);

            if (checkExistHoleNumDelete == true) {
                ManageHole deleteHoleNum = new ManageHole();
                ManageHole readHoleDataAfterDelete = new ManageHole();
                deleteHoleNum.deleteHoleDB(holeNumToDelete);
                readArrayHole = readHoleDataAfterDelete.readHoleDB();

                if (!readArrayHole.contains(holeNumToDelete)) {
                    System.out.println("\033[1mDeleted succesfully\033[0m \uD83D\uDE0A");
                } else {
                    System.out.println("\033[1mDeleted failed\033[0m ");
                }
            } else {
                System.out.println("\033[1mWARNING: No Record\033[0m \u2639\uFE0F");
                System.out.println("Please retry via menu.");
            }

        } catch (InputMismatchException e) {
            System.out.println(invalidMsg);
        }

        readArrayHole.clear();
        displayArrayHole.clear();
        App.Menu();
    }

    /**
     * This method is to check whether the entered hole number from the administrator is in the database.
     *
     * @param checkExistHoleNum The hole number which must be greater than 0.
     * @return The result of the hole number whether is in the database.
     */
    public boolean checkExistHoleNumDB(int checkExistHoleNum) {
        String sqlSelectData = "SELECT * FROM hole WHERE hole_no = " + checkExistHoleNum;
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
     * This method is to insert the corresponding input from the administrator to the database.
     *
     * @param holeNum The hole number which is greater than 0.
     * @param holeIndex The hole index with the value of 2 to 18 only.
     * @param holePAR The hole PAR with the value of 3 to 5 only.
     */
    public void createHoleDB(int holeNum, int holeIndex, int holePAR) {
        String sqlInsertHole = "INSERT INTO hole (hole_no, hole_index, hole_par) VALUES (?,?,?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertHole)) {
            stmt.setInt(1, holeNum);
            stmt.setInt(2, holeIndex);
            stmt.setInt(3, holePAR);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to select the hole database table in order to read all the corresponding data.
     *
     * @return All hole details from the database.
     */
    public List<Integer> readHoleDB() {
        String sqlSelectData = "SELECT * FROM hole";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectData)) {

            while (rs.next()) {
                int holeNo = rs.getInt("hole_no");
                int holeIndex = rs.getInt("hole_index");
                int holePAR = rs.getInt("hole_par");

                displayArrayHole.add(holeNo);
                displayArrayHole.add(holeIndex);
                displayArrayHole.add(holePAR);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayArrayHole;
    }

    /**
     * This method is to check whether the entered hole number from the administrator is in the database.
     *
     * @param checkHoleNum The hole number which must be greater than 0 in the database.
     * @return The result of the hole number whether is in the database.
     */
    public boolean checkHoleDB(int checkHoleNum) {
        String sqlSelectData = "SELECT * FROM hole WHERE hole_no = " + checkHoleNum;
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
     * This method is to update new hole index entered by the administrator to the database.
     *
     * @param holeNumForUpdate The selected hole number that plans to update its hole index.
     * @param newHoleIndex The new hole index with the value of 2 to 18 only.
     */
    public void updateHoleIndexDB(int holeNumForUpdate, int newHoleIndex) {
        String sqlUpdate = "UPDATE hole SET hole_index = ? " + "WHERE hole_no = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setInt(1, newHoleIndex);
            stmt.setInt(2, holeNumForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to update new hole PAR entered by the administrator to the database.
     *
     * @param holeNumForUpdate The selected hole number that plans to update its hole PAR.
     * @param newHolePAR The new PAR with the value of 3 to 5 only.
     */
    public void updateHoleParDB(int holeNumForUpdate, int newHolePAR) {
        String sqlUpdate = "UPDATE hole SET hole_par = ? " + "WHERE hole_no = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setInt(1, newHolePAR);
            stmt.setInt(2, holeNumForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to display the corresponding update hole details from the hole table database with the selected hole number.
     *
     * @param currentHoleNum To retrieve what details have been updated with the selected hole number.
     * @return The update hole details with the selected hole number.
     */
    public List<Integer> readUpdatedHoleDB(int currentHoleNum) {
        String sqlSelectUpdatedData = "SELECT * FROM hole WHERE hole_no = " + currentHoleNum;

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectUpdatedData)) {

            while (rs.next()) {
                int holeNo = rs.getInt("hole_no");
                int holeIndex = rs.getInt("hole_index");
                int holePAR = rs.getInt("hole_par");

                displayUpdatedArrayHole.add(holeNo);
                displayUpdatedArrayHole.add(holeIndex);
                displayUpdatedArrayHole.add(holePAR);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayUpdatedArrayHole;
    }

    /**
     * This method is to delete the hole details with selected hole number.
     *
     * @param holeNumToDelete The detailed of the selected hole number will be deleted.
     */
    public void deleteHoleDB(int holeNumToDelete) {
        String sqlDelete = "DELETE FROM hole WHERE hole_no = " + holeNumToDelete;

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}