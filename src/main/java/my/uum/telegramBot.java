package my.uum;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This class is the main structure of STIW3054_A202_GoalDiggers_bot and it will receive the input through Telegram Bots and return back the result.
 *
 * @author Yong Kai Yi, Lai Yip Hang, Lim Wei Yi, H'ng Chew Teng
 */
public class telegramBot extends TelegramLongPollingBot {
    public boolean start_status = false;
    public boolean register_status = false;
    public boolean score_status = false;
    public boolean approvalStatus = false;

    /**
     * This method is to read the bot username.
     *
     * @return The registered bot username.
     */
    @Override
    public String getBotUsername() {
        return "STIW3054_A202_GoalDiggers_bot";
    }

    /**
     * This method is to read the bot token from BotFather.
     *
     * @return The registered bot token.
     */
    @Override
    public String getBotToken() {
        return "1818929604:AAHxIZHiYz86SHFb7UDgHBZbOo69XauvQIk";
    }

    /**
     * This method is to reply the input message through Telegram Bots and send back the output.
     *
     * @param update The new input through Telegram Bots
     */
    @Override
    public void onUpdateReceived(Update update) {
        new Thread(() -> {
            if (update.hasMessage() && update.getMessage().hasText()) {

                String msg = update.getMessage().getText();
                String telegram_first_name = update.getMessage().getChat().getFirstName();
                String telegram_last_name = update.getMessage().getChat().getLastName();
                long telegram_id = update.getMessage().getChatId();

                if (msg.contains("/start") || msg.contains("/cancelReg")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("Welcome!\n\n" +
                                    "#Menu#\n" +
                                    "/register\n" +
                                    "/tournament\n" +
                                    "/record\n" +
                                    "/approval")
                            .build();
                    start_status = true;
                    executeMessageMethod(message);

                } else if (!start_status) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("Please enter /start to continue.")
                            .build();
                    executeMessageMethod(message);
                } else if (msg.contains("/register")) {
                    if (dbConnection.verifyRegister(telegram_id)) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("You are register already!" + "\n" + dbConnection.golferInformation(telegram_id))
                                .build();
                        executeMessageMethod(message);

                        message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Welcome!\n\n" +
                                        "#Menu#\n" +
                                        "/register\n" +
                                        "/tournament\n" +
                                        "/record\n" +
                                        "/approval")
                                .build();
                        start_status = true;
                        executeMessageMethod(message);

                    } else {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Golfer Name: " + telegram_first_name + " " + telegram_last_name + "\n/handicap [handicap number(0-24)] Enter your handicap to register.\n/cancelReg To cancel Registration")
                                .build();
                        executeMessageMethod(message);
                        register_status = true;
                    }

                } else if (msg.startsWith("/handicap") && register_status) {
                    String cap = msg.substring((msg.indexOf("cap") + 3));
                    if (cap.equals("")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for handicap (e.g. /handicap 18)")
                                .build();
                        executeMessageMethod(message);
                    } else if (Integer.parseInt(cap.trim()) > 24 || Integer.parseInt(cap.trim()) < 0) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for handicap (e.g. /handicap [0-24])")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        int handicap = Integer.parseInt(cap.trim());
                        dbConnection.insertGolfer(telegram_first_name + " " + telegram_last_name, handicap, telegram_id);
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Register Successful")
                                .build();
                        executeMessageMethod(message);

                        message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Welcome!\n\n" +
                                        "#Menu#\n" +
                                        "/register\n" +
                                        "/tournament\n" +
                                        "/record\n" +
                                        "/approval")
                                .build();
                        start_status = true;
                        register_status = false;
                        executeMessageMethod(message);
                    }
                } else if (msg.equals("/tournament")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("Tournament Page\n" +
                                    "/view view tournament\n" +
                                    "/take_part [Tournament ID] enter the tournament ID to take part tournament\n" +
                                    "/viewParticipant [Tournament ID] enter the tournament ID to view the participant in tournament\n" +
                                    "/viewTournament view included tournament information")
                            .build();
                    executeMessageMethod(message);

                } else if (msg.equals("/view")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text(dbConnection.readtournament())
                            .build();
                    executeMessageMethod(message);
                } else if (msg.startsWith("/take_part")) {
                    String art = msg.substring((msg.indexOf("art") + 3));
                    if (art.equals(" ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for tournament ID (e.g. /take_part 22)")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        String tournament_id = art.trim();

                        // verify tournament
                        if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /take_part 20)")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            //check telegram user register or not
                            if (dbConnection.readgolferid(telegram_id) == 0) {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(Long.toString(telegram_id))
                                        .text("Please register first to continue.")
                                        .build();
                                executeMessageMethod(message);
                            } else {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(Long.toString(telegram_id))
                                        .text(dbConnection.take_part(tournament_id, dbConnection.readgolferid(telegram_id)))
                                        .build();
                                executeMessageMethod(message);
                                message = SendMessage
                                        .builder()
                                        .chatId(Long.toString(telegram_id))
                                        .text("Welcome!\n\n" +
                                                "#Menu#\n" +
                                                "/register\n" +
                                                "/tournament\n" +
                                                "/record\n" +
                                                "/approval")
                                        .build();
                                start_status = true;
                                executeMessageMethod(message);
                            }
                        }
                    }

                } else if (msg.contains("/viewParticipant")) {
                    String ant = msg.substring((msg.indexOf("ant") + 3));
                    if (ant.equals(" ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for tournament ID (e.g. /viewParticipant 22)")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        String tournament_id = ant.trim();

                        // verify tournament
                        if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /viewParticipant 20)")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text(dbConnection.who_take_part(tournament_id))
                                    .build();
                            executeMessageMethod(message);
                        }
                    }
                } else if (msg.equals("/viewTournament")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text(dbConnection.viewtournament(telegram_id))
                            .build();
                    executeMessageMethod(message);
                } else if (msg.contains("/record")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("Score Page\n" +
                                    "/viewScore [tournament ID] view tournament's score\n" +
                                    "/setMarker [tournament ID] [Marker ID (Another Golfer ID)] set marker ID in each tournament, 1 marker can mark 1 golfer\n" +
                                    "/setScore [Tournament ID] [hole No] [Score] to set score\n" +
                                    "/submitScore [Tournament ID] Submit the tournament Score to marker and pending approval"
                            )
                            .build();
                    executeMessageMethod(message);
                    score_status = true;
                } else if (msg.startsWith("/viewScore")) {

                    String ore = msg.substring((msg.indexOf("ore") + 3));
                    if (ore.equals(" ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for tournament ID (e.g. /viewScore 22)")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        String tournament_id = ore.trim();

                        // verify tournament
                        if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /viewScore 20)")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text(dbConnection.tournament_hole_mark(tournament_id))
                                    .build();
                            executeMessageMethod(message);
                        }
                    }
                } else if (msg.startsWith("/setMarker")) {
                    String ker = msg.substring((msg.indexOf("ker") + 3));
                    if (ker.equals(" ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for marker ID (e.g. /setMarker 20 7)")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        String marker_id = "";
                        String tournament_id = "";

                        try {
                            String[] messages = ker.trim().split(" ");
                            marker_id = messages[1];
                            tournament_id = messages[0];
                        } catch (Exception ex) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /setMarker 20 7)")
                                    .build();
                            executeMessageMethod(message);
                        }

                        if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /setMarker 20 7)")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.isInthisTournament(tournament_id, telegram_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("You are not in this tournament.")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.verifymarkerid(tournament_id, marker_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid marker ID, please input an exist marker ID(e.g. /setMarker 20 7)")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.samemarkerid(tournament_id, marker_id, telegram_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("This marker ID has been used by you in this tournament.")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.verifyrepliedmarkerid(tournament_id, marker_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("This marker ID has been used by another player in this tournament.")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            dbConnection.setMarkerID(tournament_id, marker_id, telegram_id);
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Added. Marker ID: " + marker_id)
                                    .build();
                            executeMessageMethod(message);
                        }
                    }
                } else if (msg.startsWith("/setScore") && score_status) {
                    String ore = msg.substring((msg.indexOf("ore") + 3));
                    String score;
                    int hole_no;
                    String tournament_id;

                    try {
                        String[] messages = ore.trim().split(" ");
                        score = messages[2];
                        hole_no = Integer.parseInt(messages[1]);
                        tournament_id = messages[0];

                        if (hole_no > 18 || hole_no < 1) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid hole number(1-18)(e.g. /setScore 20 18 3)")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /setScore 20 18 3)")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.isInthisTournament(tournament_id, telegram_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("You are not in this tournament.")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            dbConnection.updateScore(tournament_id, hole_no, score, telegram_id);
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Updated hole " + hole_no + " to " + score)
                                    .build();
                            executeMessageMethod(message);

                            message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text(dbConnection.tournament_hole_mark(tournament_id))
                                    .build();
                            executeMessageMethod(message);
                        }

                    } catch (Exception ex) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid command, please input an valid command(e.g. /setScore 20 18 3)")
                                .build();
                        executeMessageMethod(message);
                    }
                } else if (msg.startsWith("/submitScore")) {
                    String ore = msg.substring((msg.indexOf("ore") + 3));
                    if (ore.equals(" ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(Long.toString(telegram_id))
                                .text("Invalid input for tournament ID (e.g. /submitScore 22)")
                                .build();
                        executeMessageMethod(message);
                    } else {
                        String tournament_id = ore.trim();

                        // verify tournament
                        if (!dbConnection.verifytournamentid(tournament_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("Invalid tournament ID, please input an exist tournament ID(e.g. /submitScore 20)")
                                    .build();
                            executeMessageMethod(message);
                        } else if (!dbConnection.isInthisTournament(tournament_id, telegram_id)) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text("You are not in this tournament.")
                                    .build();
                            executeMessageMethod(message);
                        } else {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(Long.toString(telegram_id))
                                    .text(dbConnection.setPendingApproval(tournament_id, telegram_id))
                                    .build();
                            executeMessageMethod(message);
                            score_status = false;
                        }
                    }
                } else if (msg.startsWith("/approval")) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text(dbConnection.readPendingApproval(telegram_id))
                            .build();
                    executeMessageMethod(message);
                    approvalStatus = true;
                } else if (msg.startsWith("/Yes") && approvalStatus) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(dbConnection.getOwnerTelegramID(telegram_id))
                            .text("Approved")
                            .build();
                    executeMessageMethod(message);
                    dbConnection.updateApprovalStatus(telegram_id);
                    message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("You are accept the approval.")
                            .build();
                    executeMessageMethod(message);
                    approvalStatus = false;
                } else if (msg.startsWith("/No") && approvalStatus) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(dbConnection.getOwnerTelegramID(telegram_id))
                            .text("REJECTED, PLEASE CHECK YOUR MARK AND RESUBMIT.")
                            .build();
                    executeMessageMethod(message);
                    message = SendMessage
                            .builder()
                            .chatId(Long.toString(telegram_id))
                            .text("You are reject the approval.")
                            .build();
                    executeMessageMethod(message);
                    approvalStatus = false;
                }
            }
        }).start();
    }

    /**
     * This method is to execute the input message through Telegram Bots.
     *
     * @param message The input message.
     */
    private void executeMessageMethod(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

}