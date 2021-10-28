package com.prenotazioni.biglietto.Services;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class GenerateService {

    final String imageCiackSiGira = "C:\\Users\\Utente\\Desktop\\Tirocinio\\progetto\\RPI\\biglietto\\src\\main\\resources\\ciack.jpg";
    final String imagePopCorn = "C:\\Users\\Utente\\Desktop\\Tirocinio\\progetto\\RPI\\biglietto\\src\\main\\resources\\popCorn.jpg";

    /**
     * Genere un pdf e lo mostra
     * @param name: nome dell'utente
     * @param surname: cognome dell'utente
     * @param cost: costo dello show
     * @param spettacolo: titolo dello show
     * @param posto: posto prenotato
     * @param numeroSala: numero della sala
     * @return ritorna un pdf 
     * @throws DocumentException
     * @throws FileNotFoundException: non trova i file messi per le immagini
     * @throws IOException
     * @throws MalformedURLException
     */
	public ByteArrayOutputStream createAndSee_PDF(String name, String surname, float cost, String spettacolo, String posto, int numeroSala) throws DocumentException, FileNotFoundException, IOException, MalformedURLException{

		//immagini e loro format
		Image image1 = Image.getInstance(imageCiackSiGira);
		Image image2 = Image.getInstance(imagePopCorn);
		image1.scaleAbsolute(50, 35);
		image2.scaleAbsolute(50, 100);

		//tabella per immagin image1 da inserire all'inizio
		PdfPTable table = new PdfPTable(6);
		PdfPCell cell1 = new PdfPCell(image1);
		cell1.setBorderColor(BaseColor.WHITE);
		PdfPCell cell2 = new PdfPCell(image1);
		cell2.setBorderColor(BaseColor.WHITE);	
		PdfPCell cell3 = new PdfPCell(image1);
		cell3.setBorderColor(BaseColor.WHITE);	
		PdfPCell cell4 = new PdfPCell(image1);
		cell4.setBorderColor(BaseColor.WHITE);
		PdfPCell cell5 = new PdfPCell(image1);
		cell5.setBorderColor(BaseColor.WHITE);
		PdfPCell cell6 = new PdfPCell(image1);
		cell6.setBorderColor(BaseColor.WHITE);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);

		//tabella per immagini popCorn da inserire alla fine
		PdfPTable table2 = new PdfPTable(6);
		PdfPCell cell11 = new PdfPCell(image2);
		cell11.setBorderColor(BaseColor.WHITE);
		PdfPCell cell12 = new PdfPCell(image2);
		cell12.setBorderColor(BaseColor.WHITE);	
		PdfPCell cell13 = new PdfPCell(image2);
		cell13.setBorderColor(BaseColor.WHITE);	
		PdfPCell cell14 = new PdfPCell(image2);
		cell14.setBorderColor(BaseColor.WHITE);
		PdfPCell cell15 = new PdfPCell(image2);
		cell15.setBorderColor(BaseColor.WHITE);
		PdfPCell cell16 = new PdfPCell(image2);
		cell16.setBorderColor(BaseColor.WHITE);
		table2.addCell(cell11);
		table2.addCell(cell12);
		table2.addCell(cell13);
		table2.addCell(cell14);
		table2.addCell(cell15);
		table2.addCell(cell16);

		//Tabella con il testo
		Font font = new Font();
		PdfPTable tableTesto1 = new PdfPTable(1);
		font.setSize(30);
		font.setColor(BaseColor.BLACK);
		font.setStyle(Font.BOLD);
		Phrase phrase = new Phrase("BIGLIETTO PER LO SPETTACOLO: \n", font);
		PdfPCell cellTesto1 = new PdfPCell(phrase);
		cellTesto1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTesto1.setVerticalAlignment(Element.ALIGN_CENTER);
		cellTesto1.setBorderColor(BaseColor.WHITE);
		tableTesto1.addCell(cellTesto1);

		//creazione vera e propria del documento
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);	
		document.add(tableTesto1);
		font.setSize(10);
		document.add(new Paragraph("\n", font));
		font.setSize(30);

		Phrase phrase1 = new Phrase(spettacolo.toUpperCase() + "\n", font);
		cellTesto1.setPhrase(phrase1);
		PdfPTable tabelTesto2 = new PdfPTable(1);
		tabelTesto2.addCell(cellTesto1);
		document.add(tabelTesto2);
		font.setSize(20);
		font.setStyle(Font.NORMAL);

		Phrase phrase2 = new Phrase("Valido a nome di: " + name.toUpperCase() + " " + surname.toUpperCase() +"\n\nPosto: " + posto.toUpperCase() + "  Sala: " +numeroSala+"           costo: " + cost + "\n\n", font);
		cellTesto1.setPhrase(phrase2);
		PdfPTable tableTesto3 = new PdfPTable(1);
		tableTesto3.addCell(cellTesto1);
		document.add(tableTesto3);
		document.add(table2);

		//close
		document.close();

		return out; 
	}
	
}