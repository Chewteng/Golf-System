package my.uum;

/**
 * This class is to set and get the data of the students (golfer id, golfer name, golfer handicap, golfer telegram id).
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class golferData {

    String golfer_id, golfer_name, telegram_id;
    int golfer_handicap;

    /**
     * This method is the constructor for golfer's data.
     *
     * @param column1 The golfer id with the format of four digit numbers only.
     * @param column2 The full name which including the first name and the last name.
     * @param column4 The name registered in the Telegram.
     */
    public golferData(String column1, String column2, String column4) {
        this.golfer_id = column1;
        this.golfer_name = column2;
        this.telegram_id = column4;
    }

    /**
     * This method is the constructor for golfer's data.
     *
     * @param column1 The golfer id with the format of four digit numbers only.
     * @param column2 The full name which including the first name and the last name.
     */
    public golferData(String column1, String column2) {
        this.golfer_id = column1;
        this.golfer_name = column2;
    }

    /**
     * This method is for getting the golfer ID.
     *
     * @return The golfer ID.
     */
    public String getGolferId() {
        return golfer_id;
    }

    /**
     * This method is for setting the golfer ID.
     *
     * @param column1 The golfer ID.
     */
    public void setGolferId(String column1) {
        this.golfer_id = column1;
    }

    /**
     * This method is for getting the golfer name.
     *
     * @return The golfer name.
     */
    public String getGolferName() {
        return golfer_name;
    }

    /**
     * This method is for setting the golfer name.
     *
     * @param column2 The golfer name.
     */
    public void setGolferName(String column2) {
        this.golfer_name = column2;
    }

    /**
     * This method is for getting the golfer handicap.
     *
     * @return The golfer handicap.
     */
    public int getGolferHandicap() {
        return this.golfer_handicap;
    }

    /**
     * This method is for setting the golfer handicap.
     *
     * @param column3 The golfer handicap.
     */
    public void setGolferHandicap(int column3) {
        this.golfer_handicap = column3;
    }

    /**
     * This method is for getting the golfer telegram ID.
     *
     * @return The golfer telegram ID.
     */
    public String getTelegramId() {
        return telegram_id;
    }

    /**
     * This method is for setting the golfer telegram ID.
     *
     * @param column4 The golfer telegram ID.
     */
    public void setTelegramId(String column4) {
        this.telegram_id = column4;
    }

}
