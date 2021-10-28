package com.prenotazioni.biglietto.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prenotazioni.biglietto.Config.PostoConfig;
import com.prenotazioni.biglietto.Config.SalaConfig;
import com.prenotazioni.biglietto.Config.SpettacoloConfig;
import com.prenotazioni.biglietto.Config.UserConfig;
import com.prenotazioni.biglietto.Entity.Posto;
import com.prenotazioni.biglietto.Entity.Sala;
import com.prenotazioni.biglietto.Entity.Spettacolo;
import com.prenotazioni.biglietto.Entity.User;
import com.prenotazioni.biglietto.Repository.PostoRepository;
import com.prenotazioni.biglietto.Repository.SalaRepository;
import com.prenotazioni.biglietto.Repository.SpettacoloRepository;
import com.prenotazioni.biglietto.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	SpettacoloRepository spettacoloRepository;

	@Autowired
	PostoRepository postoRepository;

	@Autowired
	SalaRepository salaRepository;

	/**
	 * Consuma i messaggi nel topic User o User-delete, agisce da biglietto-0
	 * 
	 * @param data
	 */
	@KafkaListener(groupId = "biglietto-0", containerFactory = "userKafkaListenerContainerFactory", topics = "User")
	public void getTopicUserMessage(UserConfig data) {
		User user = new User(data.getId(), data.getName(), data.getSurname(), data.getCash());
		if (data.getAction().equals("Delete")) {
			userRepository.delete(user);
			System.out.println("USER DELETE CORRECTLY");
		} else {
			userRepository.save(user);
			if (data.getAction().equals("Add New")) {
				System.out.println("USER ADD CORRECTLY");
			} else {
				System.out.println("USER UPSET CORRECTLY");
			}
		}
	}

	/**
	 * Consuma i messaggi del topic Spettacolo o Spettacolo-delete, agisce da
	 * biglietto-1
	 * 
	 * @param data
	 */
	@Transactional
	@KafkaListener(groupId = "biglietto-1", containerFactory = "spettacoloKafkaListenerContainerFactory", topics = "Spettacolo")
	public void getTopicSpettacoloMessage(SpettacoloConfig data) {
		if (data.getAction().equals("Delete Show")) {
			Spettacolo spettacolo = new Spettacolo(data.getSpettacolo(), data.getCosto());
			spettacoloRepository.delete(spettacolo);
			System.out.println("DELETE SHOW CORRECTLY");

		} else if (data.getAction().equals("Upset Posto")) {
			Optional<Spettacolo> spett = spettacoloRepository.findBySpettacolo(data.getSpettacolo());
			if (spett.isPresent()) {
				Spettacolo s = spett.get();
				for (SalaConfig salaConfig : data.getSala()) {
					for (Sala sala : s.getSala()) {
						if (sala.getNumeroSala() == salaConfig.getNumeroSala()) {
							for (PostoConfig postoConfig : salaConfig.getPosto()) {
								for (Posto posto : sala.getPosto()) {
									if ((postoConfig.getColonna() == posto.getColonna())
											&& (postoConfig.getRiga().equals(posto.getRiga()))
											&& (posto.getFree() != postoConfig.getFree())) {
										posto.setFree(postoConfig.getFree());
										postoRepository.save(posto);
									}
								}
							}
						}
					}
				}
			}
			System.out.println("POSTO UPSET CORRECTLY");
		} else {

			List<Sala> listOfSala = new ArrayList<>();
			List<Posto> listOfPosto = new ArrayList<>();

			Spettacolo show = new Spettacolo(data.getSpettacolo(), data.getCosto());
			spettacoloRepository.save(show);
			for (SalaConfig salaConfig : data.getSala()) {
				listOfPosto = new ArrayList<>();
				Sala sala = new Sala(salaConfig.getNumeroSala(), show);
				listOfSala.add(sala);

				// la sala non è presente perchè lo show non lo era, la salvo per fare azioni poi sul posto e aggiornarla alla fine
				salaRepository.save(sala);

				for (PostoConfig postoConfig : salaConfig.getPosto()) {
					Posto posto = new Posto(postoConfig.getRiga(), postoConfig.getColonna(), postoConfig.getFree(), sala);
					listOfPosto.add(posto);

					// non c'è già la sala quindi nemmeno il posto!
					postoRepository.save(posto);
				}
				sala.setPosto(listOfPosto);
				salaRepository.save(sala);

			}
			show.setSala(listOfSala);
			spettacoloRepository.save(show);

			if (data.getAction().equals("Add New")) {
				System.out.println("SHOW ADD CORRECTLY");
			} else {
				System.out.println("SHOW UPSET CORRECTLY");
			}
		}
	}
}