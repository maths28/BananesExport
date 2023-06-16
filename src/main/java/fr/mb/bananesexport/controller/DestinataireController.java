package fr.mb.bananesexport.controller;

import fr.mb.bananesexport.exception.BananesExportException;
import fr.mb.bananesexport.model.Destinataire;
import fr.mb.bananesexport.service.DestinataireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinataires")
public class DestinataireController {

    @Autowired
    private DestinataireService destinataireService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Destinataire> getDestinataires(){
        return destinataireService.findAllDestinataires();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Destinataire createDestinataire(@RequestBody Destinataire destinataire) throws BananesExportException {
            return destinataireService.createDestinataire(destinataire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destinataire> updateDestinataire(@RequestBody Destinataire destinataire, @PathVariable int id) throws BananesExportException {
        Destinataire finalDest = destinataireService.updateDestinataire(destinataire, id);
        HttpStatus httpStatus = (finalDest.isAlreadyExists() ? HttpStatus.OK : HttpStatus.CREATED);
        return new ResponseEntity<>(finalDest, httpStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDestinataire(@PathVariable int id) throws BananesExportException {
        destinataireService.deleteDestinataire(id);
    }
}
