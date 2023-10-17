package rest;

import Enums.Experience;
import jakarta.annotation.PostConstruct;
import model.Developer;
import model.JuniorDeveloper;
import model.MidDeveloper;
import model.SeniorDeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import tax.DeveloperTax;
import tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



@RestController
@RequestMapping ("/workintech/developers")
public class DeveloperController {

  private   Map<Integer, Developer> developers;
  private Taxable taxable;



    @PostConstruct
    public void  init (){

        developers = new HashMap<>();
    }

@Autowired
    public DeveloperController(@Qualifier ("developerTax") Taxable taxable) {
        this.taxable = taxable;
    }

   @GetMapping("/")
    public List<Developer> getDevelopers( ){

        return developers.values().stream().toList();
   }

   @GetMapping("/{id}")
   public Developer getDeveloper(@PathVariable int id){

       return developers.get(id);
   }
   @PostMapping("/")
    public Developer saveDeveloper (@RequestBody Developer developer){
 if ( developer.getExperience().name().toLowerCase(Locale.ROOT).equals("junior")){

     return developers.put(developer.getId(),
             new JuniorDeveloper(developer.getId(),developer.getName(),
                     developer.getSalary()*(1-taxable.getSimpleTaxRate()), Experience.JUNIOR));

 }
 else  if ( developer.getExperience().name().toLowerCase(Locale.ROOT).equals("mid")){

     return developers.put(developer.getId(),
             new MidDeveloper(developer.getId(),developer.getName(),
                     developer.getSalary()*(1-taxable.getMiddleTaxRate()), Experience.MID));

 }
 else  if ( developer.getExperience().name().toLowerCase(Locale.ROOT).equals("senior")){

     return developers.put(developer.getId(),
             new SeniorDeveloper(developer.getId(),developer.getName(),
                     developer.getSalary()*(1-taxable.getUpperTaxRate()), Experience.SENIOR));

 }

 else {return null ;}

   }



}
