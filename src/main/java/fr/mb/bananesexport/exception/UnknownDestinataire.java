package fr.mb.bananesexport.exception;

public class UnknownDestinataire extends BananesExportException{
    public UnknownDestinataire(int id) {
        super("Le destinataire avec l'id " + id + " est inexistant");
    }
}
