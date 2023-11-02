package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class UserData {
    String lastName;
    String firstName;
    String middleName;
    Date birthDate;
    long phoneNumber;
    char gender;

    UserData(String lastName, String firstName, String middleName, Date birthDate, long phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}

public class UserInputApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные в произвольном порядке (Фамилия Имя Отчество, Дата Рождения, Телефон, Пол):");
            String input = scanner.nextLine();

            String[] data = input.split("\\s+");

            if (data.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Введите все 6 параметров.");
            }

            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            Date birthDate = parseDate(data[3]);
            long phoneNumber = parsePhoneNumber(data[4]);
            char gender = parseGender(data[5]);

            UserData userData = new UserData(lastName, firstName, middleName, birthDate, phoneNumber, gender);

            String fileName = lastName + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(userData.lastName + " " +  userData.firstName + " " + userData.middleName + " " +
                        new SimpleDateFormat("dd.MM.yyyy").format(userData.birthDate) +
                        " " + userData.phoneNumber + " " + userData.gender + "\n");
                System.out.println("Данные успешно записаны в файл: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.parse(dateStr);
    }

    private static long parsePhoneNumber(String phoneStr) throws NumberFormatException {
        return Long.parseLong(phoneStr);
    }

    private static char parseGender(String genderStr) throws IllegalArgumentException {
        char gender = genderStr.charAt(0);
        if (gender != 'f' && gender != 'm') {
            throw new IllegalArgumentException("Неверное значение для пола. Используйте 'f' или 'm'.");
        }
        return gender;
    }
}
