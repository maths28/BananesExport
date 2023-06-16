package fr.mb.bananesexport.exception;

public class QuantiteException extends BananesExportException{

    public QuantiteException(int minQuantite, int maxQuantite, int intervalleQuantite){
        super("La quantité renseignée sur la commande est incorrecte. Elle doit être de "
                + minQuantite + " à " + maxQuantite + " et par tranche de " + intervalleQuantite);
    }

}
