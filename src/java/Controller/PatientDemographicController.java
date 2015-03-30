package Controller;

import Entity.PatientDemographic;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Bean.PatientDemographicFacade;
import Entity.Disease;
import Entity.Visit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ManagedBean(name = "patientDemographicController")
@SessionScoped
public class PatientDemographicController implements Serializable {

    @PersistenceUnit(unitName = "EMRPU")
    private EntityManagerFactory emf = null;
    public static DataModel patientByNsn = null;
    public static DataModel visitByNsn = null;
    public static DataModel permenantByPatient = null;
    public List<String> perminantList = new ArrayList<String>();

    public static DataModel getPermenantByPatient() {
        return permenantByPatient;
    }

    public static void setPermenantByPatient(DataModel permenantByPatient) {
        PatientDemographicController.permenantByPatient = permenantByPatient;
    }

    public static DataModel getVisitByNsn() {
        return visitByNsn;
    }

    public static void setVisitByNsn(DataModel visitByNsn) {
        PatientDemographicController.visitByNsn = visitByNsn;
    }

    public static DataModel getPatientByNsn() {
        return patientByNsn;
    }

    public static void setPatientByNsn(DataModel patientByNsn) {
        PatientDemographicController.patientByNsn = patientByNsn;
    }
    private PatientDemographic current;
    public static PatientDemographic currentPatient;
    private int nsn;

    public int getNsn() {
        return nsn;
    }

    public void setNsn(int nsn) {
        this.nsn = nsn;
    }

    public static PatientDemographic getCurrentPatient() {
        return currentPatient;
    }

    public static void setCurrentPatient(PatientDemographic currentPatient) {
        PatientDemographicController.currentPatient = currentPatient;
    }
    private DataModel items = null;
    @EJB
    private Bean.PatientDemographicFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PatientDemographicController() {
        perminantList.add("Episodic and paroxysmal disorders");
        perminantList.add("Nerve, nerve root and plexus disorders");
        perminantList.add("Systemic connective tissue disorders");
        perminantList.add("Deforming dorsopathies");
    }

    public PatientDemographic getSelected() {
        if (current == null) {
            current = new PatientDemographic();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PatientDemographicFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (PatientDemographic) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PatientDemographic();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareCreateFromIndex() {
        current = new PatientDemographic();
        selectedItemIndex = -1;
        return "JSF/patientDemographic/Create.xhtml";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PatientDemographicCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PatientDemographic) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PatientDemographicUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PatientDemographic) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PatientDemographicDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public String doSearch() {
        List<String> perList = perminantList;
        List patientList = new ArrayList();
        List visitList = new ArrayList();
        List permList = new ArrayList();
        patientList = emf.createEntityManager().createQuery(
                "SELECT p FROM PatientDemographic p WHERE p.nsn = :nsn").setParameter("nsn", nsn).getResultList();
        if (!patientList.isEmpty()) {
            this.current = (PatientDemographic) patientList.get(0);
            patientByNsn = getPagination().createPageDataModel();
            patientByNsn.setWrappedData(patientList);
            Integer patientId = ((PatientDemographic) patientList.get(0)).getId();
            visitList = emf.createEntityManager().createQuery(
                    "SELECT p FROM Visit p WHERE p.patient.id = :id").setParameter("id", patientId).getResultList();

            for (int i = 0; i < visitList.size(); i++) {
                Visit visit = (Visit) visitList.get(i);
                if (visit.getDisease() != null) {
                    String dis = visit.getDisease().getType();
                    if (perList.contains(dis)) {
                        permList.add(dis);
                    }
                }
            }
            visitByNsn = getPagination().createPageDataModel();
            visitByNsn.setWrappedData(visitList);

            permenantByPatient = getPagination().createPageDataModel();
            permenantByPatient.setWrappedData(permList);
        } else {
            JsfUtil.addErrorMessage("Error in NSN number ");
            return "index";
        }
        return "index";
    }

    @FacesConverter(forClass = PatientDemographic.class)
    public static class PatientDemographicControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PatientDemographicController controller = (PatientDemographicController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "patientDemographicController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PatientDemographic) {
                PatientDemographic o = (PatientDemographic) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PatientDemographicController.class.getName());
            }
        }
    }
}
