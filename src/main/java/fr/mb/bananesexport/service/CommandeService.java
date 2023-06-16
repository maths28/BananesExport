package fr.mb.bananesexport.service;

import fr.mb.bananesexport.exception.*;
import fr.mb.bananesexport.model.Commande;
import fr.mb.bananesexport.repository.CommandeRepository;
import fr.mb.bananesexport.repository.DestinataireRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private DestinataireRepository destinataireRepository;

    @Value("${delayLivraisonDays}")
    private int delayLivraisonDays;

    @Value("${minQuantite}")
    private int minQuantite;

    @Value("${maxQuantite}")
    private int maxQuantite;

    @Value("${intervalleQuantite}")
    private int intervalleQuantite;

    @Value("${prixKilo}")
    private double prixKilo;

    public List<Commande> findAllCommandes(int destId) throws BananesExportException{
        if (destinataireRepository.existsById(destId)){
            return commandeRepository.findCommandesByDestinataireId(destId);
        } else {
            throw new UnknownDestinataire(destId);
        }
    }

    public Commande createCommande(Commande commande, int destId) throws BananesExportException {
        commande.setDestinataireId(destId);
        this.checkCommandeValidity(commande);
        return commandeRepository.save(commande);
    }

    public Commande updateCommande(Commande commande, int destId, int commandeId) throws BananesExportException {
        commande.setDestinataireId(destId);
        this.checkCommandeValidity(commande);
        Optional<Commande> existCommandeOpt = commandeRepository.findById(commandeId);
        if(existCommandeOpt.isPresent()){
            Commande existCommande = existCommandeOpt.get();
            existCommande.setDestinataireId(commande.getDestinataireId());
            existCommande.setDate(commande.getDate());
            existCommande.setPrix(commande.getPrix());
            existCommande.setQuantite(commande.getQuantite());
            commandeRepository.save(existCommande);
            existCommande.setAlreadyExists(true);
            return existCommande;
        } else {
            commandeRepository.save(commande);
            commande.setAlreadyExists(false);
            return commande;
        }
    }

    public void deleteCommande(int destinataireId, int id) throws BananesExportException{
        if(!this.commandeRepository.existsByIdAndDestinataireId(id, destinataireId)){
            throw new UnknownCommandeException(id, destinataireId);
        }
        this.commandeRepository.deleteByIdAndDestinataireId(id, destinataireId);
    }

    private void checkCommandeValidity(Commande commande) throws BananesExportException{
        if (!destinataireRepository.existsById(commande.getDestinataireId())){
            throw new UnknownDestinataire(commande.getDestinataireId());
        }
        if(commande.anyNull()){
            throw new MissingInfoException(commande);
        }
        if(!commande.getDate().matches("^([0-2][0-9]|3[0-1])(-)(0[0-9]|1[0-2])(-)(\\d{4})$")){
            throw new UnknownDateLivraisonException();
        }
        try {
            Date dateLivraison = DateUtils.parseDate(commande.getDate(), "dd-MM-yyyy");
            Date dateDebutAutorise = new Date();
            dateDebutAutorise = DateUtils.addDays(dateDebutAutorise, this.delayLivraisonDays);
            dateDebutAutorise = DateUtils.setHours(dateDebutAutorise, 0);
            dateDebutAutorise = DateUtils.setMinutes(dateDebutAutorise, 0);
            dateDebutAutorise = DateUtils.setSeconds(dateDebutAutorise, 0);
            dateDebutAutorise = DateUtils.setMilliseconds(dateDebutAutorise, 0);
            if (dateDebutAutorise.after(dateLivraison)){
                throw new WrongDateLivraisonException(this.delayLivraisonDays);
            }
        } catch (ParseException e){
        }

        if(commande.getQuantite() < this.minQuantite || commande.getQuantite() > this.maxQuantite || commande.getQuantite() % intervalleQuantite != 0) {
            throw new QuantiteException(this.minQuantite, this.maxQuantite, this.intervalleQuantite);
        }

        commande.setPrix(commande.getQuantite() * this.prixKilo);
    }

}
