package fr.mb.bananesexport.repository;

import fr.mb.bananesexport.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    List<Commande> findCommandesByDestinataireId(int destId);

    boolean existsByIdAndDestinataireId(int commandeId, int destId);

    @Transactional
    void deleteByIdAndDestinataireId(int commandeId, int destId);
}
