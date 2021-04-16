package controle;

import dominio.LutaLivre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorio.LutaLivreRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lutadores")
public class LutaLivreController {

    @Autowired
    private LutaLivreRepository repository;

    @PostMapping
    public ResponseEntity postLutaLivre(@RequestBody @Valid LutaLivre novaLuta) {
        if (novaLuta.getForcaGolpe()<0){
            return ResponseEntity.status(400).body("Força do golpe deve ser um valor acima de 0.");
        }
        else{
            repository.save(novaLuta);
            return ResponseEntity.status(201).build();
        }
    }

   @GetMapping("/lutadores")
   public ResponseEntity getLutadores(){
       List<LutaLivre> lutas = repository.findAll();
       if (lutas.isEmpty()){
           return ResponseEntity.status(204).build();
       } else {
           return ResponseEntity.status(200).body(lutas);
       }
   }

    @GetMapping ("/{contagem-vivos}")
    public ResponseEntity getLutadores(){
    
    }
}