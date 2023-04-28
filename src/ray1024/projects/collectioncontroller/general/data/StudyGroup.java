package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс представляющий учебную группу
 * Элементы коллекции являются представителями данного класса
 * Сортировка по-умолчанию : по лексикографическому неубыванию названий групп
 */
public class StudyGroup extends SteppedInputObject implements Comparable<StudyGroup> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    private IUser ownen;



    public StudyGroup() {
        creationDate = LocalDateTime.now();
        name = "EmptyName";
        coordinates = Coordinates.emptyCoordinates;
        currentStep = 0;
        coordinates.reset();
        studentsCount = 0;
        formOfEducation = null;
        groupAdmin = Person.emptyPerson;
        groupAdmin.reset();
        stepsCount = 6 + Coordinates.emptyCoordinates.getStepsCount() + Person.emptyPerson.getStepsCount();
    }

    public IUser getOwnen() {
        return ownen;
    }

    public StudyGroup setOwnen(IUser ownen) {
        this.ownen = ownen;
        return this;
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        try {
            if (currentStep == 0) {
                name = line;
                if (name == null || "".equals(name))
                    throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
                ++currentStep;
            } else if (currentStep >= 1 && currentStep <= Coordinates.emptyCoordinates.getStepsCount()) {
                coordinates.inputLine(line);
                ++currentStep;
            } else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 1) {
                studentsCount = Integer.parseInt(line);
                if (studentsCount < 1) throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
                ++currentStep;
            } else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 2) {
                if ("yes".equals(line)) ++currentStep;
                else if ("no".equals(line)) {
                    formOfEducation = null;
                    currentStep += 2;
                } else throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
            } else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 3) {
                formOfEducation = FormOfEducation.valueOf(line);
                ++currentStep;
            } else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 4) {
                semesterEnum = Semester.valueOf(line);
                ++currentStep;
            } else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 5) {
                if ("yes".equals(line)) {
                    groupAdmin = new Person();
                    ++currentStep;
                } else if ("no".equals(line)) {
                    groupAdmin = null;
                    currentStep += 1 + Person.emptyPerson.getStepsCount();
                } else throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
            } else if (currentStep >= Coordinates.emptyCoordinates.getStepsCount() + 6 && currentStep < Coordinates.emptyCoordinates.getStepsCount() + 6 + Person.emptyPerson.getStepsCount()) {
                groupAdmin.inputLine(line);
                ++currentStep;
            }
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalStateException(Phrases.getPhrase("CantParseNumber"));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
        }
    }

    @Override
    public String getStepDescription() {
        if (currentStep == 0) return Phrases.getPhrase("StudyGroupNameDescription");
        else if (currentStep >= 1 && currentStep <= Coordinates.emptyCoordinates.getStepsCount())
            return Phrases.getPhrase("StudyGroupCoordinatesDescription") + "\n" + coordinates.getStepDescription();
        else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 1)
            return Phrases.getPhrase("StudyGroupStudentsCountDescription");
        else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 2)
            return Phrases.getPhrase("StudyGroupFormOfEducationIsNullDescription");
        else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 3)
            return Phrases.getPhrase("StudyGroupFormOfEducationDescription");
        else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 4)
            return Phrases.getPhrase("StudyGroupSemesterDescription");
        else if (currentStep == Coordinates.emptyCoordinates.getStepsCount() + 5)
            return Phrases.getPhrase("StudyGroupGroupAdminIsNullDescription");
        else if (currentStep >= Coordinates.emptyCoordinates.getStepsCount() + 6 && currentStep < Coordinates.emptyCoordinates.getStepsCount() + 6 + Person.emptyPerson.getStepsCount())
            return Phrases.getPhrase("StudyGroupGroupAdminDescription") + "\n" + groupAdmin.getStepDescription();
        else return Phrases.getPhrase("ReadySteppedInputObject");
    }

    @Override
    public int compareTo(StudyGroup o) {
        if (this.equals(o)) return 0;
        return this.studentsCount - o.studentsCount;
    }

    public StudyGroup setId(long id) {
        this.id = id;
        return this;
    }

    public StudyGroup setName(String name) {
        this.name = name;
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public StudyGroup setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public StudyGroup setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public StudyGroup setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
        return this;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public StudyGroup setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
        return this;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public StudyGroup setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
        return this;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public StudyGroup setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StudyGroup{" + "id=" + id + ", name='" + name + '\'' + ", coordinates=" + coordinates + ", creationDate=" + creationDate + ", studentsCount=" + studentsCount + ", formOfEducation=" + formOfEducation + ", semesterEnum=" + semesterEnum + ", groupAdmin=" + groupAdmin + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) return false;
        StudyGroup gr = (StudyGroup) obj;
        return name.equals(gr.name) && coordinates.equals(gr.coordinates) && studentsCount == gr.studentsCount && ((formOfEducation == null && gr.formOfEducation == null) || formOfEducation.equals(gr.formOfEducation)) && semesterEnum.equals(gr.semesterEnum) && ((groupAdmin == null && gr.groupAdmin == null) || groupAdmin.equals(gr.groupAdmin));
    }
}