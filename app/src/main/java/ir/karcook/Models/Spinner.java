package ir.karcook.Models;

public class Spinner {

    private int age_year;
    private int age_mounth;
    private int age_day;

    public Spinner(int age_year, int age_mounth, int age_day) {
        this.age_day = age_day;
        this.age_mounth = age_mounth;
        this.age_year = age_year;
    }

    public int getAge_year() {
        return age_year;
    }

    public void setAge_year(int age_year) {
        this.age_year = age_year;
    }

    public int getAge_mounth() {
        return age_mounth;
    }

    public void setAge_mounth(int age_mounth) {
        this.age_mounth = age_mounth;
    }

    public int getAge_day() {
        return age_day;
    }

    public void setAge_day(int age_day) {
        this.age_day = age_day;
    }

}
