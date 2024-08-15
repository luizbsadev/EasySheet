package org.luiz;


import org.luiz.conf.SheetFactory;
import org.luiz.services.BackupService;
import org.luiz.services.PathService;
import org.luiz.services.SheetService;
import org.luiz.view.Tela;


import java.io.IOException;
import java.text.ParseException;



public class Main {
    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        PathService.configurar();
        String caminho = PathService.getPath();
        SheetService.setArquivo(caminho);
        BackupService.fazerBackup();

        Tela.mostrar();

        SheetFactory.close();



    }

}