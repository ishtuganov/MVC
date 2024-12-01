package ishtuganov.springcourse.util;
import ishtuganov.springcourse.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ishtuganov.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    PersonValidator(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findByName(person.getName()).isPresent())
            errors.rejectValue("name", "", "this name is already taken");
    }
}
