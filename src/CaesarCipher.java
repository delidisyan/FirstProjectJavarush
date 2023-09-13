import java.io.*;
import java.util.Scanner;

public class CaesarCipher {

    public static final String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";

    // Метод шифрования
    public static String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            int index = RUSSIAN_ALPHABET.indexOf(ch);
            if (index != -1) {
                int newIndex = (index + key + RUSSIAN_ALPHABET.length()) % RUSSIAN_ALPHABET.length();
                encryptedText.append(RUSSIAN_ALPHABET.charAt(newIndex));
            } else {
                encryptedText.append(ch);
            }
        }
        return encryptedText.toString();
    }

    // Метод расшифровки
    public static String decrypt(String encryptedText, int key) {
        return encrypt(encryptedText, -key);
    }

    public static void bruteForceDecrypt(String encryptedText) {
        for (int key = 1; key <= RUSSIAN_ALPHABET.length(); key++) {
            String decryptedText = decrypt(encryptedText, key);
            System.out.println("Key " + key + ": " + decryptedText);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);

        System.out.print("Выберите режим (1 - Шифрование, 2 - Расшифровка, 3 - Криптоанализ): ");
        int mode = Integer.parseInt(reader.readLine());

        if (mode == 1) {
            System.out.print("Введите путь к текстовому файлу с исходным текстом: ");
            String inputFilePath = scanner.nextLine();
            System.out.print("Введите ключ (целое число): ");
            int key = Integer.parseInt(reader.readLine());

            StringBuilder inputText = new StringBuilder();
            try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFilePath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    inputText.append(line);
                }
            }
            String encryptedText = encrypt(inputText.toString(), key);

            System.out.print("Введите путь для сохранения зашифрованного текста: ");
            String outputPath = scanner.nextLine();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                writer.write(encryptedText);
            }
            System.out.println("Зашифрованный текст сохранен в файл '" + outputPath + "'");
        } else if (mode == 2) {
            System.out.print("Введите путь к файлу с зашифрованным текстом: ");
            String encryptedFilePath = scanner.nextLine();
            System.out.print("Введите ключ (целое число): ");
            int key = Integer.parseInt(reader.readLine());

            StringBuilder encryptedText = new StringBuilder();
            try (BufferedReader fileReader = new BufferedReader(new FileReader(encryptedFilePath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    encryptedText.append(line);
                }
            }
            String decryptedText = decrypt(encryptedText.toString(), key);
            System.out.println("Расшифрованный текст:\n" + decryptedText);
        } else if (mode == 3) {
            System.out.print("Введите путь к файлу с зашифрованным текстом для криптоанализа: ");
            String encryptedFilePath = scanner.nextLine();

            StringBuilder encryptedText = new StringBuilder();
            try (BufferedReader fileReader = new BufferedReader(new FileReader(encryptedFilePath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    encryptedText.append(line);
                }
            }
            bruteForceDecrypt(encryptedText.toString());
        } else {
            System.out.println("Некорректный выбор режима.");
        }
    }
}


