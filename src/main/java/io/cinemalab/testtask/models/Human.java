/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.cinemalab.testtask.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author knud
 */
@Entity
@Table(name = "Human")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Human.findAll", query = "SELECT h FROM Human h"),
    @NamedQuery(name = "Human.findById", query = "SELECT h FROM Human h WHERE h.id = :id"),
    @NamedQuery(name = "Human.findByName", query = "SELECT h FROM Human h WHERE h.name = :name"),
    @NamedQuery(name = "Human.findByAge", query = "SELECT h FROM Human h WHERE h.age = :age"),
    @NamedQuery(name = "Human.findByBirthday", query = "SELECT h FROM Human h WHERE h.birthday = :birthday")})
public class Human implements Serializable {

    @Basic(optional = false)
    @Column(name = "Age")
    private int age;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    public Human() {
    }

    public Human(Integer id) {
        this.id = id;
    }

    public Human(String name) {
        this.name = name;
    }

    public Human(Integer id, Integer age, Date birthday) {
        this.id = id;
        this.age = age;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Human)) {
            return false;
        }
        Human other = (Human) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "io.cinemalab.testtask.models.Human[ id=" + id + " ]";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
