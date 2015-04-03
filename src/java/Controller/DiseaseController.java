package Controller;

import Entity.Disease;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Bean.DiseaseFacade;

import java.io.Serializable;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "diseaseController")
@SessionScoped
public class DiseaseController implements Serializable {

    private Disease current;
    private DataModel items = null;
    @EJB
    private Bean.DiseaseFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public static Disease currentDisease;

    public static Disease getCurrentDisease() {
        return currentDisease;
    }

    public static void setCurrentDisease(Disease currentDisease) {
        DiseaseController.currentDisease = currentDisease;
    }

    public DiseaseController() {
    }

    public Disease getSelected() {
        if (current == null) {
            current = new Disease();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DiseaseFacade getFacade() {
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
        current = (Disease) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Disease();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareCreateForVisit() {
        current = new Disease();
        selectedItemIndex = -1;
        currentDisease = null;
        return "/JSF/disease/Create";
    }
    
    public String create() {
        try {
            current.setType(DiseaseController.selectedNode.toString());
            getFacade().create(current);
            currentDisease = current;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DiseaseCreated"));
            return ("/JSF/patientDemographic/Visit");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Disease) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DiseaseUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Disease) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DiseaseDeleted"));
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
    public static TreeNode selectedNode;

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getTreeDiagnoses() {
        TreeNode root = new DefaultTreeNode("Root", "Root", null);

        TreeNode node0 = new DefaultTreeNode("Diseasis of the nervous system", root);
        TreeNode node1 = new DefaultTreeNode("Diseasis of musculoskeletal system and connective tissue", root);

        TreeNode node00 = new DefaultTreeNode("Inflammatory diseases of the central nervous system", node0);
        TreeNode node01 = new DefaultTreeNode("systemic atrophies primarily affecting the central nervous system", node0);
        TreeNode node02 = new DefaultTreeNode("Extrapyramidal and movement disorders", node0);
        TreeNode node03 = new DefaultTreeNode("Demyelinating diseases of the central nervous system", node0);
        TreeNode node04 = new DefaultTreeNode("Episodic and paroxysmal disorders", node0);
        TreeNode node05 = new DefaultTreeNode("Nerve, nerve root and plexus disorders", node0);


        TreeNode node10 = new DefaultTreeNode("Infectious arthropathies", node1);
        TreeNode node11 = new DefaultTreeNode("Inflammatory polyathropathies", node1);
        TreeNode node12 = new DefaultTreeNode("Arthrosis", node1);
        TreeNode node13 = new DefaultTreeNode("Other joint disroders", node1);
        TreeNode node14 = new DefaultTreeNode("Systemic connective tissue disorders", node1);
        TreeNode node15 = new DefaultTreeNode("Deforming dorsopathies", node1);


        return root;

    }

    @FacesConverter(forClass = Disease.class)
    public static class DiseaseControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DiseaseController controller = (DiseaseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "diseaseController");
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
            if (object instanceof Disease) {
                Disease o = (Disease) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DiseaseController.class.getName());
            }
        }
    }
}
