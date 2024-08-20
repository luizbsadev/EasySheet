package org.luiz.services;

import java.io.*;

public class PathService {

    private static File filePath;

    public static void configurar() throws IOException {
        criarPasta();
        criarArquivo();
    }

    public static String getPath() throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String path = bufferedReader.readLine();
        bufferedReader.close();

        return path;
    }

    public static void setPath(String newPath) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write(newPath);
        bufferedWriter.close();
    }
    private static void criarPasta() {
        File theDir = new File("\\path");
        theDir.mkdir();
    }
    private static void criarArquivo() throws IOException {
        filePath = new File("\\path\\path.txt");
        filePath.createNewFile();
    }

}
