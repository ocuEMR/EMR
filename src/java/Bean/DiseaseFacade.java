/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.Disease;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * 
 */
@Stateless
public class DiseaseFacade extends AbstractFacade<Disease> {
    @PersistenceContext(unitName = "EMRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiseaseFacade() {
        super(Disease.class);
    }
    
}
