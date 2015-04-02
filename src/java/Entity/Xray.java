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
@Table(name = "xray", catalog = "emr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Xray.findAll", query = "SELECT x FROM Xray x"),
    @NamedQuery(name = "Xray.findById", query = "SELECT x FROM Xray x WHERE x.id = :id"),
    @NamedQuery(name = "Xray.findByName", query = "SELECT x FROM Xray x WHERE x.name = :name"),
    @NamedQuery(name = "Xray.findByType", query = "SELECT x FROM Xray x WHERE x.type = :type"),
    @NamedQuery(name = "Xray.findByResult", query = "SELECT x FROM Xray x WHERE x.result = :result")})
public class Xray implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 30)
    @Column(name = "name", length = 30)
    private String name;
    @Size(max = 30)
    @Column(name = "type", length = 30)
    private String type;
    @Size(max = 50)
    @Column(name = "result", length = 50)
    private String result;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @OneToMany(mappedBy = "xray")
    private List<Visit> visitList;

    public Xray() {
    }

    public Xray(Integer id) {
        this.id = id;
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
  
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
        if (!(object instanceof Xray)) {
            return false;
        }
        Xray other = (Xray) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Xray[ id=" + id + " ]";
    }
}
