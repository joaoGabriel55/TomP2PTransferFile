package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

	public static String getFile(String path) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File fileTemp = new File(path);
		String name = fileTemp.getName();
		FileReader file = new FileReader(fileTemp);
		String conteudo = null;
		
		try (BufferedReader br = new BufferedReader(file)) {
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				conteudo = sCurrentLine;
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return name + ";" + conteudo;
	}

}
