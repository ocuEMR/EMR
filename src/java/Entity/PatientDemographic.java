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
@Table(name = "patient_demographic", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientDemographic.findAll", query = "SELECT p FROM PatientDemographic p"),
    @NamedQuery(name = "PatientDemographic.findById", query = "SELECT p FROM PatientDemographic p WHERE p.id = :id"),
    @NamedQuery(name = "PatientDemographic.findByBloodType", query = "SELECT p FROM PatientDemographic p WHERE p.bloodType = :bloodType"),
    @NamedQuery(name = "PatientDemographic.findByDnaForm", query = "SELECT p FROM PatientDemographic p WHERE p.dnaForm = :dnaForm"),
    @NamedQuery(name = "PatientDemographic.findByFirstName", query = "SELECT p FROM PatientDemographic p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "PatientDemographic.findByLastName", query = "SELECT p FROM PatientDemographic p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "PatientDemographic.findByBirthDate", query = "SELECT p FROM PatientDemographic p WHERE p.birthDate = :birthDate"),
    @NamedQuery(name = "PatientDemographic.findByPlaceOfBirth", query = "SELECT p FROM PatientDemographic p WHERE p.placeOfBirth = :placeOfBirth"),
    @NamedQuery(name = "PatientDemographic.findByFatherName", query = "SELECT p FROM PatientDemographic p WHERE p.fatherName = :fatherName"),
    @NamedQuery(name = "PatientDemographic.findByMotherName", query = "SELECT p FROM PatientDemographic p WHERE p.motherName = :motherName"),
    @NamedQuery(name = "PatientDemographic.findByGender", query = "SELECT p FROM PatientDemographic p WHERE p.gender = :gender"),
    @NamedQuery(name = "PatientDemographic.findByPhoneNumber", query = "SELECT p FROM PatientDemographic p WHERE p.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "PatientDemographic.findByEmail", query = "SELECT p FROM PatientDemographic p WHERE p.email = :email"),
    @NamedQuery(name = "PatientDemographic.findByMaritalStatus", query = "SELECT p FROM PatientDemographic p WHERE p.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "PatientDemographic.findByNationality", query = "SELECT p FROM PatientDemographic p WHERE p.nationality = :nationality"),
    @NamedQuery(name = "PatientDemographic.findByAddress", query = "SELECT p FROM PatientDemographic p WHERE p.address = :address")})
public class PatientDemographic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 10)
    @Column(name = "blood_type", length = 10)
    private String bloodType;
    @Size(max = 20)
    @Column(name = "dna_form", length = 20)
    private String dnaForm;
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;
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
    @Column(name = "nsn", unique=true)
    private Integer nsn;
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
    @Size(max = 50)
    @Column(name = "address", length = 50)
    private String address;
    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String userName;
    @Size(max = 50)
    @Column(name = "password", length = 50)
    private String passWord;      
    @OneToMany(mappedBy = "patient")
    private List<Visit> visitList;

    public PatientDemographic() {
    }

    public PatientDemographic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDnaForm() {
        return dnaForm;
    }

    public void setDnaForm(String dnaForm) {
        this.dnaForm = dnaForm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public Integer getNsn() {
        return nsn;
    }

    public void setNsn(Integer nsn) {
        this.nsn = nsn;
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
        if (!(object instanceof PatientDemographic)) {
            return false;
        }
        PatientDemographic other = (PatientDemographic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.PatientDemographic[ id=" + id + " ]";
    }
    
}
