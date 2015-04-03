/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * 
 */
@Entity
@Table(name = "treatment", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treatment.findAll", query = "SELECT t FROM Treatment t"),
    @NamedQuery(name = "Treatment.findById", query = "SELECT t FROM Treatment t WHERE t.id = :id"),
    @NamedQuery(name = "Treatment.findByType", query = "SELECT t FROM Treatment t WHERE t.type = :type")})
public class Treatment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "type")
    private Integer type;
    @JoinColumn(name = "medical", referencedColumnName = "id")
    @ManyToOne
    private MedicalTreatment medical;
    @JoinColumn(name = "surgery", referencedColumnName = "id")
    @ManyToOne
    private Surgery surgery;
    @JoinColumn(name = "physical", referencedColumnName = "id")
    @ManyToOne
    private PhysicalTherapy physical;
    @OneToMany(mappedBy = "treatment")
    private List<Visit> visitList;

    public Treatment() {
    }

    public Treatment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public MedicalTreatment getMedical() {
        return medical;
    }

    public void setMedical(MedicalTreatment medical) {
        this.medical = medical;
    }

    public Surgery getSurgery() {
        return surgery;
    }

    public void setSurgery(Surgery surgery) {
        this.surgery = surgery;
    }

    public PhysicalTherapy getPhysical() {
        return physical;
    }

    public void setPhysical(PhysicalTherapy physical) {
        this.physical = physical;
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
        if (!(object instanceof Treatment)) {
            return false;
        }
        Treatment other = (Treatment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Treatment[ id=" + id + " ]";
    }
    
}
