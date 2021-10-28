package com.prenotazioni.biglietto.Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import com.prenotazioni.biglietto.Entity.Sala;
import com.prenotazioni.biglietto.Entity.Spettacolo;
import com.prenotazioni.biglietto.Entity.User;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoPosto;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSala;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoSpettacolo;
import com.prenotazioni.biglietto.Exceptions.ExceptionNoUser;
import com.prenotazioni.biglietto.Repository.PostoRepository;
import com.prenotazioni.biglietto.Repository.SalaRepository;
import com.prenotazioni.biglietto.Repository.SpettacoloRepository;
import com.prenotazioni.biglietto.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BigliettoServiceQuery {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SpettacoloRepository spettacoloRepository;

	@Autowired
	SalaRepository salaRepository;

	@Autowired
	PostoRepository postoRepository;

	//variabili per recuperare le immagini e dove lasiacre il pdf
    final String file = "C:\\Users\\Utente\\Desktop\\Tirocinio\\progetto\\Apache_Kafka\\biglietto\\src\\main\\resources\\biglietto.pdf";
    final String imageCiackSiGira = "C:\\Users\\Utente\\Desktop\\Tirocinio\\progetto\\Apache_Kafka\\biglietto\\src\\main\\resources\\ciack.jpg";
	final String imagePopCorn = "C:\\Users\\Utente\\Desktop\\Tirocinio\\progetto\\Apache_Kafka\\biglietto\\src\\main\\resources\\popCorn.jpg";

    /**
     * Genera il biglietto per uno show dati i parametri
	 * Il settaggio dei soldi e del posto sono lasciati all'altro microservizio, perchè questa è una tabella di sola lettura
	 * Qui ritorna solamente il pdf senza fare controlli se il posto è già occupato o se ci sono abbastanza soldi, perchè lo fa l'altro microservizio
     * @param name
     * @param surname
     * @param cost
     * @param spettacolo
     * @param posto
     * @param numeroSala
     * @throws DocumentException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MalformedURLException
	 * @return
     */
	public ByteArrayInputStream create_PDF(int id, String spettacolo, int colonna, String riga, int numeroSala) throws ExceptionNoUser, ExceptionNoSala, ExceptionNoSpettacolo,
	  		DocumentException, IOException, ExceptionNoPosto{	
		
		Optional<User> userr = userRepository.findById(id);
		Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(spettacolo);
		if(userr.isPresent() && spett.isPresent()){
			User user = userr.get();
			Spettacolo show = spett.get();

			//controllo se esiste la sala
			List<Sala> sale = show.getSala();
			boolean flag = false;
			Sala sala = new Sala();
			for(int i = 0; i < sale.size(); i++){
				if(sale.get(i).getNumeroSala() == numeroSala){
					flag = true;
					sala = sale.get(i);
					break;
				}
			}
			if(flag == false){
				throw new ExceptionNoSala();
			}

			//controllo se esiste il posto nella sala
			flag = false;
			for(int i = 0; i < sala.getPosto().size(); i++){
				if((colonna == sala.getPosto().get(i).getColonna()) & (riga.equals(sala.getPosto().get(i).getRiga()))){
					flag = true;
					break;
				}
			}
			if(flag == false){
				throw new ExceptionNoPosto();
			}

			//prendo i parametri necessari
			int cost = show.getCosto();
			String posto = riga.toUpperCase() + colonna;
			String name = user.getName();
			String surname = user.getSurname();
			
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

			return new ByteArrayInputStream(out.toByteArray());
			
	    }else if(userr.isPresent() == false){
			throw new ExceptionNoUser();
		}else{
			throw new ExceptionNoSpettacolo();
		}
	}
}