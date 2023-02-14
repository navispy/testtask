/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.cinemalab.testtask.controllers;

import io.cinemalab.testtask.App;
import io.cinemalab.testtask.LocalDateConverter;
import io.cinemalab.testtask.models.Human;
import io.cinemalab.testtask.repository.HumanRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author knud
 */
public class AddHumanController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("io.cinemalab_testtask_jar_1.0-SNAPSHOTPU");
    final EntityManager em = emf.createEntityManager();
    final HumanRepository humanRepository = new HumanRepository(em);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // force the age field to be numeric only
        tfAge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    public void handleButtonCancelPress(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }

    @FXML
    public void handleButtonSavePress(ActionEvent event) throws IOException {
        boolean hasEmptyFields = "".equals(tfName.getText().trim());
        hasEmptyFields = hasEmptyFields || "".equals(tfAge.getText().trim());
        hasEmptyFields = hasEmptyFields || dpBirthday.getValue() == null;
        
        if (hasEmptyFields) {
            return;
        }
        String name = tfName.getText();
        int age = Integer.parseInt(tfAge.getText());
        LocalDate birthday = dpBirthday.getValue();

        LocalDateConverter conv = new LocalDateConverter();
        Date sqlDate = conv.convertToDatabaseColumn(birthday);

        Human human = new Human();
        human.setName(name);
        human.setAge(age);
        human.setBirthday(sqlDate);

        humanRepository.save(human);

        App.setRoot("Main");
    }

}
