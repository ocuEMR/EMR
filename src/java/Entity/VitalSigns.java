/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
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
@Table(name = "vital_signs", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VitalSigns.findAll", query = "SELECT v FROM VitalSigns v"),
    @NamedQuery(name = "VitalSigns.findById", query = "SELECT v FROM VitalSigns v WHERE v.id = :id"),
    @NamedQuery(name = "VitalSigns.findByPressure", query = "SELECT v FROM VitalSigns v WHERE v.pressure = :pressure"),
    @NamedQuery(name = "VitalSigns.findByTemperature", query = "SELECT v FROM VitalSigns v WHERE v.temperature = :temperature"),
    @NamedQuery(name = "VitalSigns.findByPulsation", query = "SELECT v FROM VitalSigns v WHERE v.pulsation = :pulsation"),
    @NamedQuery(name = "VitalSigns.findByConscious", query = "SELECT v FROM VitalSigns v WHERE v.conscious = :conscious")})
public class VitalSigns implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 15)
    @Column(name = "pressure", length = 15)
    private String pressure;
    @Column(name = "temperature")
    private Integer temperature;
    @Size(max = 15)
    @Column(name = "pulsation", length = 15)
    private String pulsation;
    @Size(max = 20)
    @Column(name = "conscious", length = 20)
    private String conscious;
    @OneToMany(mappedBy = "vitalSigns")
    private List<Visit> visitList;

    public VitalSigns() {
    }

    public VitalSigns(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getPulsation() {
        return pulsation;
    }

    public void setPulsation(String pulsation) {
        this.pulsation = pulsation;
    }

    public String getConscious() {
        return conscious;
    }

    public void setConscious(String conscious) {
        this.conscious = conscious;
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
        if (!(object instanceof VitalSigns)) {
            return false;
        }
        VitalSigns other = (VitalSigns) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.VitalSigns[ id=" + id + " ]";
    }
    
}
