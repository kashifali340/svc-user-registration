package com.lumimindsinc.domain_name.usersregistration.utill;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Constants {



    public static final Integer SERVICE_SUCCESS_CODE = 200;
    public static final Integer SERVICE_UNSUCCESS_CODE = 202;
    public static final String SERVICE_UNSUCCESS_STATUS = "failure";

    public static final String INVALID_TOKEN_STATUS = "Invalid Token Or Expired!";

    public static final String PROJECT_ALREADY_EXIST = "PROJECT ALREADY EXIST WITH THIS CODE";
    public static final String SERVICE_SUCCESS_STATUS = "success";
    public static final String INTERNAL_SERVER_STATUS = "Internal Server Error";
    public static final Integer INTERNAL_SERVER_CODE = 500;
    public static final Integer RECORD_CREATED_CODE = 202;

    public static final Integer FILE_DOWNLOAD_FAILED = 600;
    public static final String RECORD_CREATED_STATUS = "Data Inserted Successfully";

        public static final Integer RECORD_ALREADY_EXIST_CODE = 409;
    public static final String RECORD_ALREADY_EXIST_STATUS = "Record already exist!";
    public static final String NOTIFICATION_SUBJECT_CREATE_COMPLAINT = "Create Complaint Successfully  ";
    public static final String INVALID_USER_DATA_STATUS = "BAD REQUEST!";

    public static final String ALREADY_REGISTERED_EMAIL_STATUS = "already registered email!";
    public static final Integer INVALID_USER_DATA_CODE = 400;

    public static final Integer RECORD_NOT_FOUND_CODE = 202;
    public static final String RECORD_NOT_FOUND_STATUS = "Record not found";

    public static final String EMAIL_NOT_FOUND_STATUS = "Email not found";
    public static final Integer EMAIL_NOT_FOUND_CODE = 202;
    public static final String SAME_CURRENT_PREVIOUS_PASSWORD_STATUS = "same previous and new password ";
    public static final Integer SAME_CURRENT_PREVIOUS_PASSWORD_CODE = 205;



    public static final Integer INVALID_PAGE_NUMBER_CODE = 400;
    public static final String INVALID_PAGE_NUMBER_STATUS = "Invalid Page Number";

    public static final Integer UNAUTHORIZED_REQUEST_CODE = 401;
    public static final String UNAUTHORIZED_REQUEST_STATUS = "UnAuthorized Request";

    //Contract Type
    public static final Character[] contractType = {'S', 'G', 'C'};
    //contract Status
    public static final Character[] contractStatus = {'A','F','D','T'};

    public static final Character[] serviceType = {'W','R'};




    public static final String BASE_URL = "";
    public static final String RESET_PASSWORD_URL = "";
    public static final String CHANGE_TEMPORARY_PASSWORD_URL = "";
    public static final String RESET_PASSWORD_MAIL_SUBJECT = "";
    public static final String CHANGE_TEMPORARY_PASSWORD_MAIL_SUBJECT = "Your login information for Mushko Panel";



    public static String getResetPasswordMessage(String temporaryPin) {
        return BASE_URL + RESET_PASSWORD_URL + temporaryPin;
    }

    public static String getChangeTemporaryPasswordMessage(String email, String password) {
        return "Your account has been created at: " + BASE_URL + "\n" +
                "Following are the details: " + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password;
    }

    public static boolean isEmpty(Long value) {
        boolean isEmpty = false;
        if (value == null || value == (long) 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isEmpty(String value) {
        boolean isEmpty = false;
        if (value == null || value.length() == 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static String nextDate(String date) {
        if (Constants.isEmpty(date)) {
            return null;
        }
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDate addedDate = parsedDate.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        return addedDate.toString();
    }

    public static int numOfDaysBetweenTwoTimeStamps(Timestamp time1, Timestamp time2) {
        int numOfDays = 1;
        if (time1 != null && time2 != null) {
            Date date = new Date(time1.getTime());
            Date date1 = new Date(time2.getTime());
            numOfDays = (int) ((date1.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
        } else if (time1 != null && time2 == null) {
            time2 = new Timestamp(System.currentTimeMillis());
            Date date = new Date(time1.getTime());
            Date date1 = new Date(time2.getTime());
            numOfDays = (int) ((date1.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
        }
        return numOfDays;
    }

    public static String getDateInString(Timestamp timestamp) {
        String strDate = null;
        if (timestamp != null) {
            Date date = new Date(timestamp.getTime());
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            strDate = dateFormat.format(date);
        }
        return strDate;
    }

    public static Timestamp convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String codeGenerator4Digit() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
            sb.append((char) ('0' + rnd.nextInt(10)));
        return sb.toString();
    }

    public static String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
//customMadeByShifali
    private static String[] userRoles = {"Employee", "HiringHR"};
    public static String getHiringManagerRole() {
        return userRoles[1];
    }

}
