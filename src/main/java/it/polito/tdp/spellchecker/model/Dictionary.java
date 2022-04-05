package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {
	
	List<String> dizionario = new ArrayList<String>();
	
	public void loadDictionary(String language) {
		dizionario.clear();
		if (language.equals("English")) {
			try {
				FileReader fr = new FileReader("src/main/resources/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					dizionario.add(word.toLowerCase());
					}
				br.close();
				} catch (IOException e){
					System.out.println("Errore nella lettura del file");
					}
			} else if (language.equals("src/main/resources/Italian")) {
				try {
					FileReader fr = new FileReader("Italian.txt");
					BufferedReader br = new BufferedReader(fr);
					String word;
					while ((word = br.readLine()) != null) {
						dizionario.add(word.toLowerCase());
						}
					br.close();
					} catch (IOException e){
						System.out.println("Errore nella lettura del file");
						}
				}
		}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> lista = new LinkedList<RichWord>();
		for (String s : inputTextList) 
			if (!dizionario.contains(s))
				lista.add(new RichWord(s, dizionario.contains(s)));
		return lista;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> lista = new LinkedList<RichWord>();
		boolean corretta = false;
		for (String s : inputTextList) {
			for (String st : dizionario)
				if (s.equals(st))
					corretta = true;
			if (corretta == false)
				lista.add(new RichWord(s, corretta));
			corretta = false;
		}
		return lista;
	}

	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> lista = new LinkedList<RichWord>();
		boolean corretta = false;
		for (String s : inputTextList) {
			int min = 0;
			int max = dizionario.size();
			while (max != min) {
				int indice = min + (max - min) / 2;
				if (s.compareTo(dizionario.get(indice))==0) {
					corretta = true;
					max = min;
				}
				else if (s.compareTo(dizionario.get(indice))<0) 
					max = indice;
				else if (s.compareTo(dizionario.get(indice))>0) 
					min = indice + 1;
			}
			if (corretta == false)
				lista.add(new RichWord(s, corretta));
			corretta = false;
		}
		return lista;
	}

}
