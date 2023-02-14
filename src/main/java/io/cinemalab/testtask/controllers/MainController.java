package io.cinemalab.testtask.controllers;

import io.cinemalab.testtask.App;
import io.cinemalab.testtask.HumanHolder;
import io.cinemalab.testtask.models.Human;
import io.cinemalab.testtask.repository.HumanRepository;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MainController implements Initializable {

    @FXML
    private TreeTableView<Human> tableview;
    /*@FXML
    private TableColumn<?, ?> tcID;
    @FXML
    private TableColumn<?, ?> tcFirstName;
    @FXML
    private TableColumn<?, ?> tcLastName;
    @FXML*/
    @FXML
    private TreeTableColumn tcID;
    @FXML
    private TreeTableColumn tcName;
    @FXML
    private TreeTableColumn tcAge;
    @FXML
    private TreeTableColumn tcBirthday;

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfBirthday;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    private ObservableList<Human> humans;
    private Human currentHuman;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("io.cinemalab_testtask_jar_1.0-SNAPSHOTPU");
    final EntityManager em = emf.createEntityManager();
    final HumanRepository humanRepository = new HumanRepository(em);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateHumanList();
        setupTable();
        applyCSS();
    }
    
    private void applyCSS() {
        String buttonStyle = "-fx-text-fill: white; -fx-background-color: blue;-fx-font-size: 16px;"; 
        btnAdd.setStyle(buttonStyle);
        btnEdit.setStyle(buttonStyle);
        btnDelete.setStyle(buttonStyle);
        
        System.out.println(System.getProperty("user.dir") );
        //String tableViewStyle = "-fx-background-color: linear-gradient(to bottom, #1dbbdd44, #93f9b944);-fx-background-radius: 7px 7px 0px 0px;-fx-background-insets: 0 11px 0 0; -fx-padding: 0 0 5px 0;"; 
        
        System.out.println(App.class.getResource("styles.css").toExternalForm());
        tableview.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
    }

    private void updateHumanList() {
        Query rq = em.createNamedQuery("Human.findAll");
        List results = rq.getResultList();
        if (humans == null) {
            humans = FXCollections.observableArrayList(results);
        } else {
            humans.clear();
            humans.addAll(results);
        }
    }

    private void setupColumns() {
        tcID = new TreeTableColumn<Human, Integer>("id");
        tcID.setText("ID");
        tcID.setMinWidth(50);
        tcID.setCellValueFactory(new TreeItemPropertyValueFactory("id"));

        tcName = new TreeTableColumn<Human, String>("name");
        tcName.setText("Name");
        tcName.setMinWidth(200);
        tcName.setCellValueFactory(new TreeItemPropertyValueFactory("name"));

        tcAge = new TreeTableColumn<Human, Integer>("age");
        tcAge.setText("Age");
        tcAge.setMinWidth(50);
        tcAge.setCellValueFactory(new TreeItemPropertyValueFactory("age"));

        tcBirthday = new TreeTableColumn<Human, Date>("birthday");
        tcBirthday.setText("Birthday");
        tcBirthday.setMinWidth(200);
        tcBirthday.setCellValueFactory(new TreeItemPropertyValueFactory("birthday"));

        tableview.getColumns().addAll(tcID, tcName, tcAge, tcBirthday);
    }

    private void setupTable() {
        setupColumns();

        TreeItem root = new TreeItem(new Human("Humans"));
        tableview.setRoot(root);

        humans.forEach((human) -> {
            TreeItem obj = new TreeItem(human);
            root.getChildren().add(obj);
        });

        // hid root item as it does not make sense to have it 
        tableview.setShowRoot(false);
        setSortPolicy();
    }

    private void setSortPolicy() {
        tcID.setSortable(false);
        tcAge.setSortable(false);
        tcBirthday.setSortable(false);

        tcName.setSortType(TreeTableColumn.SortType.ASCENDING);
        tableview.getSortOrder().setAll(tcName);
    }

    @FXML
    public void handleButtonAddPress(ActionEvent event) throws IOException {
        App.setRoot("AddHuman");
    }

    @FXML
    public void handleButtonEditPress(ActionEvent event) throws IOException {

        TreeItem<Human> selectedItem = tableview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Human h = selectedItem.getValue();

            HumanHolder.getInstance().setHuman(h);
            App.setRoot("EditHuman");
        }
    }

    @FXML
    public void handleButtonDeletePress(ActionEvent event) throws IOException {

        TreeItem<Human> selectedItem = tableview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Human h = selectedItem.getValue();
            if (!humanRepository.delete(h).isPresent()) { // it means no errors

                int selIndex = tableview.getSelectionModel().getSelectedIndex();
                tableview.getRoot().getChildren().remove(selIndex);
            }
        }
    }

    @FXML
    public void handleTableViewMouseClicked(MouseEvent event) throws IOException {
        boolean isDoubleClick = event.getClickCount() == 2;

        if (isDoubleClick) {
            TreeItem<Human> selectedItem = tableview.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Human h = selectedItem.getValue();

                Date birthday = (java.sql.Date) h.getBirthday();
                int birthdayMonth = birthday.getMonth() + 1;
                int birthdayDay = birthday.getDate();

                LocalDateTime today = LocalDateTime.now();
                int todayMonth = today.getMonthValue();
                int todayDay = today.getDayOfMonth();

                boolean isBirthdayToday = (todayMonth == birthdayMonth) && (todayDay == birthdayDay);

                if (isBirthdayToday) {
                    // create a alert
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("Birthday");
                    a.setHeaderText(String.format("It's a birthday of %s", h.getName()));
                    a.setContentText(String.format("Happy birthday, %s!", h.getName()));
                    a.show();
                }
            }
        }
    }

}
