package com.codeinside.attendancesystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "number_of_students")
    private Integer numberOfStudents;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<Student> students;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<Lesson> aClasses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Lesson> getClasses() {
        return aClasses;
    }

    public void setClasses(List<Lesson> aClasses) {
        this.aClasses = aClasses;
    }
}
