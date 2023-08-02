import models.Person;

import java.util.*;
public class PeopleGenerator {
    private String personGender;
    private String personName;
    private String dateOfBirth;
    private List<String> maleFirstNames = new ArrayList<>(Arrays.asList("Andrey", "Alexey", "Kirill", "Roman", "Dmitriy", "Sergey", "Alexandr", "Fedor"));
    private List<String> femaleFirstNames = new ArrayList<>(Arrays.asList("Anna", "Nina", "Ekaterina", "Elena", "Nadejda", "Olga", "Valentina", "Elizaveta"));
    private List<String> middleNames = new ArrayList<>(Arrays.asList("Nikolaev", "Vladimirov", "Alexandrov", "Ivanov", "Vasiliev", "Sergeev", "Viktorov", "Mihaylov", "Artemov", "Andreev"));
    private List<String> lastNames = new ArrayList<>(Arrays.asList("Ivanov", "Smirnov", "Kuznetsov", "Popov", "Vasiliev", "Petrov", "Sokolov", "Lazarev", "Frolov", "Fedorov"));
    private List<String> gender = new ArrayList<>(Arrays.asList("Male", "Female"));
    public Person generate() {
        Random random = new Random();
        personGender = gender.get(random.nextInt(gender.size()));
        if (personGender.equals("Female"))
            personName = lastNames.get(random.nextInt(lastNames.size())) + "a" + femaleFirstNames.get(random.nextInt(femaleFirstNames.size())) + "" +middleNames.get(random.nextInt(middleNames.size())) + "na";
        else
            personName = lastNames.get(random.nextInt(lastNames.size())) + maleFirstNames.get(random.nextInt(maleFirstNames.size())) + middleNames.get(random.nextInt(middleNames.size())) + "ich";

        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        dateOfBirth = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);

        Person person = new Person();
        person.setName(personName);
        person.setGender(personGender);
        person.setDate_of_birth(dateOfBirth);

        return person;
    }
    public Person generateHundred(){
        Random random = new Random();
        personGender = gender.get(0);
        personName = lastNames.get(random.nextInt(8,10)) + maleFirstNames.get(random.nextInt(maleFirstNames.size())) + middleNames.get(random.nextInt(middleNames.size())) + "ich";

        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        dateOfBirth = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
        Person person = new Person();
        person.setName(personName);
        person.setGender(personGender);
        person.setDate_of_birth(dateOfBirth);
        return person;
    }
    public static int randBetween(int start, int end){
        return start+(int)Math.round(Math.random()*(end-start));
    }
}
