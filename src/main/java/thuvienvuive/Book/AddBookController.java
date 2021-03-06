package thuvienvuive.Book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import thuvienvuive.Author.Author;
import thuvienvuive.Author.AuthorController;
import thuvienvuive.Author.SelectAuthorController;
import thuvienvuive.Database.ConnectDB;

public class AddBookController implements Initializable {
	public static String authorName = "";
	static ObservableList<String> cacheList = FXCollections.observableArrayList();
     public static String trangthai = null;
    @FXML
    ImageView bookIcon;
    @FXML
    TextField tenSach;
    @FXML
    TextField soTrang;
    @FXML
	
      TextField tacGia;
    @FXML
    ComboBox<String> theLoai;
    @FXML
    Spinner<Integer> soLuong;
    @FXML
    TextField nxbText;
    @FXML
    TextField giaTien;
    @FXML
    DatePicker ngayNhap;
    @FXML
    Button clearButton;
    @FXML
    Button addButton;
    @FXML
    Button cancelButton;
    @FXML
    TextArea motaSach;
    @FXML
    DatePicker ngayXB;
    @FXML
    ImageView anhBia;
    @FXML
    Label linkImage;
    @FXML
    Button imageButton;
    @FXML
	private Button exitButton;
	@FXML
	TableView<Author> authorTable;
	@FXML 
	Button authorButton;
    Stage stage;
    static String co="";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      System.out.println(layTenSach("T??i t??i gi???i"));
        setIcon();
        addCombobox();
        setSpinner();
        fileChoose();
         setCache();
         cacheList.removeAll(cacheList);
     this.tacGia.setText(authorName);
    
 	soTrang.textProperty().addListener((observable, oldValue, newValue) -> {
 		if(newValue != null) {
        if(!newValue.matches("^[0-9]{0,6}$"))
        {
        	Alert alert = new Alert(AlertType.WARNING, "Vui l??ng nh???p s???", ButtonType.OK);
	    	alert.setTitle("C???nh b??o nh???p s??? trang");
	    	soTrang.setText("");
	    	soTrang.setFocusTraversable(true);
	    	alert.showAndWait();
			return;
        }
 		}
    });
 	giaTien.textProperty().addListener((observable, oldValue, newValue) -> {
 		if(newValue != null) {
        if(!newValue.matches("^[0-9]{0,6}$"))
        {
        	Alert alert = new Alert(AlertType.WARNING, "Vui l??ng nh???p s???", ButtonType.OK);
	    	alert.setTitle("C???nh b??o nh???p s??? trang");
	    	giaTien.setText("");
	    	giaTien.setFocusTraversable(true);
	    	alert.showAndWait();
			return;
        }
 		}
    });
  
        
    }
    public static void setCo(String string)
    {
    	AddBookController.co=string;
    }
  
    public  void setIcon(){
        File iconFile = new File("src/image/copy_139314729.png");
        Image iconBook=new Image(iconFile.toURI().toString());
        bookIcon.setImage(iconBook);
        
        Image image = null;
			try {
				image = new Image(new FileInputStream("src/image/exit.png"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageView imageView = new ImageView(image);

			imageView.setFitWidth(60);

			imageView.setFitHeight(43);

			exitButton.setGraphic(imageView);

    }
    public void exit(ActionEvent event) {
		
    	Alert alert = new Alert(AlertType.CONFIRMATION, "B???n c?? mu???n tho??t " + " ?", ButtonType.YES, ButtonType.NO);
    	alert.setTitle("Tho??t");
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    	    //do stuff
    	
		  stage = (Stage)((Button)event.getSource()).getScene().getWindow();                     
        stage.close();
    	}
	}
    @FXML
    public void choiceAuthor(ActionEvent event) throws Exception {  
    	
    	
if(ngayNhap.getValue()==null && ngayXB.getValue() != null) {
	 AddBookController.setCo("ngaynhap"); 
    	cacheList.addAll(linkImage.getText(),motaSach.getText(),giaTien.getText(),nxbText.getText(),soLuong.getValue().toString(),theLoai.getValue(),soTrang.getText(),tenSach.getText(),ngayXB.getValue().toString());
}else if(ngayNhap.getValue()!=null && ngayXB.getValue() == null)
{
	 AddBookController.setCo("ngayxuat"); 
	cacheList.addAll(linkImage.getText(),motaSach.getText(),giaTien.getText(),nxbText.getText(),soLuong.getValue().toString(),theLoai.getValue(),soTrang.getText(),tenSach.getText(),ngayNhap.getValue().toString());

}else if(ngayNhap.getValue()!=null && ngayXB.getValue() != null)
{
	cacheList.addAll(linkImage.getText(),motaSach.getText(),giaTien.getText(),nxbText.getText(),soLuong.getValue().toString(),theLoai.getValue(),soTrang.getText(),tenSach.getText(),ngayNhap.getValue().toString(),ngayXB.getValue().toString());

}
else {
	cacheList.addAll(linkImage.getText(),motaSach.getText(),giaTien.getText(),nxbText.getText(),soLuong.getValue().toString(),theLoai.getValue(),soTrang.getText(),tenSach.getText());

}
stage =(Stage)((Node) event.getSource()).getScene().getWindow();
stage.close();

    	Stage stage = new Stage(); 
    		
    			//(Stage)((Node) event.getSource()).getScene().getWindow();
    	
        FXMLLoader loader = new FXMLLoader(AuthorController.class.getResource("SelectAuthorList.fxml"));
        Scene scene = new Scene(loader.load());    
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    	
}
    
    
    public void setAddBook(ActionEvent event) {
    	 ObservableList<String> listAdd = FXCollections.observableArrayList();
      	 ObservableList<String> listTitle = FXCollections.observableArrayList();
 		
      	 listTitle.addAll("???nh b??a s??ch","m?? t??? s??ch","gi?? ti???n","nh?? xu???t b???n",soLuong.getValue().toString(),"th??? lo???i","s??? trang","t??n s??ch","t??c gi???");

    		listAdd.addAll(linkImage.getText(),motaSach.getText(),giaTien.getText(),nxbText.getText(),soLuong.getValue().toString(),theLoai.getValue(),soTrang.getText(),tenSach.getText(),tacGia.getText());
    		for(int i=0 ; i<listAdd.size();i++)
    		{
    			if( listAdd.get(4).equalsIgnoreCase("0"))
    			{
    				Alert alert = new Alert(AlertType.WARNING, "Vui l??ng nh???p ????ng s??? l?????ng s??ch c???n nh???p !!!", ButtonType.OK);
    		    	alert.setTitle("C???nh b??o nh???p s??? l?????ng s??ch !!!");
    		    	alert.showAndWait();
    				return;
    			}
    			if( listAdd.get(5)==null)
    			{
    				Alert alert = new Alert(AlertType.WARNING, "Vui l??ng ch???n th??? lo???i s??ch !!!", ButtonType.OK);
    		    	alert.setTitle("C???nh b??o ch???n th??? lo???i s??ch !!!");
    		    	alert.showAndWait();
    				return;
    			}
    			if( listAdd.get(0).isEmpty())
    			{
    				Alert alert = new Alert(AlertType.WARNING, "Vui l??ng ch???n ???nh b??a s??ch !!!", ButtonType.OK);
    		    	alert.setTitle("C???nh b??o ch???n ???nh b??a s??ch !!!");
    		    	alert.showAndWait();
    				return;
    			}
    			if(listAdd.get(i).isEmpty())
    			{
    				Alert alert = new Alert(AlertType.WARNING, "Vui l??ng nh???p " + listTitle.get(i), ButtonType.OK);
    		    	alert.setTitle("C???nh b??o nh???p " + listTitle.get(i));
    		    	alert.showAndWait();
    				return;
    			}
             
    		}
    		if(ngayNhap.getValue()==null)
    		{
    			Alert alert = new Alert(AlertType.WARNING, "Vui l??ng ch???n th???i gian nh???p s??ch!!!", ButtonType.OK);
		    	alert.setTitle("C???nh b??o nh???p th???i gian!!!");
		    	alert.showAndWait();
				return;
    		}
    		if(ngayXB.getValue()==null)
    		{
    			Alert alert = new Alert(AlertType.WARNING, "Vui l??ng ch???n th???i gian xu???t b???n s??ch!!!", ButtonType.OK);
		    	alert.setTitle("C???nh b??o nh???p th???i gian!!!");
		    	alert.showAndWait();
				return;
    		}
    		
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDate localnhap = LocalDate.parse(ngayNhap.getValue().toString(), formatter);
    		LocalDate localxuat = LocalDate.parse(ngayXB.getValue().toString(), formatter);
    		if(layTenSach(tenSach.getText())!=null)
    		{
    			Alert alert = new Alert(AlertType.INFORMATION,"S??ch" + tenSach .getText() + " ???? t???n t???i trong th?? vi???n, ch??? c???n c???p nh???t l???i s??? l?????ng trong ch???c n??ng s???a th??ng tin s??ch.", ButtonType.OK);
    	    	alert.setTitle("Th??ng b??o s??ch ???? t???n t???i!!!");
    	    	alert.showAndWait();
    			return;
    		}
   String string=theLoai.getValue(); //t??ch chu???i
    		String[] parts = string.split("-");
    	Book bookDTO = new Book("IDSach"+layMa(),tenSach.getText(),tacGia.getText(),soLuong.getValue(),localxuat,localnhap,Integer.parseInt(soTrang.getText()),motaSach.getText(),parts[0], linkImage.getText(),Float.parseFloat(giaTien.getText()));
    	if(themSach(bookDTO) >0)
    	{
    		Alert alert = new Alert(AlertType.INFORMATION, "Th??m s??ch th??nh c??ng !!!", ButtonType.OK);
	    	alert.setTitle("Th??m s??ch th??nh c??ng!!!");
	    	alert.showAndWait();
			return;
    	}else {
    		Alert alert = new Alert(AlertType.INFORMATION, "Th??m s??ch th???t b???i !!!", ButtonType.OK);
	    	alert.setTitle("Th??m s??ch th???t b???i!!!");
	    	alert.showAndWait();
			return;
		}
	}
    
    public int layMa() {
    	int ma=0;
    	ConnectDB conn = new ConnectDB();
    	 ResultSet id = null;
    	try {
			id=conn.excuteQuery("select * from Sach");
             if(id!=null)
             {
            	 while (id.next()) {
                    
                  		ma++;   
            	 }
             }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ma+1;
    	
	}
    public String layTenSach(String ten) {
    	
    	String tenSach=null;
    	String qryString="select TenSach from Sach Where TenSach=N'".concat(ten).concat("'");
    	ConnectDB conn = new ConnectDB();
    	 ResultSet id = null;
    	try {
			id=conn.excuteQuery(qryString);
             if(id!=null)
             {
               while (id.next()) {
	              
            		 tenSach=id.getString("TenSach");           		 
         	    }
            	                
             }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return tenSach;
    	
	}
    public int themSach(Book book) { //c???n ghi l???i khi qua class kh??c
    	ConnectDB connection = new ConnectDB();
    	String   qry = "INSERT INTO Sach values (";
        qry = qry + "'" + book.getID() + "'";
        qry = qry + "," + "'" + book.getTen() + "'";
        qry = qry + "," + "'" + book.getIDTacGia() + "'";
        qry = qry + "," + "'" + book.getSoLuong() + "'";
        qry = qry + "," + "'" + book.getGiaTien() + "'";
        qry = qry + "," + "'" + book.getNgayXuatBan() + "'";
        qry = qry + "," + "'" + book.getNgayNhanSach() + "'";
        qry = qry + "," + "'" + book.getSoTrang() + "'";
        qry = qry + "," + "'" + book.getGhiChu() + "'";
        qry = qry + "," + "'" + book.getIDTheLoai() + "'";
        qry = qry + "," + "'" + book.getHinhAnh() + "'";
        qry = qry + ")";
    	
    	int check = 0;
        try {
                  connection.getStatement();
           check= connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
        return check;
    }
    
    public void setCancel(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "H???y " + " ?", ButtonType.YES, ButtonType.NO);
    	alert.setTitle("H???y th??m s??ch");
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    	    //do stuff
    	
		  stage = (Stage)((Button)event.getSource()).getScene().getWindow();                     
        stage.close();
    	}
	}
    public void addCombobox() {
    	ConnectDB conn = new ConnectDB();
    	 ObservableList<String> list = FXCollections.observableArrayList();
		ResultSet m;
		try {
		m=conn.excuteQuery("Select * from TheLoai");
	
	     if (m != null) {
             while (m.next()) {
               list.addAll(m.getString("IDTheLoai") + "-" + m.getString(2));
             }
         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	  theLoai.setItems(list);	
    	  
	}
    public void setSpinner() {
    SpinnerValueFactory<Integer> valueFactory = 
                 new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
    	           soLuong.setValueFactory(valueFactory);
         
	}
    public void fileChoose() {
    	final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
             Button button = new Button("Open DirectoryChooser and select a directory");
         imageButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                File file=fileChooser.showOpenDialog(stage);
                 
                 if (file != null) {
                     // pickUpPathField it's your TextField fx:id
                     linkImage.setText(file.getPath());
                     File fileimg = new File(file.getPath());
                     Image image = new Image(fileimg.toURI().toString());                  
                     anhBia.setImage(image);
                                   } 
             }
         });
	
	}
    private void configuringFileChooser(FileChooser fileChooser) {
    	 
        // Set ti??u ????? cho FileChooser
        fileChooser.setTitle("Select Pictures");

  
        // S??t th?? m???c b???t ?????u nh??n th???y khi m??? FileChooser
        fileChooser.setInitialDirectory(new File("D:/???nh"));

  
        // Th??m c??c b??? l???c file v??o
        fileChooser.getExtensionFilters().addAll(//
            //
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), //
                new FileChooser.ExtensionFilter("PNG", "*.png"));
    }
    public void setCache() {
    	if (cacheList.size()>1) {
    	
    	linkImage.setText(cacheList.get(0));
    	motaSach.setText(cacheList.get(1));
    	giaTien.setText(cacheList.get(2));
    	nxbText.setText(cacheList.get(3));
      	 SpinnerValueFactory<Integer> valueFactory = 
                 new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,Integer.parseInt(cacheList.get(4)));
    	           soLuong.setValueFactory(valueFactory);
    	theLoai.setValue(cacheList.get(5));
    	soTrang.setText(cacheList.get(6));
    	tenSach.setText(cacheList.get(7));
    	  File fileimg = new File(cacheList.get(0));
          Image image = new Image(fileimg.toURI().toString());  
          anhBia.setImage(image);
        
      	}
    	  if(cacheList.size() == 9) {
        	  if(co.equalsIgnoreCase("ngaynhap"))
        	  {
          convertString(null,cacheList.get(8));
        	  }else if (co.equalsIgnoreCase("ngayxuat")){
				convertString(cacheList.get(8), null);
			   }
             }
         if (cacheList.size() == 10) {
        	 convertString(cacheList.get(8),cacheList.get(9));
		       }
		}
    public void setClear(ActionEvent event)
    {
    	linkImage.setText(null);
    	ngayNhap.setValue(null);
    	motaSach.setText(null);
    	giaTien.setText(null);
    	nxbText.setText(null);
      	setSpinner();
    	theLoai.setValue(null);
    	soTrang.setText(null);
    	tenSach.setText(null);
    	tacGia.setText(null);
    	anhBia.setImage(null);
    	ngayXB.setValue(null);
    }
    public void setAuthorText() {
    	 Author select = authorTable.getSelectionModel().getSelectedItem();
         
         String nameString = select.getFistName() + " " + select.getLastName();
     
      		this.tacGia.setText(nameString);    	     
       
	}
    private void updateDatePicker(DatePicker datePicker, LocalDateTime newValue) {
    	  if (newValue == null) {
    	    if (datePicker.getValue() != null)
    	      datePicker.setValue(null);
    	    return;
    	  }
    	  LocalDate current = datePicker.getValue();
    	  if (current != null && current.equals(newValue.toLocalDate()))
    	    return;
    	  datePicker.setValue(newValue.toLocalDate());
    	}

    public void convertString(String daynhap,String dayxuat)
    {
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	if(daynhap==null) {
    	    	 LocalDate localxuat = LocalDate.parse(dayxuat, formatter);
    			ngayXB.setValue(localxuat);
    	}else if (dayxuat == null) {
    		 LocalDate localnhap = LocalDate.parse(daynhap, formatter);
		ngayNhap.setValue(localnhap);
    	}else if(daynhap != null && dayxuat != null)
    	{
    		 LocalDate localxuat = LocalDate.parse(dayxuat, formatter);
    		 LocalDate localnhap = LocalDate.parse(daynhap, formatter);
    		 ngayXB.setValue(localxuat);
    		 ngayNhap.setValue(localnhap);
    	}else {
    		 ngayXB.setValue(null);
    		 ngayNhap.setValue(null);
		}
    }
    
public static void changeStatus(String abc)
{
	AddBookController.trangthai=abc;
}


}
