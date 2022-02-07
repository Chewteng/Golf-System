package my.uum;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class is to manage (create, read, update, delete) the records of the tournament table in the database.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class ManageTournament {

    Scanner scannerTournament = new Scanner(System.in);
    List<String> arrayTournament = new ArrayList<>();
    List<String> displayArrayTournament = new ArrayList<>();
    List<String> displayUpdatedArrayTournament = new ArrayList<>();
    List<String> readArrayTournament = new ArrayList<>();
    List<String> readUpdatedArrayTournament = new ArrayList<>();
    static String tournamentTile = "| Tournament ID | Tournament Date | Mode Of Play | \n" + "|-------------- | --------------- | ------------ |";
    String errorMsg = "\033[1mWrong format. Please retry via menu.\033[0m" + "\n";
    String invalidMsg = "\033[1mInvalid input. Please retry via menu.\033[0m" + "\n";

    /**
     * This method is to create new tournament details such as tournament ID, tournament date and its mode of play in the database.
     */
    public void createTournament() {
        ManageTournament createTournament = new ManageTournament();
        String tournamentID, tournamentDate, tournamentMode;
        char ansCreateAnotherTournament;

        do {
            System.out.println("\n--> Please insert the -->  ");
            System.out.print("1. Tournament ID (1234): ");
            tournamentID = scannerTournament.next();

            String regexID = "(\\d{4})";
            boolean resultID = tournamentID.matches(regexID);

            if (resultID == true)
            {
                boolean checkExistTournamentID = checkExistTournamentIdDB(tournamentID);

                if (checkExistTournamentID == true) {
                    System.out.println("\033[1mWARNING: Tournament ID already exists in the database\033[0m \u2639\uFE0F");
                    System.out.println("Please retry via menu.");
                    App.Menu();
                }
                else
                {
                    System.out.print("2. Tournament Date (YYYY-MM-DD): ");
                    tournamentDate = scannerTournament.next();

                    String regexDate = "^\\d{4}-\\d{2}-\\d{2}$";
                    boolean resultDate = tournamentDate.matches(regexDate);

                    if (resultDate == true)
                    {
                        System.out.println("3. Tournament Play Mode --> ");
                        System.out.println("A. Stroke");
                        System.out.println("B. Bogey");
                        System.out.println("C. Sford");
                        System.out.println("D. BestBall");
                        System.out.println("E. Match");
                        System.out.print("Please select: ");
                        tournamentMode = scannerTournament.next();

                        String selectedMode;
                        switch (tournamentMode.toUpperCase()) {
                            case "A":
                                selectedMode = "Stroke";
                                break;

                            case "B":
                                selectedMode = "Bogey";
                                break;

                            case "C":
                                selectedMode = "Sford";
                                break;

                            case "D":
                                selectedMode = "BestBall";
                                break;

                            case "E":
                                selectedMode = "Match";
                                break;

                            default:
                                selectedMode = null;
                                System.out.println(errorMsg);
                                App.Menu();
                                break;
                        }

                        arrayTournament.add(tournamentID);
                        arrayTournament.add(tournamentDate);
                        arrayTournament.add(selectedMode);

                    }
                    else {
                        System.out.println(errorMsg);
                        App.Menu();
                    }

                }
            }
            else {
                System.out.printf(errorMsg);
            }

            System.out.print("\033[1mDo you want to create another tournament? (Y/N): \033[0m");
            ansCreateAnotherTournament = scannerTournament.next().charAt(0);

        } while ((ansCreateAnotherTournament == 'Y') || (ansCreateAnotherTournament == 'y'));

        for (int x = 0; x < arrayTournament.size(); x += 3) {

            createTournament.createTournamentDB(arrayTournament.get(x), arrayTournament.get(x + 1), arrayTournament.get(x + 2));
        }
        System.out.println("\033[1mCreated new tournament info.\033[0m");
        App.Menu();
    }

    /**
     * This method is to read all tournament details such as tournament ID, tournament date and its mode of play from the database.
     */
    public void readTournament() {

        ManageTournament readTournamentData = new ManageTournament();
        readArrayTournament = readTournamentData.readTournamentDB();
        System.out.println("\n" + tournamentTile);
        if (readArrayTournament.size() > 0) {
            for (int y = 0; y < readArrayTournament.size(); y += 3) {
                System.out.println(String.format("| %-13s | %-15s | %-12s |", readArrayTournament.get(y), readArrayTournament.get(y + 1), readArrayTournament.get(y + 2)));
            }
        } else {
            System.out.println("\033[1mNo record.\033[0m");
        }
        readArrayTournament.clear();
        displayArrayTournament.clear();
        App.Menu();
    }

    /**
     * This method is to update the selected tournament details such as tournament date and its mode of play in the database by inserting the corresponding tournament ID.
     */
    public void updateTournament() {
        ManageTournament updateTournamentData = new ManageTournament();
        ManageTournament readUpdatedTournamentData = new ManageTournament();

        try {
            System.out.print("\nPlease insert the tournament ID to update: ");
            String tourIDUpdate = scannerTournament.next();

            boolean checkResult = checkTournamentDB(tourIDUpdate);

            if (checkResult == true) {
                System.out.println("\n" + "\033[1mWhich do you want to update?: \033[0m");
                System.out.println("1. Tournament Date ");
                System.out.println("2. Mode Of Play ");
                System.out.print("--> Please select: ");
                int option = scannerTournament.nextInt();

                if (option == 1) {

                    System.out.print("Please insert new tournament date (YYYY-MM-DD): ");
                    String newTourDate = scannerTournament.next();

                    String regexDate = "^\\d{4}-\\d{2}-\\d{2}$";
                    boolean resultDate = newTourDate.matches(regexDate);

                    if (resultDate == true) {
                        updateTournamentData.updateTourDateDB(tourIDUpdate, newTourDate);
                        readUpdatedArrayTournament = readUpdatedTournamentData.readUpdatedTourDB(tourIDUpdate);
                    } else {
                        System.out.println(errorMsg);
                        App.Menu();
                    }
                } else if (option == 2) {

                    System.out.println("Mode of play --> ");
                    System.out.println("A. Stroke");
                    System.out.println("B. Bogey");
                    System.out.println("C. Sford");
                    System.out.println("D. BestBall");
                    System.out.println("E. Match");
                    System.out.print("Please select new mode: ");
                    String newMode = scannerTournament.next();

                    String selectedNewMode;
                    switch (newMode.toUpperCase()) {
                        case "A":
                            selectedNewMode = "Stroke";
                            break;

                        case "B":
                            selectedNewMode = "Bogey";
                            break;

                        case "C":
                            selectedNewMode = "Sford";
                            break;

                        case "D":
                            selectedNewMode = "BestBall";
                            break;

                        case "E":
                            selectedNewMode = "Match";
                            break;

                        default:
                            selectedNewMode = null;
                            System.out.println(errorMsg);
                            App.Menu();
                            break;
                    }

                    updateTournamentData.updateTourModeDB(tourIDUpdate, selectedNewMode);
                    readUpdatedArrayTournament = readUpdatedTournamentData.readUpdatedTourDB(tourIDUpdate);
                } else {
                    System.out.println(errorMsg);
                    App.Menu();
                }
            } else {
                System.out.println("\033[1mTournamenet ID is not found. Please try again.\033[0m");
            }

            if (checkResult == true) {
                System.out.println("The updated tournament info is shown as below: ");
                System.out.println(tournamentTile);
                if (readUpdatedArrayTournament.size() > 0) {
                    for (int z = 0; z < readUpdatedArrayTournament.size(); z += 3) {
                        System.out.println(String.format("| %-13s | %-15s | %-12s |", readUpdatedArrayTournament.get(z), readUpdatedArrayTournament.get(z + 1), readUpdatedArrayTournament.get(z + 2)));
                    }
                }
            }

        } catch (InputMismatchException e) {
            System.out.println(invalidMsg);
        }

        readUpdatedArrayTournament.clear();
        displayUpdatedArrayTournament.clear();
        App.Menu();
    }

    /**
     * This method is to delete the tournament details by inserting the tournament ID you plan to remove it from the database.
     */
    public void deleteTournament() {
        System.out.print("Please insert the tournament ID to delete: ");
        String tourIDToDelete = scannerTournament.nextLine();

        boolean checkExistTournamentIdDelete = checkExistTournamentIdDB(tourIDToDelete);

        if (checkExistTournamentIdDelete == true) {
            ManageTournament deleteTourID = new ManageTournament();
            ManageTournament readTourDataAfterDelete = new ManageTournament();
            deleteTourID.deleteTournamentDB(tourIDToDelete);
            readArrayTournament = readTourDataAfterDelete.readTournamentDB();

            if (!readArrayTournament.contains(tourIDToDelete)) {
                System.out.println("\033[1mDeleted succesfully\033[0m \uD83D\uDE0A");
            } else {
                System.out.println("\033[1mDeleted failed\033[0m ");
            }
        } else {
            System.out.println("\033[1mWARNING: No Record\033[0m \u2639\uFE0F");
            System.out.println("Please retry via menu.");
        }

        readArrayTournament.clear();
        displayArrayTournament.clear();
        App.Menu();
    }

    /**
     * This method is to check whether the entered tournament ID from the administrator is in the database.
     *
     * @param checkExistTournamentID The tournament ID with the format of four digit numbers only.
     * @return The result of the tournament ID whether is in the database.
     */
    public boolean checkExistTournamentIdDB(String checkExistTournamentID) {
        String sqlSelectData = "SELECT * FROM tournament WHERE tournament_id = " + checkExistTournamentID;
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
     * @param tID The tournament ID with the format of four digit numbers only.
     * @param tDate The tournament date with the format of YYYY-MM-DD only.
     * @param tMode The tournament mode of play with the A to E option only.
     */
    public void createTournamentDB(String tID, String tDate, String tMode) {
        String sqlInsertTournament = "INSERT INTO tournament (tournament_id, tournament_date, mode_of_play) VALUES (?,?,?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertTournament)) {
            stmt.setString(1, tID);
            stmt.setString(2, tDate);
            stmt.setString(3, tMode);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to select the tournament database table in order to read all the corresponding data.
     *
     * @return All tournament details from the database.
     */
    public List<String> readTournamentDB() {
        String sqlSelectData = "SELECT * FROM tournament";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectData)) {

            while (rs.next()) {
                String tourID = rs.getString("tournament_id");
                String tourDate = rs.getString("tournament_date");
                String mode = rs.getString("mode_of_play");

                displayArrayTournament.add(tourID);
                displayArrayTournament.add(tourDate);
                displayArrayTournament.add(mode);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayArrayTournament;
    }

    /**
     * This method is to check whether the entered tournament ID from the administrator is in the database.
     *
     * @param checkTourID The tournament ID with the format of four digit numbers only in the database.
     * @return The result of the tournament ID whether is in the database.
     */
    public boolean checkTournamentDB(String checkTourID) {
        String sqlSelectData = "SELECT * FROM tournament WHERE tournament_id = " + checkTourID;
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
     * This method is to update new tournament date entered by the administrator to the database.
     *
     * @param tIDForUpdate The selected tournament ID that plans to update its tournament date.
     * @param newTDate The new tournament date with the format of YYYY-MM-DD only.
     */
    public void updateTourDateDB(String tIDForUpdate, String newTDate) {
        String sqlUpdate = "UPDATE tournament SET tournament_date = ? " + "WHERE tournament_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setString(1, newTDate);
            stmt.setString(2, tIDForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to update new tournament mode of play selected by the administrator to the database.
     *
     * @param tIDForUpdate The selected tournament ID that plans to update its tournament mode of play.
     * @param newTMode The new tournament mode of play with the A to E option only.
     */
    public void updateTourModeDB(String tIDForUpdate, String newTMode) {
        String sqlUpdate = "UPDATE tournament SET mode_of_play = ? " + "WHERE tournament_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setString(1, newTMode);
            stmt.setString(2, tIDForUpdate);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is to display the corresponding update tournament details from the tournament table database with the selected tournament ID.
     *
     * @param currentTID To retrieve what details have been updated with the selected tournament ID.
     * @return The update tournament details with the selected tournament ID.
     */
    public List<String> readUpdatedTourDB(String currentTID) {
        String sqlSelectUpdatedData = "SELECT * FROM tournament WHERE tournament_id = " + currentTID;

        try (Connection conn1 = dbConnection.connect();
             Statement stmt = conn1.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectUpdatedData)) {

            while (rs.next()) {
                String tID = rs.getString("tournament_id");
                String tDate = rs.getString("tournament_date");
                String tMode = rs.getString("mode_of_play");

                displayUpdatedArrayTournament.add(tID);
                displayUpdatedArrayTournament.add(tDate);
                displayUpdatedArrayTournament.add(tMode);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return displayUpdatedArrayTournament;
    }

    /**
     * This method is to delete the tournament details with selected tournament ID.
     *
     * @param tIDToDelete The detailed of the selected tournament ID will be deleted.
     */
    public void deleteTournamentDB(String tIDToDelete) {
        String sqlDelete = "DELETE FROM tournament WHERE tournament_id = " + tIDToDelete;

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}