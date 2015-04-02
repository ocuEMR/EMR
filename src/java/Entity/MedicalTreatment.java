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
@Table(name = "medical_treatment", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicalTreatment.findAll", query = "SELECT m FROM MedicalTreatment m"),
    @NamedQuery(name = "MedicalTreatment.findById", query = "SELECT m FROM MedicalTreatment m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalTreatment.findByMedication", query = "SELECT m FROM MedicalTreatment m WHERE m.medication = :medication"),
    @NamedQuery(name = "MedicalTreatment.findByDose", query = "SELECT m FROM MedicalTreatment m WHERE m.dose = :dose"),
    @NamedQuery(name = "MedicalTreatment.findByFrequency", query = "SELECT m FROM MedicalTreatment m WHERE m.frequency = :frequency"),
    @NamedQuery(name = "MedicalTreatment.findByStartDate", query = "SELECT m FROM MedicalTreatment m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MedicalTreatment.findByEndDate", query = "SELECT m FROM MedicalTreatment m WHERE m.endDate = :endDate")})
public class MedicalTreatment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "medication", referencedColumnName = "id")
    @ManyToOne
    private Medicine medication;
    @Size(max = 20)
    @Column(name = "dose", length = 20)
    private String dose;
    @Size(max = 20)
    @Column(name = "frequency", length = 20)
    private String frequency;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @OneToMany(mappedBy = "medical")
    private List<Treatment> treatmentList;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public MedicalTreatment() {
    }

    public MedicalTreatment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medicine getMedication() {
        return medication;
    }

    public void setMedication(Medicine medication) {
        this.medication = medication;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @XmlTransient
    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
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
        if (!(object instanceof MedicalTreatment)) {
            return false;
        }
        MedicalTreatment other = (MedicalTreatment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.MedicalTreatment[ id=" + id + " ]";
    }
}
