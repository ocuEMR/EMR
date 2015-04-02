/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.Surgery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * 
 */
@Stateless
public class SurgeryFacade extends AbstractFacade<Surgery> {
    @PersistenceContext(unitName = "EMRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SurgeryFacade() {
        super(Surgery.class);
    }
    
}
