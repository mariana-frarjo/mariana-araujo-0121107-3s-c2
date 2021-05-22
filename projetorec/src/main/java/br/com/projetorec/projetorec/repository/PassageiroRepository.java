package br.com.projetorec.projetorec.repository;

import br.com.projetorec.projetorec.dominio.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageiroRepository extends JpaRepository<Passageiro, Integer> {

    int countByCpf(String cpf);

}
