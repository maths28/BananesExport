package fr.mb.bananesexport.exception;

public class DestinataireAlreadyExistsException extends BananesExportException{

    public DestinataireAlreadyExistsException(){
        super("Le destinataire saisi est déjà existant.");
    }

}
