package repositorio;

import dominio.LutaLivre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LutaLivreRepository extends JpaRepository<LutaLivre, Integer> {
}
