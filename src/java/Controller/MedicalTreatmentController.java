package Controller;

import Entity.MedicalTreatment;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Bean.MedicalTreatmentFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

@ManagedBean(name = "medicalTreatmentController")
@SessionScoped
public class MedicalTreatmentController implements Serializable {

    Calendar date = Calendar.getInstance();
    public static Date currentdate;
    private MedicalTreatment current;
    private DataModel items = null;
    @EJB
    private Bean.MedicalTreatmentFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public static MedicalTreatment currentMedicalTreatment;

    public static MedicalTreatment getCurrentMedicalTreatment() {
        return currentMedicalTreatment;
    }

    public static void setCurrentMedicalTreatment(MedicalTreatment currentMedicalTreatment) {
        MedicalTreatmentController.currentMedicalTreatment = currentMedicalTreatment;
    }

    public MedicalTreatmentController() {
    }

    public MedicalTreatment getSelected() {
        if (current == null) {
            current = new MedicalTreatment();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MedicalTreatmentFacade getFacade() {
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
        current = (MedicalTreatment) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new MedicalTreatment();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareCreateForVisit() {
        current = new MedicalTreatment();
        selectedItemIndex = -1;
        currentMedicalTreatment = null;
        return "/JSF/medicalTreatment/Create";
    }

    public String create() {
        try {
            Calendar date = Calendar.getInstance();
            //   current.setDate(date.getTime());
            getFacade().create(current);
            currentMedicalTreatment = current;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicalTreatmentCreated"));
            return "/JSF/treatment/Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (MedicalTreatment) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicalTreatmentUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (MedicalTreatment) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicalTreatmentDeleted"));
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

    @FacesConverter(forClass = MedicalTreatment.class)
    public static class MedicalTreatmentControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MedicalTreatmentController controller = (MedicalTreatmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "medicalTreatmentController");
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
            if (object instanceof MedicalTreatment) {
                MedicalTreatment o = (MedicalTreatment) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MedicalTreatmentController.class.getName());
            }
        }
    }
}
