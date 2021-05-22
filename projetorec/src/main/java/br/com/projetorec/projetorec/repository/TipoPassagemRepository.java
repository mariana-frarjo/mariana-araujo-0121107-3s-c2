package br.com.projetorec.projetorec.repository;

import br.com.projetorec.projetorec.dominio.TipoPassagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPassagemRepository extends JpaRepository<TipoPassagem, Integer> {

    int countByDescricao(String descricao);
}
