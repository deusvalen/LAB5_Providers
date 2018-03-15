package by.bstu.fit.lyolia.lab5_providers;

/**
 * Created by User on 14.03.2018.
 */

public class Student {

    private String name;
    private String grade;
    private String adress;

    public Student(String name, String grade, String adress) {
        this.name = name;
        this.grade = grade;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
