package fr.mb.bananesexport.exception;

public class UnknownDateLivraisonException extends BananesExportException{

    public UnknownDateLivraisonException(){
        super("La date de livraison renseignée de la commande n'est pas reconnue. Le format doit être jj-mm-aaaa.");
    }

}
