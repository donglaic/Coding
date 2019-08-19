package tacos.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class RestDesignTacoController {
    private TacoRepository tacoRepository;

    public RestDesignTacoController(TacoRepository tacoRepository){
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos(){
        PageRequest pageRequest = PageRequest.of(0,12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> findById(@PathVariable("id") Long id){
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if(optionalTaco.isPresent()){
            return new ResponseEntity<>(optionalTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @PutMapping("/{id}")
    public Taco putTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

//    @PatchMapping(path = "/{tacoId",consumes = "application/json")
//    public Taco patchTaco(@PathVariable("tacoId") Long tacoId,
//        @RequestBody Taco patch){
//        Taco taco = tacoRepository.findById(tacoId).get();
//        if(patch.getName() != Null){
//            taco.setName(patch.getName());
//        }
//        return tacoRepository.save(taco);
//    }
}
