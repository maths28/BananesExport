package fr.mb.bananesexport.exception;

public class WrongDateLivraisonException extends BananesExportException{

    public WrongDateLivraisonException(int nbDaysDelay){
        super("La date de livraison doit être fixée à " + nbDaysDelay + " jour" + (nbDaysDelay > 1 ? "s" : "") + " d'intervalle");
    }

}
