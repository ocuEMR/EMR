/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * 
 */
@Entity
@Table(name = "nurse", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nurse.findAll", query = "SELECT n FROM Nurse n"),
    @NamedQuery(name = "Nurse.findById", query = "SELECT n FROM Nurse n WHERE n.id = :id"),
    @NamedQuery(name = "Nurse.findByFirstName", query = "SELECT n FROM Nurse n WHERE n.firstName = :firstName"),
    @NamedQuery(name = "Nurse.findByLastName", query = "SELECT n FROM Nurse n WHERE n.lastName = :lastName"),
    @NamedQuery(name = "Nurse.findByUsername", query = "SELECT n FROM Nurse n WHERE n.username = :username"),
    @NamedQuery(name = "Nurse.findByPassword", query = "SELECT n FROM Nurse n WHERE n.password = :password"),
    @NamedQuery(name = "Nurse.findByPhone", query = "SELECT n FROM Nurse n WHERE n.phone = :phone"),
    @NamedQuery(name = "Nurse.findByGender", query = "SELECT n FROM Nurse n WHERE n.gender = :gender")})
public class Nurse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;
    @Size(max = 50)
    @Column(name = "password", length = 50)
    private String password;
    @Column(name = "phone")
    private Integer phone;
    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    public Nurse() {
    }

    public Nurse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        if (!(object instanceof Nurse)) {
            return false;
        }
        Nurse other = (Nurse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Nurse[ id=" + id + " ]";
    }
    
}
