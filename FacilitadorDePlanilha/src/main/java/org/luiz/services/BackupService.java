package org.luiz.services;

import org.luiz.conf.SheetFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BackupService {

    public static void fazerBackup() throws IOException {
        try {
            criarPasta();
            FileOutputStream backup = new FileOutputStream("\\backup\\backup.xlsx");
            SheetFactory.getWb().write(backup);
        }catch (NullPointerException e){
            System.out.println("Não foi possivel fazer o backup pois o arquivo não foi encontrado");
        }
    }

    private static void criarPasta(){
        File theDir = new File("\\backup");
        theDir.mkdir();
    }
}
