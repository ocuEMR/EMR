/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.Xray;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * 
 */
@Stateless
public class XrayFacade extends AbstractFacade<Xray> {
    @PersistenceContext(unitName = "EMRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public XrayFacade() {
        super(Xray.class);
    }
    
}
