package Controller;

import Entity.Doctor;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Bean.DoctorFacade;
import Entity.Nurse;
import Entity.PatientDemographic;

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

@ManagedBean(name = "doctorController")
@SessionScoped
public class DoctorController implements Serializable {

    private Doctor current;
    private DataModel items = null;
    public static Doctor currentDoctor;
    @PersistenceUnit(unitName = "EMRPU")
    private EntityManagerFactory emf = null;
    @EJB
    private Bean.DoctorFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String userName;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public static Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public static void setCurrentDoctor(Doctor currentDoctor) {
        DoctorController.currentDoctor = currentDoctor;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    private String passWord;

    public DoctorController() {
    }

    public Doctor getSelected() {
        if (current == null) {
            current = new Doctor();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DoctorFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(4) {

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
        current = (Doctor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Doctor();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DoctorCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Doctor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DoctorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Doctor) getItems().getRowData();
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

    private void init() {
        PatientDemographicController.patientByNsn = null;
        PatientDemographicController.visitByNsn = null;
        XrayController.currentXray = null;
        AllergyController.currentAllergy = null;
        TestController.currentTest = null;
        VitalSignsController.currentVital = null;
        DiseaseController.currentDisease = null;
        TreatmentController.currentTreatment = null;
        MedicalTreatmentController.currentMedicalTreatment = null;
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DoctorDeleted"));
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

    public String getLogin() {
        init();
        
        List loginList = new ArrayList();
        PatientDemographicController.patientByNsn = null;
        
        //"1">Doctor
        loginList = emf.createEntityManager().createQuery("SELECT e FROM Doctor e WHERE e.userName = :username "
                + " and e.passWord = :password ").setParameter("username", userName).setParameter("password", passWord).getResultList();
        if (!loginList.isEmpty()) {
            Doctor doc = (Doctor) loginList.get(0);
            DoctorController.currentDoctor = doc;
            NurseController.currentNurse = null;
            PatientDemographicController.currentPatient = null;
            return "index";
        } else {  //"2">Patient
            loginList = emf.createEntityManager().createQuery("SELECT e FROM PatientDemographic e WHERE e.userName = :username "
                    + " and e.passWord = :password ").setParameter("username", userName).setParameter("password", passWord).getResultList();
            if (!loginList.isEmpty()) {
                PatientDemographic patient = (PatientDemographic) loginList.get(0);
                DoctorController.currentDoctor = null;
                PatientDemographicController.currentPatient = patient;
                NurseController.currentNurse = null;
                return "index";
            } 
            // Nurse
            loginList = emf.createEntityManager().createQuery("SELECT n FROM Nurse n WHERE n.username = :username "
                    + " and n.password = :password ").setParameter("username", userName).setParameter("password", passWord).getResultList();
            if (!loginList.isEmpty()) {
                Nurse nurse = (Nurse) loginList.get(0);
                DoctorController.currentDoctor = null;
                NurseController.currentNurse = nurse;
                PatientDemographicController.currentPatient = null;
                return "index";
            }
            JsfUtil.addErrorMessage("Error in username or password ");
            return "main";
        }
    }

    @FacesConverter(forClass = Doctor.class)
    public static class DoctorControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DoctorController controller = (DoctorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "doctorController");
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
            if (object instanceof Doctor) {
                Doctor o = (Doctor) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DoctorController.class.getName());
            }
        }
    }
}
