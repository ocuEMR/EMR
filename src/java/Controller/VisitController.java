package Controller;

import Entity.Visit;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Bean.VisitFacade;
import Entity.PatientDemographic;

import java.io.Serializable;
import java.util.Calendar;
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

@ManagedBean(name = "visitController")
@SessionScoped
public class VisitController implements Serializable {

    private Visit current;
    private DataModel items = null;
    @EJB
    private Bean.VisitFacade ejbFacade;
    @EJB
    private Bean.PatientDemographicFacade patientEjbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public String diagnosisText;

    public String getDiagnosisText() {
        return diagnosisText;
    }

    public void setDiagnosisText(String diagnosisText) {
        this.diagnosisText = diagnosisText;
    }

    public VisitController() {
    }

    public Visit getSelected() {
        if (current == null) {
            current = new Visit();
            selectedItemIndex = -1;
        }
        return current;
    }

    private VisitFacade getFacade() {
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
        current = (Visit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareIndex() {
        PatientDemographicController.patientByNsn = null;
        PatientDemographicController.visitByNsn = null;
        PatientDemographicController.currentPatient = null;
        XrayController.currentXray = null;
        AllergyController.currentAllergy = null;
        TestController.currentTest = null;
        VitalSignsController.currentVital = null;
        DiseaseController.currentDisease = null;
        TreatmentController.currentTreatment = null;
        MedicalTreatmentController.currentMedicalTreatment = null;
        return "/index";
    }
    
    public String prepareViewFromIndex(Integer id) {
        current = getFacade().find(id);
        PatientDemographicController.currentPatient = current.getPatient();
        XrayController.currentXray = current.getXray();
        AllergyController.currentAllergy = current.getAllergy();
        TestController.currentTest = current.getTest();
        VitalSignsController.currentVital = current.getVitalSigns();
        DiseaseController.currentDisease = current.getDisease();
        TreatmentController.currentTreatment = current.getTreatment();
        if (current.getTreatment() != null) {
            MedicalTreatmentController.currentMedicalTreatment = current.getTreatment().getMedical();
        }
        //  PatientDemographicController.setCurrentPatient(patientEjbFacade.find(id));

        return "JSF/patientDemographic/Visit";
    }

    public String prepareCreate() {
        current = new Visit();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareVisit(Integer id) {
        current = new Visit();
        selectedItemIndex = -1;
        XrayController.currentXray = null;
        AllergyController.currentAllergy = null;
        TestController.currentTest = null;
        VitalSignsController.currentVital = null;
        DiseaseController.currentDisease = null;
        TreatmentController.currentTreatment = null;
        MedicalTreatmentController.currentMedicalTreatment = null;
        PatientDemographicController.setCurrentPatient(patientEjbFacade.find(id));
        return "JSF/patientDemographic/Visit";
    }

    public String create() {
        try {
            Calendar date = Calendar.getInstance();
            current.setDoctor(DoctorController.currentDoctor);
            current.setXray(XrayController.currentXray);
            current.setDate(date.getTime());
            current.setPatient(PatientDemographicController.currentPatient);
            current.setAllergy(AllergyController.currentAllergy);
            current.setTest(TestController.currentTest);
            current.setVitalSigns(VitalSignsController.currentVital);
            current.setDisease(DiseaseController.currentDisease);
            current.setTreatment(TreatmentController.currentTreatment);
            //   current.setDiagnosis(DiseaseController.selectedNode.toString());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitCreated"));
            return ("Visit");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Visit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Visit) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VisitDeleted"));
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

    @FacesConverter(forClass = Visit.class)
    public static class VisitControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VisitController controller = (VisitController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "visitController");
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
            if (object instanceof Visit) {
                Visit o = (Visit) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + VisitController.class.getName());
            }
        }
    }
}
