package fr.mb.bananesexport.controller;

import fr.mb.bananesexport.exception.BananesExportException;
import fr.mb.bananesexport.model.Commande;
import fr.mb.bananesexport.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinataires/{destId}/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Commande> getCommandesDestinataire(@PathVariable int destId) throws BananesExportException{
        return commandeService.findAllCommandes(destId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Commande createCommande(@RequestBody Commande commande, @PathVariable int destId) throws BananesExportException {
            return commandeService.createCommande(commande, destId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commande, @PathVariable int destId, @PathVariable int id) throws BananesExportException {
        Commande finalCommande = commandeService.updateCommande(commande, destId, id);
        HttpStatus httpStatus = (finalCommande.isAlreadyExists() ? HttpStatus.OK : HttpStatus.CREATED);
        return new ResponseEntity<>(finalCommande, httpStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDestinataire(@PathVariable int destId, @PathVariable int id) throws BananesExportException {
        commandeService.deleteCommande(destId, id);
    }
}
