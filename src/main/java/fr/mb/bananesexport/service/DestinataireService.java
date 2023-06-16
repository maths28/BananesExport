package fr.mb.bananesexport.service;

import fr.mb.bananesexport.exception.BananesExportException;
import fr.mb.bananesexport.exception.DestinataireAlreadyExistsException;
import fr.mb.bananesexport.exception.MissingInfoException;
import fr.mb.bananesexport.exception.UnknownDestinataire;
import fr.mb.bananesexport.model.Destinataire;
import fr.mb.bananesexport.repository.DestinataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinataireService {

    @Autowired
    private DestinataireRepository destinataireRepository;

    public List<Destinataire> findAllDestinataires(){
        return destinataireRepository.findAll();
    }

    public Destinataire createDestinataire(Destinataire destinataire) throws BananesExportException {

        this.checkDestinataireValidity(destinataire);

        return destinataireRepository.save(destinataire);
    }

    public Destinataire updateDestinataire(Destinataire destinataire, int id) throws BananesExportException {
        this.checkDestinataireValidity(destinataire);
        Optional<Destinataire> existDestOpt = destinataireRepository.findById(id);
        if(existDestOpt.isPresent()){
            Destinataire existDest = existDestOpt.get();
            existDest.setNom(destinataire.getNom());
            existDest.setAdresse(destinataire.getAdresse());
            existDest.setCodePostal(destinataire.getCodePostal());
            existDest.setVille(destinataire.getVille());
            existDest.setPays(destinataire.getPays());
            destinataireRepository.save(existDest);
            existDest.setAlreadyExists(true);
            return existDest;
        } else {
            destinataireRepository.save(destinataire);
            destinataire.setAlreadyExists(false);
            return destinataire;
        }
    }

    public void deleteDestinataire(int id) throws BananesExportException{
        try {
            this.destinataireRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UnknownDestinataire(id);
        }
    }

    private void checkDestinataireValidity(Destinataire destinataire) throws BananesExportException{

        if(destinataire.anyNull()){
            throw new MissingInfoException(destinataire);
        }

        destinataire.cleanSpaces();

        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll()
                .withIgnoreCase().withIgnorePaths("id");
        Example<Destinataire> example = Example.of(destinataire, caseInsensitiveExampleMatcher);

        Optional<Destinataire> actual = destinataireRepository.findOne(example);

        if(actual.isPresent()) throw new DestinataireAlreadyExistsException();

    }

}
