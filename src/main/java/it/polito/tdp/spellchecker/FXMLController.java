package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblTime;

    @FXML
    private TextArea txtText;

    @FXML
    private TextArea txtWrongWords;

    @FXML
    void doClearText(ActionEvent event) {
    	txtText.clear();
    	txtWrongWords.clear();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	txtWrongWords.clear();
    	String testo = txtText.getText();
    	String lingua = cmbLanguage.getValue();
    	if ( testo=="" || lingua==null)
    		System.out.println("Inserire testo e selezionare lingua.");
    	model.loadDictionary(lingua);
    	String testoMinuscolo = testo.toLowerCase();
    	String testoSenzaPunteggiatura = testoMinuscolo.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	String array[] = testoSenzaPunteggiatura.split(" ");
    	//List<String> lista = new ArrayList<String>();
    	List<String> lista = new LinkedList<String>();
    	for (int i=0; i<array.length; i++)
    		lista.add(array[i]);
    	long start = System.nanoTime();
    	//List<RichWord> errori = model.spellCheckText(lista);
    	//List<RichWord> errori = model.spellCheckTextLinear(lista);
    	List<RichWord> errori = model.spellCheckTextDichotomic(lista);
    	for (RichWord rw : errori)
    		txtWrongWords.appendText(rw.toString()+"\n");
    	long end = System.nanoTime();
    	lblErrors.setText("The text contains " + errori.size() + " errors");
    	lblTime.setText("Spell check completed in " + (end-start)/1E9 + " seconds");
    }

    @FXML
    void initialize() {
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtText != null : "fx:id=\"txtText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtWrongWords != null : "fx:id=\"txtWrongWords\" was not injected: check your FXML file 'Scene.fxml'.";
    }

	public void setModel(Dictionary model) {
		this.model = model;
		
		cmbLanguage.getItems().clear();
		cmbLanguage.getItems().addAll("English","Italian");
		txtText.clear();
		
		txtWrongWords.clear();
	}

}
