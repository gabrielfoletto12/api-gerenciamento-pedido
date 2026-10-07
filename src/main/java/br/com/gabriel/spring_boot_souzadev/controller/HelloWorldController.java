package br.com.gabriel.spring_boot_souzadev.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/hello")
public class HelloWorldController {

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String get1(@PathVariable("id") String id){
        return "Hello Word id "+id;


    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String get2(@RequestParam(value = "name", required = false) String name){
        return "Hello World GET " + name;
    }


    // Post, Put e Patch normalmente usa RequestBody
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String post(@RequestBody String name){
        return "Hello World Post " + name;
    }





 /*   @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping
    public ResponseEntity<String> helloWorldPost(){
         return new ResponseEntity<>("Hello WOrld Post", HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  */

}
