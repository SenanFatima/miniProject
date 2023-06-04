package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import application.student;


public class StudentDAO extends Application {
	private ObservableList<student> studentList; 
	TableView<student> table = new TableView<>();
	
	private TextField nomTextField;
	private TextField prenomTextField;
	private ComboBox<String> filiereComboBox;
	private TextField emailTextField;
	Stage primaryStage;
	Scene scene3;
	Scene scene2;
	Scene scene1;

	Button retourbtn;
	@Override
	public void start(Stage primaryStage) {
		try {
			//scene2
			
			
			TableColumn<student, String> prenomCol = new TableColumn<>("Prénom");
			prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
			
			TableColumn<student,String> nomCol = new TableColumn<student,String>("Nom");
			nomCol.setCellValueFactory(new PropertyValueFactory<student,String>("nom"));
			
			TableColumn<student,String> filiereCol = new TableColumn<student,String>("Filière");
			filiereCol.setCellValueFactory(new PropertyValueFactory<student,String>("filiere"));
			
			TableColumn<student,String> emailCol = new TableColumn<student,String>("Email");
			emailCol.setCellValueFactory(new PropertyValueFactory<student,String>("email"));
			
			TableColumn<student, Void> editCol = new TableColumn<>("Edit");
			  editCol.setCellFactory(param -> {
		            Button editButton = new Button("Edit");
		            TableCell<student, Void> cell = new TableCell<>() {
		                @Override
		                protected void updateItem(Void item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (empty) {
		                        setGraphic(null);
		                    } else {
		                        setGraphic(editButton);
		                    }
		                }
		            };

		            editButton.setOnAction(event -> {
		                student person = table.getItems().get(cell.getIndex());
		                editContact(person);
		            });

		            return cell;
		        });
			  
			  
			//bouton delete
		        TableColumn<student, Void> deleteCol = new TableColumn<>("Delete");
		        deleteCol.setCellFactory(param -> {
		            Button deleteButton = new Button("Delete");
		            TableCell<student, Void> cell = new TableCell<>() {
		                @Override
		                protected void updateItem(Void item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (empty) {
		                        setGraphic(null);
		                    } else {
		                        setGraphic(deleteButton);
		                    }
		                }
		            };

		            deleteButton.setOnAction(event -> {
		                student person = table.getItems().get(cell.getIndex());
		                deleteContact(person);
		            });

		            return cell;
		        });
		        table.getColumns().addAll(prenomCol, nomCol, filiereCol, emailCol, editCol, deleteCol);
		       

			
			//prenomCol.setPrefWidth(100);
			//nomCol.setPrefWidth(100);
			//filiereCol.setPrefWidth(100);
			//emailCol.setPrefWidth(100);						
			
			Button updateButton = new Button("Update");
			updateButton.setOnAction(event -> updateContact());
			
			Button retourbtn = new Button("Back");
			retourbtn.setOnAction(event -> {
			    primaryStage.setScene(scene1);
			});
			
	        
			
			HBox button = new HBox(20);
	        VBox root1 = new VBox(20);
	        
	        root1.getChildren().addAll(table,button);
	        button.getChildren().addAll(retourbtn);
	        
	        button.setLayoutX(10);
            button.setLayoutY(30);
			scene2 = new Scene(root1, 500, 500);
			//css scene2
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//scene1
			//buttons
			Button btn1=new Button("Create");
			Button btn2=new Button("Read");
			btn2.setOnAction(event ->{primaryStage.setScene(scene2);
			
			// Récupérer les données de la base de données
		    List<student> students = getStudentsFromDatabase();

		    // Effacer les données existantes dans la TableView
		    table.getItems().clear();

		    // Ajouter les données récupérées à la TableView
		    table.getItems().addAll(students);
			
			});
			HBox box1 = new HBox(50);
			box1.getChildren().addAll(btn1,btn2);
			box1.setAlignment(Pos.CENTER);
			Text titre= new Text("Gestion de l'inscription");
			//create image object
			Image image1=new Image(getClass().getResource("/application/img_logo.jpeg").toExternalForm());
			//creating ImageView for adding images
			ImageView imageView1=new ImageView();
			imageView1.setImage(image1);
			VBox box2=new VBox();
			//HBox box = new HBox();
			//box.getChildren().add(titre);
			//box.setAlignment(Pos.CENTER);
			box2.setAlignment(Pos.CENTER);
			box2.getChildren().add(titre);
			box2.getChildren().add(imageView1);
			box2.getChildren().add(box1);
			box2.setMargin(box1, new Insets(30, 30, 30, 30));
			box2.setMargin(titre, new Insets(30, 30, 30, 30));
			scene1= new Scene(box2,500,500);
			
			//String css=this.getClass().getResource("style.css").toExternalForm();
			//scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			//scene1.getStylesheets().add(css);
			//primaryStage.show();
			
			
			
			//scene3
			GridPane grid = new GridPane();
	        VBox root = new VBox(20, grid);
	        root.setAlignment(Pos.CENTER);

	         scene3 = new Scene(root, 500, 500);
	        //scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setScene(scene3);
	        primaryStage.setTitle("Registration");
	        
	        btn1.setOnAction(e ->{primaryStage.setScene(scene3);});

	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        Text scenetitle = new Text("Registration Form");
	        scenetitle.setId("welcome-text");
	        scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

	        grid.add(scenetitle, 0, 0, 2, 1);
	        
	        	        	      

	        Label prenomLabel = new Label("Prénom:");
	        grid.add(prenomLabel, 0, 2);
	         prenomTextField = new TextField();
	        grid.add(prenomTextField, 1, 2);

	        Label nomLabel = new Label("Nom:");
	        grid.add(nomLabel, 0, 3);
	        nomTextField = new TextField();
	        grid.add(nomTextField, 1, 3);

	        Label filiereLabel = new Label("Filière:");
	        grid.add(filiereLabel, 0, 4);
	        filiereComboBox = new ComboBox<>();
	        filiereComboBox.getItems().addAll(
	            "Génie Informatique",
	            "Génie Civil",
	            "Génie Industriel",
	            "Génie Électrique",
	            "Cyber Security",
	            "BigData",
	            "GSEIR",
	            "ITIRC" );
	        grid.add(filiereComboBox, 1, 4);

	        Label emailLabel = new Label("Email:");
	        grid.add(emailLabel, 0, 5);
	        emailTextField = new TextField();
	        grid.add(emailTextField, 1, 5);

	        Button submitBtn = new Button("Submit");
	   
	        HBox btnBox = new HBox(10);
	        btnBox.setAlignment(Pos.BOTTOM_RIGHT);
	        btnBox.getChildren().addAll(submitBtn,updateButton);

	        grid.add(btnBox, 1, 6);

	        final Text actiontarget = new Text();
	        actiontarget.setId("actiontarget");
	        grid.add(actiontarget, 1, 7);

	        
	        
	        submitBtn.setOnAction(event -> {
	            // Récupérer les valeurs des champs du formulaire
	            
	           
	            String prenom = prenomTextField.getText();
	            String nom = nomTextField.getText();
	            String filiere = filiereComboBox.getValue();
	            String email = emailTextField.getText();

	            // Appeler la méthode addStudent() pour ajouter un nouvel étudiant
	            addStudent(prenom, nom, filiere, email);

	            // Effacer les champs du formulaire après soumission
	            
	            
	            prenomTextField.clear();
	            nomTextField.clear();
	            filiereComboBox.getSelectionModel().clearSelection();
	            emailTextField.clear();
	        });

	        // ... 
	        
	      
	        studentList = FXCollections.observableArrayList();

	        
	        

	        // Remplir le TableView avec l'ObservableList d'étudiants
	        
	        table.setItems(studentList);
	        
	        table.getStyleClass().add("my-table");
	     
	        
	        
	        
	        Button back = new Button("Back");
	        back.setOnAction(event -> {
	        	primaryStage.setScene(scene1);
	            
	        });
	        btnBox.getChildren().add(back);
	        
			
	        primaryStage.setScene(scene1);
	        primaryStage.show();			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

     private void addStudent( String prenom, String nom, String filiere, String email) {
        student newStudent = new student(prenom, nom, filiere, email);
        studentList.add(newStudent);
        table.getItems().add(newStudent);
        
        try {
            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");

            // Créer la requête SQL pour insérer les données de l'étudiant
            String query = "INSERT INTO student (prenom, nom, filiere, email) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
           
            preparedStatement.setString(1, prenom);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, filiere);
            preparedStatement.setString(4, email);

            // Exécuter la requête
            preparedStatement.executeUpdate();

            // Fermer la connexion et le preparedStatement
            preparedStatement.close();
            connection.close();

            
            
            String message = "\n";
            message += "Prénom: " + prenom + "\n";
            message += "Nom: " + nom + "\n";
            message += "Filière: " + filiere + "\n";
            message += "Email: " + email + "\n";
            // Switch to Scene 2
            Stage stage2 = new Stage();
            GridPane printGrid = new GridPane();
            printGrid.setAlignment(Pos.CENTER);
            printGrid.setHgap(10);
            printGrid.setVgap(10);
            printGrid.setPadding(new Insets(30, 35, 30, 35));

            Scene scene2 = new Scene(printGrid, 600, 500);
            scene2.getStylesheets().add("application/application.css");
            stage2.setScene(scene2);
            stage2.setTitle("Print Registration");

            Text printTitle = new Text("Registration Details");
            printTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Label printLabel = new Label(message);

            Button printBtn = new Button("Print");
            printBtn.setOnAction(event -> {
                // Code to print the details
                System.out.println("Printing registration details...");
            });

            printGrid.add(printTitle, 0, 0, 2, 1);
            printGrid.add(printLabel, 0, 1);
            printGrid.add(printBtn, 1, 2);

            stage2.show();
        
            
            
        } catch (SQLException e) {
            e.printStackTrace();
         
            
        }
        
      
        System.out.println("Nouvel étudiant ajouté : " + newStudent.toString());
    }
	
     //affichage
     private List<student> getStudentsFromDatabase() {
    	    List<student> students = new ArrayList<>();

    	    try {
    	        // Établir la connexion à la base de données
    	        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");

    	        // Créer la requête SQL pour récupérer les données des étudiants
    	        String query = "SELECT * FROM student";
    	        Statement statement = connection.createStatement();

    	        // Exécuter la requête
    	        ResultSet resultSet = statement.executeQuery(query);

    	        // Parcourir les résultats et créer les objets student correspondants
    	        while (resultSet.next()) {
    	            
    	           
    	            String prenom = resultSet.getString("prenom");
    	            String nom = resultSet.getString("nom");
    	            String filiere = resultSet.getString("filiere");
    	            String email = resultSet.getString("email");

    	            student newStudent = new student(prenom, nom, filiere, email);
    	            students.add(newStudent);
    	            System.out.println(students);
    	        }

    	        //Fermer les ressources
    	        resultSet.close();
    	        statement.close();
    	        connection.close();
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }

    	    return students;
    	}
     
     private void deleteContact(student person) {
    	 studentList.remove(person);
    	// Effacer les champs de saisie
    	 prenomTextField.clear();
         nomTextField.clear();
         filiereComboBox.getSelectionModel().clearSelection();
         emailTextField.clear();
         
         
         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root")) {
             // Préparer l'instruction SQL pour supprimer l'étudiant
             String sql = "DELETE FROM student WHERE nom = ?";
             try (PreparedStatement statement = connection.prepareStatement(sql)) {
                 statement.setString(1, person.getNom());

                 // Exécuter l'instruction SQL pour supprimer l'étudiant
                 int rowsDeleted = statement.executeUpdate();

                 if (rowsDeleted > 0) {
                     System.out.println("Étudiant supprimé avec succès de la base de données.");
                 } else {
                     System.out.println("Échec de la suppression de l'étudiant de la base de données.");
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     
     private void editContact(student person) {
         
 		// Mettre à jour la variable selectedPerson
     	 
     	  student selectedPerson = table.getSelectionModel().getSelectedItem();
     	  prenomTextField.setText(person.getPrenom());
          nomTextField.setText(person.getNom());
          filiereComboBox.getSelectionModel().select(person.getFiliere());
          emailTextField.setText(person.getEmail());
          
       // Afficher la scène 3 de manière modale
          Stage dialogStage = new Stage();
          dialogStage.initModality(Modality.APPLICATION_MODAL);
          dialogStage.setScene(scene3);
          dialogStage.showAndWait();
      }
     
     private void updateContact() {
    	    // Vérifier si un contact est sélectionné dans la table
    	    student selectedPerson = table.getSelectionModel().getSelectedItem();

    	    if (selectedPerson != null) {
    	        
    	        String prenom = prenomTextField.getText();
    	        String nom = nomTextField.getText();
    	        String filiere = filiereComboBox.getValue();
    	        String email = emailTextField.getText();

    	       
    	        selectedPerson.setPrenom(prenom);
    	        selectedPerson.setNom(nom);
    	        selectedPerson.setFiliere(filiere);
    	        selectedPerson.setEmail(email);

    	       
    	        updateContact(selectedPerson);

    	        // Effacer la sélection dans la table
    	        table.getSelectionModel().clearSelection();

    	        // Effacer les champs de saisie
    	        prenomTextField.clear();
    	        nomTextField.clear();
    	        filiereComboBox.getSelectionModel().clearSelection();
    	        emailTextField.clear();

    	        // Rafraîchir la table pour afficher les modifications
    	        table.refresh();
    	        
    	    }
    	}

    	private void updateContact(student std) {
    	    String sql = "UPDATE student SET prenom = ?, nom = ?, filiere = ?, email = ? WHERE nom = ?";

    	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
    	         PreparedStatement statement = connection.prepareStatement(sql)) {
    	        statement.setString(1, std.getPrenom());
    	        statement.setString(2, std.getNom());
    	        statement.setString(3, std.getFiliere());
    	        statement.setString(4, std.getEmail());
    	        statement.setString(5, std.getNom()); 
    	        statement.executeUpdate();
    	        
    	        
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    
    	}
	
	
}