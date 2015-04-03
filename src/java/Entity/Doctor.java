/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * 
 */
@Entity
@Table(name = "doctor", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d"),
    @NamedQuery(name = "Doctor.findById", query = "SELECT d FROM Doctor d WHERE d.id = :id"),
    @NamedQuery(name = "Doctor.findByFirstName", query = "SELECT d FROM Doctor d WHERE d.firstName = :firstName"),
    @NamedQuery(name = "Doctor.findByLastName", query = "SELECT d FROM Doctor d WHERE d.lastName = :lastName"),
    @NamedQuery(name = "Doctor.findByBirthDate", query = "SELECT d FROM Doctor d WHERE d.birthDate = :birthDate"),
    @NamedQuery(name = "Doctor.findByPlaceOfBirth", query = "SELECT d FROM Doctor d WHERE d.placeOfBirth = :placeOfBirth"),
    @NamedQuery(name = "Doctor.findByFatherName", query = "SELECT d FROM Doctor d WHERE d.fatherName = :fatherName"),
    @NamedQuery(name = "Doctor.findByMotherName", query = "SELECT d FROM Doctor d WHERE d.motherName = :motherName"),
    @NamedQuery(name = "Doctor.findByGender", query = "SELECT d FROM Doctor d WHERE d.gender = :gender"),
    @NamedQuery(name = "Doctor.findByPhoneNumber", query = "SELECT d FROM Doctor d WHERE d.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Doctor.findByEmail", query = "SELECT d FROM Doctor d WHERE d.email = :email"),
    @NamedQuery(name = "Doctor.findByMaritalStatus", query = "SELECT d FROM Doctor d WHERE d.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "Doctor.findByNationality", query = "SELECT d FROM Doctor d WHERE d.nationality = :nationality"),
    @NamedQuery(name = "Doctor.findByAddress", query = "SELECT d FROM Doctor d WHERE d.address = :address")})
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int ADMIN = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "speciality", referencedColumnName = "id")
    @ManyToOne
    private Specialist specialist;
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String userName;
    @Size(max = 50)
    @Column(name = "password", length = 50)
    private String passWord;
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Size(max = 75)
    @Column(name = "place_of_birth", length = 75)
    private String placeOfBirth;
    @Size(max = 50)
    @Column(name = "father_name", length = 50)
    private String fatherName;
    @Size(max = 50)
    @Column(name = "mother_name", length = 50)
    private String motherName;
    @Size(max = 20)
    @Column(name = "gender", length = 20)
    private String gender;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "email", length = 20)
    private String email;
    @Size(max = 20)
    @Column(name = "marital_status", length = 20)
    private String maritalStatus;
    @Size(max = 20)
    @Column(name = "nationality", length = 20)
    private String nationality;
    @Column(name = "type")
    private Integer type;
    @Size(max = 50)
    @Column(name = "address", length = 50)
    private String address;
    @OneToMany(mappedBy = "doctor")
    private List<Visit> visitList;

    public Doctor() {
    }

    public Doctor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
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
        if (!(object instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Doctor[ id=" + id + " ]";
    }
}
