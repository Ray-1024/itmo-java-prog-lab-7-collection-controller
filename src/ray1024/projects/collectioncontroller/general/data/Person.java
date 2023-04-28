package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.util.Objects;

/**
 * Класс представляющий человека
 */
public class Person extends SteppedInputObject {
    private String name = ""; //Поле не может быть null, Строка не может быть пустой
    private double weight = -1.0; //Значение поля должно быть больше 0
    private Location location = Location.emptyLocation; //Поле может быть null
    public static final Person emptyPerson = new Person();

    public Person() {
        stepsCount = 2 + Location.emptyLocation.getStepsCount();
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        try {
            switch (currentStep) {
                case 0:
                    if (line == null) break;
                    name = line;
                    ++currentStep;
                    return;
                case 1:
                    weight = Double.parseDouble(line);
                    if (weight <= 0.0d) break;
                    ++currentStep;
                    return;
                default:
                    if (Location.emptyLocation == location) {
                        if ("yes".equals(line)) {
                            location = new Location();
                            return;
                        } else if ("no".equals(line)) {
                            location = null;
                            stepsCount = 2;
                            return;
                        } else throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
                    }
                    if (location == null || location.isObjectReady()) return;
                    location.inputLine(line);
                    ++currentStep;
                    return;
            }
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalStateException(Phrases.getPhrase("CantParseNumber"));
        }
        throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
    }

    @Override
    public String getStepDescription() {
        switch (currentStep) {
            case 0:
                return Phrases.getPhrase("PersonNameDescription");
            case 1:
                return Phrases.getPhrase("PersonWeightDescription");
            default:
                if (Location.emptyLocation == location) return Phrases.getPhrase("PersonLocationIsNullDescription");
                if (!isObjectReady()) return location.getStepDescription();
                return Phrases.getPhrase("ReadySteppedInputObject");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (Double.compare(person.weight, weight) != 0) return false;
        if (!Objects.equals(name, person.name)) return false;
        return Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", weight=" + weight + ", location=" + location + '}';
    }
}
