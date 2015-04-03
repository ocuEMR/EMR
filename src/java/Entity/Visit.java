/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * 
 */
@Entity
@Table(name = "visit", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v"),
    @NamedQuery(name = "Visit.findById", query = "SELECT v FROM Visit v WHERE v.id = :id"),
    @NamedQuery(name = "Visit.findByDate", query = "SELECT v FROM Visit v WHERE v.date = :date"),
    @NamedQuery(name = "Visit.findByDiagnosis", query = "SELECT v FROM Visit v WHERE v.diagnosis = :diagnosis")})
public class Visit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 250)
    @Column(name = "diagnosis", length = 250)
    private String diagnosis;
    @JoinColumn(name = "xray", referencedColumnName = "id")
    @ManyToOne
    private Xray xray;
    @JoinColumn(name = "vital_signs", referencedColumnName = "id")
    @ManyToOne
    private VitalSigns vitalSigns;
    @JoinColumn(name = "test", referencedColumnName = "id")
    @ManyToOne
    private Test test;
    @JoinColumn(name = "patient", referencedColumnName = "id")
    @ManyToOne
    private PatientDemographic patient;
    @JoinColumn(name = "doctor", referencedColumnName = "id")
    @ManyToOne
    private Doctor doctor;
    @JoinColumn(name = "disease", referencedColumnName = "id")
    @ManyToOne
    private Disease disease;
    @JoinColumn(name = "allergy", referencedColumnName = "id")
    @ManyToOne
    private Allergy allergy;
    @JoinColumn(name = "treatment", referencedColumnName = "id")
    @ManyToOne
    private Treatment treatment;

    public Visit() {
    }

    public Visit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Xray getXray() {
        return xray;
    }

    public void setXray(Xray xray) {
        this.xray = xray;
    }

    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(VitalSigns vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public PatientDemographic getPatient() {
        return patient;
    }

    public void setPatient(PatientDemographic patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Allergy getAllergy() {
        return allergy;
    }

    public void setAllergy(Allergy allergy) {
        this.allergy = allergy;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
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
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Visit[ id=" + id + " ]";
    }
    
}
