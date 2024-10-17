package com.example.projekatrma.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class PasswordCheck {

    public static boolean isPasswordCharacterValid(String password) {
        if (password == null || password.isEmpty() || password.length() < 6) {
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        String forbiddenCharacters = "(~#^|$%&*!)";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                hasUpperCase = true;
            } else if (ch >= '0' && ch <= '9') {
                hasNumber = true;
            } else if (forbiddenCharacters.indexOf(ch) >= 0) {
                return false;
            }
        }
        return hasUpperCase && hasNumber;
    }

    public static boolean isPasswordCorrectForUser(Context context, String username, String password) {
        try {
            FileInputStream fileInputStream = context.openFileInput("users.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(username) && parts[1].equals(password)) {
                    br.close();
                    return true;
                }
            }
            br.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean savePassword(Context context,String username,String password){

        try{
            FileInputStream fileInputStream = context.openFileInput("users.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    parts[1] = password;
                    line = String.join(",", parts);
                }
                sb.append(line).append("\n");
            }
            br.close();

            String textToSave = sb.toString();
            System.out.println("Text to save: " + textToSave);
            FileOutputStream fileOutputStream = context.openFileOutput("users.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes(StandardCharsets.UTF_8));

        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    //Inicijalni korisnici
    public static void writeInitialUsers(Context context){


        try{
            FileOutputStream writer=context.openFileOutput("users.txt",Context.MODE_PRIVATE);
            String str="makelele,Makelele91\nadmin,Admin1\nuser,User1";
            writer.write(str.getBytes(StandardCharsets.UTF_8));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
