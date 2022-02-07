package my.uum;

import java.sql.*;

/**
 * This class is contains a lot of sql function for telegram bot interaction.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class dbConnection {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:golf.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return con;
    }

    /**
     * This method is for insert golfer name, golfer handicap and telegram id into sqlite
     * @param GolferName golfer name
     * @param GolferHandicap golfer handicap
     * @param TelegramID telegram ID take from telegram
     */
    public static void insertGolfer(String GolferName, int GolferHandicap, long TelegramID){
        Connection con = connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO golfer(golfer_name, golfer_handicap, telegram_id) VALUES(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, GolferName);
            ps.setInt(2, GolferHandicap);
            ps.setLong(3, TelegramID);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }

    /**
     * This method is for verify register, is check
     * @param telegram_id telegram ID take from telegram
     * @return True or False
     */
    public static boolean verifyRegister(long telegram_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String readsql = "SELECT golfer_name FROM golfer WHERE telegram_id = ? ";
            ps = con.prepareStatement(readsql);
            ps.setLong(1, telegram_id);
            rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return false;
    }

    /**
     * This method is use for telegram can get golfer information from sqlite thought telegram ID
     * @param telegram_id user telegram ID take from telegram chat
     * @return golfer ID, golfer name and golfer handicap return to telegram bot
     */
    public static String golferInformation(long telegram_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            String readsql = "SELECT * FROM golfer WHERE telegram_id = ? ";
            ps = con.prepareStatement(readsql);
            ps.setLong(1, telegram_id);
            rs = ps.executeQuery();

            if (rs.next()){
                result = "ID: " + rs.getInt("golfer_id") + "\nName: " + rs.getString("golfer_name") + "\nHandicap: " + rs.getString("golfer_handicap");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }

    /**
     * This method is for read information of tournament from sqlite.
     * @return return tournament id, tournament date and mode of play to telegram.
     */
    public static String readtournament(){
        Connection con = connect();
        Statement ps = null;
        ResultSet rs = null;
        String id;
        String date;
        String mode;
        StringBuilder view = new StringBuilder("_id______date_______mode of play______");

        try {
            String readsql = "SELECT * FROM tournament";
            ps = con.createStatement();
            rs = ps.executeQuery(readsql);

            while(rs.next()){
                id = rs.getString("tournament_id");
                date = rs.getString("tournament_date");
                mode = rs.getString("mode_of_play");
                view.append("\n").append(" ").append(id).append("   ").append(date).append("          ").append(mode);
            }

        } catch (SQLException e) {
            return "No Tournament";
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return view.toString();
    }

    /**
     * This method is use for take part function, insert golfer information into tournament if golfer desire to take part.
     * @param tournament_id tournament ID to take part
     * @param golfer_id golfer ID to take part the tournament
     * @return Comment will return like, "Full already" or "Success to take part"
     */
    public static String take_part(String tournament_id, int golfer_id){
        Connection con = connect();
        PreparedStatement ps = null;

        if(!verifytakepartstatus(tournament_id,golfer_id))
        {
            return "You are take part this tournament already.";
        }
        else if(!tournament_take_part_number(tournament_id))
        {
            return "Full already";
        }
        else
        {
            try {
                String sql = "INSERT INTO player_tournament(tournament_id, golfer_id) VALUES(?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, tournament_id);
                ps.setInt(2, golfer_id);
                ps.execute();

                for(int i = 1; i <=18 ; i ++){
                    createPlayerScore(tournament_id, golfer_id, i);
                }
                return "Success to take part.";
            } catch (SQLException e) {
                System.out.println(e.toString());
                return "Invalid Tournament ID";
            } finally{
                try{
                    if(ps != null)
                        ps.close();
                    con.close();
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }
    }

    /**
     * This method is for create player score in player_score after user register.
     * @param tournament_id tournament id get from telegram id
     * @param golfer_id golfer id get from register method
     * @param hole_no hole number for create score and save into database
     */
    private static void createPlayerScore(String tournament_id, int golfer_id, int hole_no) {
        Connection con = connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO player_score(tournament_id, golfer_id, hole_no, score_id, approval_status) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            ps.setInt(2, golfer_id);
            ps.setInt(3, hole_no);
            ps.setString(4,"0");
            ps.setString(5,"False");
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }

    /**
     * This method is for check the tournament participant number
     * @param tournament_id tournament id from telegram is use for get data from play_tournament sqlite
     * @return if participant number in this tournament is over than 6 return false, lower than 6 return true
     */
    public static boolean tournament_take_part_number(String tournament_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String readplayertournamentsql = "SELECT * FROM player_tournament WHERE tournament_id = ?";
            ps = con.prepareStatement(readplayertournamentsql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            int number = 0;
            while(rs.next()){
                number ++;
            }

            if(number==6){
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return true;
    }

    /**
     * This method is for read golfer ID from sqlite.
     * @param telegram_id telegram id take from telegram id, use for get golfer_id from golfer database
     * @return golfer id from sqlite
     */
    public static int readgolferid(long telegram_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String readsql = "SELECT golfer_id FROM golfer WHERE telegram_id = ? ";
            ps = con.prepareStatement(readsql);
            ps.setLong(1, telegram_id);
            rs = ps.executeQuery();

            if (rs.next()){
                return rs.getInt("golfer_id");
            }else{
                return 0;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return 0;
    }

    /**
     * This method is use for verify tournament ID in database or not.
     * @param tournament_id tournament id get from telegram, use to get the all information from tournament database and verity the tournament ID
     * @return return true if tournament ID pass from telegram is appear in database, return false if no have appear in database
     */
    public static boolean verifytournamentid(String tournament_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String readsql = "SELECT * FROM tournament WHERE tournament_id = ? ";
            ps = con.prepareStatement(readsql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return false;
    }

    /**
     * This method is use to verify take part status based on tournament id and golfer id from telegram
     * @param tournament_id tournament id to verify status take part
     * @param golfer_id golfer id use to verify status of take part tournament
     * @return return false if they are in the database, return true if they are not in the database
     */
    public static boolean verifytakepartstatus(String tournament_id, int golfer_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String readsql = "SELECT * FROM player_tournament WHERE tournament_id = ? AND golfer_id = ?";
            ps = con.prepareStatement(readsql);
            ps.setString(1, tournament_id);
            ps.setInt(2, golfer_id);
            rs = ps.executeQuery();

            if(rs.next()){
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return true;
    }

    /**
     * This method is for view tournament based on the telegram id from telegram
     * @param telegram_id telegram id use to find the all data from player_tournament
     * @return return all tournament information to telegram
     */
    public static String viewtournament(long telegram_id){
        int golfer_id = readgolferid(telegram_id);
        if(golfer_id == 0){
            return "Please register first to continue.";
        }else{
            Connection con = connect();
            PreparedStatement ps = null;
            ResultSet rs = null;
            String result = "_id______date_______mode of play______";

            try {
                String readsql = "SELECT * FROM player_tournament WHERE golfer_id = ?";
                ps = con.prepareStatement(readsql);
                ps.setInt(1, golfer_id);
                rs = ps.executeQuery();

                while (rs.next()){
                    result = result + "\n" + read_tournament_based_tournament_id(rs.getString("tournament_id"));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally{
                try{
                    if(ps != null)
                        ps.close();
                    if(rs != null)
                        rs.close();
                    con.close();
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
            return result;
        }
    }

    /**
     * This method read tournament information based on tournament id
     * @param tournament_id this tournament id is input from telegram user and use for view tournament from viewtournament method
     * @return tournament information from sqlite
     */
    public static String read_tournament_based_tournament_id(String tournament_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            String readsql = "SELECT * FROM tournament WHERE tournament_id = ?";
            ps = con.prepareStatement(readsql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                result = " " + rs.getString(1) + "   " + rs.getString(2) + "          " + rs.getString(3);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }

    /**
     * This method is use for check who is take part the tournament
     * @param tournament_id tournament id input from telegram user and check who is take part the tournament in database
     * @return return golfer id based on tournament id who is take part the tournament
     */
    public static String who_take_part(String tournament_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "Tournament ID: " + tournament_id;

        try {
            String readsql = "SELECT * FROM player_tournament WHERE tournament_id = ?";
            ps = con.prepareStatement(readsql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                result = result + "\n" + getGolferName(rs.getString("golfer_id"));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }

    /**
     * This method is use for get golfer name based on golfer id from golfer database.
     * @param golfer_id golfer id(get after user register this system)
     * @return golfer name and handicap return
     */
    public static String getGolferName(String golfer_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            String readsql = "SELECT * FROM golfer WHERE golfer_id = ?";
            ps = con.prepareStatement(readsql);
            ps.setInt(1, Integer.parseInt(golfer_id.trim()));
            rs = ps.executeQuery();

            while (rs.next()){
                result = "Name: " + rs.getString(2) + ",\t\tHandicap: " + rs.getString(3);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }

    /**
     * This method is use for get mark from every hole based on tournament id.
     * @param tournament_id tournament id get from telegram bot
     * @return return result of tournament mark in every hole based on tournament id
     */
    public static String tournament_hole_mark(String tournament_id){
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "Tournament ID: " + tournament_id;

        try {
            String readsql = "SELECT * FROM player_tournament WHERE tournament_id = ?";
            ps = con.prepareStatement(readsql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                result = result + "\n" + getGolferScore(rs.getString("golfer_id"),tournament_id);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result+"\n"+"Winner: ";
    }

    /**
     * This method is for get Golfer Score based on golfer id and tournament id
     * @param golfer_id golfer id after user register in telegram or system admin
     * @param tournament_id tournament id from telegram and use for get golfer score
     * @return return the score and total to the another two method to continue the process
     */
    private static String getGolferScore(String golfer_id, String tournament_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        String[] score = new String[18];
        String result = "ID = " + golfer_id + "    Score = ";

        try {
            String sql = "SELECT * FROM player_score WHERE golfer_id = ? AND tournament_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(golfer_id.trim()));
            ps.setString(2,tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                for(int i = 1; i <= 18;i++){
                    if(rs.getInt("hole_no") == i){
                        score[i-1] = rs.getString("score_id");
                        total = total + Integer.parseInt((score[i-1].trim()));
                    }
                }
            }

            for(int i = 0; i < score.length; i++){
                result = result + " " + score[i];
            }
            result = result + "   Total: " + total;

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }
    /**
     * This is for verifying marker id based on tournament id and marker id
     * @param tournament_id is taken from database and as an identifier to verify marker id
     * @param marker_id is taken from database and as an identifier to verify marker id
     * @return false if the marker id is not same with golfer id in the database or vice versa
     */
    public static boolean verifymarkerid(String tournament_id, String marker_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM player_tournament WHERE tournament_id = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                if(marker_id.equals(rs.getString("golfer_id"))){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return false;
    }
    /**
     * This method is for setting the marker id based on tournament id, marker id, and telegram id
     * @param tournament_id is taken from database and as an identifier to set the marker id
     * @param marker_id is taken from database and as an identifier to set the marker id
     * @param telegram_id is taken from telegram and as an identifier to set the marker id
     */
    public static void setMarkerID(String tournament_id, String marker_id, long telegram_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        String golfer_id = Integer.toString(readgolferid(telegram_id));
        try {
            String sql = "INSERT INTO player(tournament_id, marker_id, golfer_id) VALUES(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            ps.setString(2, marker_id);
            ps.setString(3, golfer_id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
    /**
     * This method is for verifying reply marker id based on tournament id and marker id
     * @param tournament_id as an identifier to check the reply marker id
     * @param marker_id as an identifier to check the reply marker id
     * @return false if the marker id have been used by others players or vice versa
     */
    public static boolean verifyrepliedmarkerid(String tournament_id, String marker_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM player WHERE tournament_id = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                if(marker_id.equals(rs.getString("marker_id"))){
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return true;
    }

    /**
     * This method is for checking the marker id from Telegram is same as the marker id from database
     * @param tournament_id as an identifier to check whether the marker id from both side is same
     * @param marker_id as an identifier to check whether the marker id from both side is same
     * @param telegram_id as an identifier to check whether the marker id from both side is same
     * @return true means to reject if they are same or vice versa
     */
    public static boolean samemarkerid(String tournament_id, String marker_id, long telegram_id) {
        int golfer_id = readgolferid(telegram_id);
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM player WHERE tournament_id = ? AND golfer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            ps.setString(2, String.valueOf(golfer_id));
            rs = ps.executeQuery();

            while (rs.next()){
                if(marker_id.equals(rs.getString("marker_id"))){
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return true;
    }

    /**
     * This method is for checking the telegram player whether exist in the tournament
     * @param tournament_id as an identifier to check the player whether exist in the tournament
     * @param telegram_id as an identifier to check the player whether exist in the tournament
     * @return true if the player exist in the tournament or vice versa
     */
    public static boolean isInthisTournament(String tournament_id, long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM player_tournament WHERE tournament_id = ? AND golfer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            ps.setString(2, golfer_id);
            rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return false;
    }
    /**
     * This method is for updating the score
     * @param tournament_id is taken from the database and as an identifier to update the score
     * @param hole_no is taken from the database and as an identifier to update the score
     * @param score is taken from the database and as an identifier to update the score
     * @param telegram_id is taken from the telegram and as an identifier to update the score
     */
    public static void updateScore(String tournament_id, int hole_no, String score, long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        Connection con = connect();
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE player_score SET score_id = ? WHERE tournament_id = ? AND golfer_id = ? AND hole_no = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, score);
            ps.setString(2, tournament_id);
            ps.setString(3, golfer_id);
            ps.setString(4, String.valueOf(hole_no));
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
    /**
     * This method is for setting the pending approval
     * @param tournament_id is the identifier
     * @param telegram_id is taken from the telegram
     * @return status of the submission
     */
    public static String setPendingApproval(String tournament_id, long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        String marker_id = getMarkerID(tournament_id,golfer_id);
        Connection con = connect();
        PreparedStatement ps = null;
        if(checkMarkIsZero(golfer_id, tournament_id)){
            return "Please check your mark, you marks have lower than zero mark";
        }
        try {
            String sql = "INSERT INTO approval(golfer_id, marker_id, tournament_id) VALUES(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, golfer_id);
            ps.setString(2, marker_id);
            ps.setString(3, tournament_id);
            ps.execute();
            return "Submitted to marker ID: " +  marker_id;
        } catch (SQLException e) {
            return "Submit Failed";
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
    /**
     * This method is for checking the mark in the database whether is 0
     * @param golfer_id is take from the telegram
     * @param tournament_id as an identifier of the mark
     * @return true if the mark exist 0 in the database  or vice versa
     */
    private static boolean checkMarkIsZero(String golfer_id, String tournament_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] score = new String[18];

        try {
            String sql = "SELECT * FROM player_score WHERE golfer_id = ? AND tournament_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(golfer_id.trim()));
            ps.setString(2, tournament_id);
            rs = ps.executeQuery();

            while (rs.next()){
                for(int i = 1; i <= 18;i++){
                    if(rs.getInt("hole_no") == i){
                        score[i-1] = rs.getString("score_id");
                    }
                }
            }

            for(int i = 0; i < score.length; i++){
                if(Integer.parseInt(score[i]) < 0){
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return false;
    }
    /**
     * This method is for receiving the marker id
     * @param tournament_id is the tournament identification
     * @param golfer_id is the golfer id
     * @return the marker id
     */
    private static String getMarkerID(String tournament_id, String golfer_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT marker_id FROM player WHERE tournament_id = ? AND golfer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tournament_id);
            ps.setString(2, golfer_id);
            rs = ps.executeQuery();

            while (rs.next()){
                return rs.getString("marker_id");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return "";
    }
    /**
     * This method is for reading the pending approval
     * @param telegram_id is the telegram id
     * @return status of the approval
     */
    public static String readPendingApproval(long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        String owner = checkMarkerOwner(golfer_id);
        String tournament_id;
        String owner_id;
        if(owner.trim().equals(",")){
            return "nothing to approval.";
        }
        try{
            String[] message = owner.trim().split(",");
            tournament_id = message[0];
            owner_id = message[1];
        }catch(Exception ex){
            return "nothing here.";
        }

        String score = getGolferScore(owner_id,tournament_id);
        return "Tournament ID: " + tournament_id + "\n" + score + "\n/Yes" + "\n/No";
    }
    /**
     * This method is for checking the marker owner id
     * @param marker_id is the marker id
     * @return the marker id
     */
    private static String checkMarkerOwner(String marker_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM player WHERE marker_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, marker_id);
            rs = ps.executeQuery();

            while (rs.next()){
                return rs.getString(1)+","+rs.getString(2);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return "";
    }
    /**
     * This method is for receiving the marker owner telegram id
     * @param telegram_id is the telegram id
     * @return marker owner id (golfer id)
     */
    public static String getOwnerTelegramID(long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        String owner = checkMarkerOwner(golfer_id);
        String[] message = owner.trim().split(",");
        String owner_id = message[1];
        deleteApproval(golfer_id,owner_id);
        return checkOwnerTelegramID(owner_id);
    }

    /**
     * This method is for deleting the approval from sqlite
     * @param golfer_id is the marker id
     * @param owner_id the telegram id of the owner
     */
    private static void deleteApproval(String golfer_id, String owner_id) {
        Connection con = connect();
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM approval WHERE golfer_id = ? AND marker_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, owner_id);
            ps.setString(2, golfer_id);
            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }

    /**
     * This method is for checking marker owner
     * @param owner_id is the telegram id of the owner
     * @return the telegram id of the owner
     */
    private static String checkOwnerTelegramID(String owner_id) {
        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            String sql = "SELECT * FROM golfer WHERE golfer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(owner_id.trim()));
            rs = ps.executeQuery();

            while (rs.next()){
                result = rs.getString("telegram_id");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                if(rs != null)
                    rs.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return result;
    }
    /**
     * This method is for updating the approval status
     * @param telegram_id is taking from telegram id
     */
    public static void updateApprovalStatus(long telegram_id) {
        String golfer_id = String.valueOf(readgolferid(telegram_id));
        String owner = checkMarkerOwner(golfer_id);
        String[] message = owner.trim().split(",");
        String tournament_id = message[0];
        String owner_id = message[1];
        Connection con = connect();
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE player_score SET approval_status = ? WHERE tournament_id = ? AND golfer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "True");
            ps.setString(2, tournament_id);
            ps.setString(3, owner_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
            try{
                if(ps != null)
                    ps.close();
                con.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
