package fr.mb.bananesexport.exception;

public class UnknownCommandeException extends BananesExportException{

    public UnknownCommandeException(int commandeId, int destinataireId){
        super("La commande " + commandeId + " du destinataire " + destinataireId + " est inexistante.");
    }

}
