package br.com.projetorec.projetorec.controller;

import br.com.projetorec.projetorec.dominio.Passageiro;
import br.com.projetorec.projetorec.dominio.TipoPassagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.projetorec.projetorec.repository.PassageiroRepository;
import br.com.projetorec.projetorec.repository.TipoPassagemRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bilhete-unico")
public class BilheteUnicoController {

    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private TipoPassagemRepository tipoPassagensRepository;

    @PostMapping
    public ResponseEntity postBu(@RequestBody @Valid Passageiro novoBilhete) {

        List<Passageiro> passageiros = passageiroRepository.findAll();
        if (passageiroRepository.countByCpf(novoBilhete.getCpf()) == 2) {
            return ResponseEntity.status(400).body("Este CPF já tem 2 BUs!");
        } else {
            passageiroRepository.save(novoBilhete);
            return ResponseEntity.status(201).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable int id){
        if (passageiroRepository.existsById(id)) {
            return ResponseEntity.of(passageiroRepository.findById(id));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/tipo-passagem")
    public ResponseEntity postTipo(@RequestBody @Valid TipoPassagem novoTipo){

        List<TipoPassagem> passagens = tipoPassagensRepository.findAll();

        if(tipoPassagensRepository.countByDescricao(novoTipo.getDescricao()) == 1){
            return ResponseEntity.status(400).body("Este tipo de passagem já está cadastrado!");
        } else {
            tipoPassagensRepository.save(novoTipo);
            return ResponseEntity.status(201).build();
        }
    }

    @PostMapping("/{id}/recarga/{valorRecarga}")
    public ResponseEntity postRecarga(@PathVariable int id, @PathVariable Double valorRecarga){

        if (passageiroRepository.existsById(id)){
            Passageiro bu = passageiroRepository.findById(id).get();
            if (valorRecarga < 2.00){
                return ResponseEntity.status(401).body("Valor da recarga deve ser a partir de R$ 2,00!");
            } else if (bu.getSaldo() + valorRecarga > 250.0) {
                return ResponseEntity.status(400).body("Recarga não efetuada! Passaria do limite de R$ 250,00!");
            } else {
                bu.setSaldo(bu.getSaldo() + valorRecarga);
                passageiroRepository.save(bu);
                return ResponseEntity.status(201).build();
            }
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/{id}/passagem/{idTipo}")
    public ResponseEntity postReduz(@PathVariable int id, @PathVariable int idTipo) {

        if (passageiroRepository.existsById(id)) {
            Passageiro bu = passageiroRepository.findById(id).get();
            TipoPassagem passagem = tipoPassagensRepository.findById(id).get();
            if (!tipoPassagensRepository.existsById(idTipo)) {
                return ResponseEntity.status(404).body("Tipo de passagem não encontrada!");
            } else if (passagem.getValor() > bu.getSaldo()) {
                return ResponseEntity.status(400).body(String.format("Saldo atual (R$ %.2f) insuficiente para esta passagem", bu.getSaldo()));
            } else {
                bu.setSaldo(bu.getSaldo() - passagem.getValor());
                passageiroRepository.save(bu);
                return ResponseEntity.status(201).build();
            }
            } else {
            return ResponseEntity.status(404).body("BU não encontrado!");
        }
    }

}
