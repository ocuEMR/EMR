/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.Nurse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * 
 */
@Stateless
public class NurseFacade extends AbstractFacade<Nurse> {
    @PersistenceContext(unitName = "EMRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NurseFacade() {
        super(Nurse.class);
    }
    
}
