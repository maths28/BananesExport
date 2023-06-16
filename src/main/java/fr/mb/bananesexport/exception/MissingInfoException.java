package fr.mb.bananesexport.exception;

import fr.mb.bananesexport.model.Commande;
import fr.mb.bananesexport.model.Destinataire;

public class MissingInfoException extends BananesExportException{

    public MissingInfoException(Object type) {
        super(fillMessageException(type));
    }

    private static String fillMessageException(Object type){
        String typeText = "";
        if(type instanceof Destinataire){
            typeText = "le destinataire";
        } else if(type instanceof Commande){
            typeText = "la commande";
        }
        return "L'intégralité des champs doit être renseigné pour " + typeText +". Veuillez tous les renseigner";
    }
}
