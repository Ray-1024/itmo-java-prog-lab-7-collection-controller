package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс представляющий координаты в 2-мерном пространстве
 */
public class Coordinates extends SteppedInputObject implements Serializable {

    private float x; //Максимальное значение поля: 948
    private Integer y; //Значение поля должно быть больше -544, Поле не может быть null
    public static final Coordinates emptyCoordinates = new Coordinates();

    public Coordinates() {
        stepsCount = 2;
    }

    public float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }


    @Override
    public void inputLine(String line) throws IllegalStateException {
        try {
            switch (currentStep) {
                case 0:
                    x = Float.parseFloat(line);
                    if (x > 948) break;
                    ++currentStep;
                    return;
                case 1:
                    y = Integer.parseInt(line);
                    if (y < -543) break;
                    ++currentStep;
                    return;

            }
        } catch (NumberFormatException ignored) {
            throw new IllegalStateException(Phrases.getPhrase("CantParseNumber"));
        }
        throw new IllegalStateException(Phrases.getPhrase("WrongArgument"));
    }

    @Override
    public String getStepDescription() {
        switch (currentStep) {
            case 0:
                return Phrases.getPhrase("CoordinatesXDescription");
            case 1:
                return Phrases.getPhrase("CoordinatesYDescription");
            default:
                return Phrases.getPhrase("ReadySteppedInputObject");
        }
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (Float.compare(that.x, x) != 0) return false;
        return Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}