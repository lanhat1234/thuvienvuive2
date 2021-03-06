package thuvienvuive.Book;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookListController implements Initializable {
    Stage stage;
    BookListDAO ListDAO = new BookListDAO();
    static ObservableList<Book> List;
    {
        try{
            List = ListDAO.docDB();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private Button closeButton;
    @FXML
    ImageView titleIcon;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private DatePicker dateSearchField1, dateSearchField2;
    @FXML
    private TableView<Book> bookList;
    @FXML
    private TableColumn<Book, String> IDBookColumn;
    @FXML
    private TableColumn<Book, String> nameColumn;
    @FXML
    private TableColumn<Book, String> IDauthorColumn;
    @FXML
    private TableColumn<Book, Integer> quantityColumn;
    @FXML
    private TableColumn<Book, Float> priceColumn;
    @FXML
    private TableColumn<Book, LocalDate> publishDateColumn;
    @FXML
    private TableColumn<Book, LocalDate> daterevColumn;
    @FXML
    private TableColumn<Book, String> noteColumn;
    @FXML
    private TableColumn<Book, String> IDgenreColumn;
    @FXML
    private ImageView bookImage;
    @FXML
    private TreeView<String> informationView;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ComboBox<String> searchBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefault();
        try{
            show(List);
        }catch(Exception e){
            e.printStackTrace();
        }
        handler();
        Search2();
    }

    //hi???n th??? th??ng b??o l???i
    private void errorAlert(String title, String Message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.getDialogPane().setStyle("-fx-font-size: 16px;  -fx-cursor: hand;");
        alert.setContentText(Message);
        alert.setHeaderText(null);
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okBtn);
        alert.show();
    }

    //hi???n th??? th??ng b??o
    private void alert(String title, String Message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.getDialogPane().setStyle("-fx-font-size: 16px; -fx-cursor: hand;");
        alert.setContentText(Message);
        alert.setHeaderText(null);
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okBtn);
        alert.show();
    }

    //?????c danh s??ch ????? hi???n th???
    public void show(ObservableList<Book> List){
        IDBookColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("ten"));
        IDauthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("IDTacGia"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("soLuong"));
        publishDateColumn.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("ngayXuatBan"));
        daterevColumn.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("ngayNhanSach"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("ghiChu"));
        IDgenreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("IDTheLoai"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Book, Float>("giaTien"));
        bookList.setItems(List);
    }

    //ch???n ki???u t??m ki???m trogng ph???n ti???m ki???m
    private String searchType(){
        return searchBox.getSelectionModel().getSelectedItem();
    }

    //l???y chi ti???t th??ng tin s??ch
    private void bookDetails(Book bookDTO){
        if(bookDTO == null){

        }
        else{
            File bookFile = new File("src/image/sach/" + bookDTO.getHinhAnh());
            Image image = new Image(bookFile.toURI().toString());
            bookImage.setImage(image);

            //t???o treeView ????? hi???n th??? chi ti???t th??ng tin s??ch
            TreeItem<String> root = new TreeItem<>("Th??ng tin:");

            TreeItem<String> nodeTenSach = new TreeItem<>("T??n s??ch:");
            TreeItem<String> tenSach = new TreeItem<>(bookDTO.getTen());

            TreeItem<String> nodeID = new TreeItem<>("M?? s???:");
            TreeItem<String> iD = new TreeItem<>(bookDTO.getID());

            TreeItem<String> nodeNgayXuatBan = new TreeItem<>("Ng??y xu???t b???n:");
            TreeItem<String> ngayXuatBan = new TreeItem<>(bookDTO.getNgayXuatBan().toString());

            TreeItem<String> nodeGiaTien = new TreeItem<>("Gi?? ti???n:");
            TreeItem<String> giatien = new TreeItem<>(String.valueOf(bookDTO.getGiaTien()));

            //m??? r???ng khi ch???y
            root.setExpanded(true);
            //th??m node v??o treeView
            root.getChildren().addAll(nodeTenSach, nodeID, nodeNgayXuatBan, nodeGiaTien);
            //t???o c??c node con
            nodeTenSach.getChildren().add(tenSach);
            nodeTenSach.setExpanded(true);

            nodeID.getChildren().add(iD);
            nodeTenSach.setExpanded(true);

            nodeNgayXuatBan.getChildren().add(ngayXuatBan);
            nodeTenSach.setExpanded(true);

            nodeGiaTien.getChildren().add(giatien);
            nodeTenSach.setExpanded(true);

            informationView.setRoot(root);
        }
    }
    //????ng c???a s???
    private void close(){
        stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    //ch???n 1 d??ng trong b???ng
    private Book selectRow(){
        //n???u nh?? b???m ngo??i b???ng
        if(bookList.getSelectionModel().getSelectedIndex() < 0){
            return null;
        }
        return bookList.getSelectionModel().getSelectedItem();
    }

    //chu???n ho?? chu???i th??nh in th?????ng v?? b??? kho???ng tr???ng
    private String normalizeString(String string){
        return string.trim().toLowerCase();
    }

    //x??? l?? s??? ki???n
    private void handler() {
        //????ng c???a s???
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        });


        //l???y chi ti???t th??ng tin s??ch
        bookList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                bookDetails(selectRow());
            }
        });

        //thi???t l???p cho lo???i t??m ki???m "T???t c???"
        searchBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String searchType = searchType();
                if (searchType.equals("T???t c???")) {
                    searchField.setText("");
                    searchField.setEditable(false);
                    try {
                        show(List);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    searchField.setEditable(true);
            }
        });

        //t??m ki???m
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //l???y ki???u t??m ki???m
                String searchType = searchType();

                //l???y gi?? tr??? c???n t??m
                String searchValue = searchField.getText();

                //n???u ki???u t??m ki???m r???ng
                if (searchType == null) {
                    alert("Th??ng b??o", "H??y ch???n lo???i t??m ki???m!");
                }

                //n???u gi?? tr??? t??m ki???m tr???ng
                else if (searchValue.equals("") && !searchType.equals("T???t c???")) {
                    alert("Th??ng b??o", "H??y nh???p th??ng tin ????? t??m ki???m!");
                }

                //n???u gi?? tr??? t??m ki???m l?? t???t c???
                else if (searchType.equals("T???t c???")) {
                    alert("Th??ng b??o", "???? hi???n t???t c??? danh s??ch!");
                } else {
                    //t???o danh s??ch ????? l??u k???t qu??? t??m ki???m
                    ObservableList<Book> searchList = FXCollections.observableArrayList();

                    //ch???n ki???u t??m ki???m
                    switch (searchType) {
                        case "T??n s??ch" -> {
                            //?????c danh s??ch so s??nh
                            for(Book book : List){
                                if(normalizeString(book.getTen()).contains(normalizeString(searchValue))){
                                    //th??m v??o danh s??ch t??m ki???m
                                    searchList.add(book);
                                }
                            }
                            //?????c l???i danh s??ch
                            try{
                                show(searchList);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        case "Ghi ch??" -> {
                            //?????c danh s??ch so s??nh
                            for(Book book : List){
                                if(normalizeString(book.getGhiChu()).contains(normalizeString(searchValue))){
                                    //th??m v??o danh s??ch t??m ki???m
                                    searchList.add(book);
                                }
                            }
                            //?????c l???i danh s??ch
                            try{
                                show(searchList);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    //thi???t l???p c??c gi?? tr??? m???c ?????nh
    private void setDefault(){
        //thi???t l???p c??c ki???u t??m ki???m
        ObservableList<String> searTypeList = FXCollections.observableArrayList(
                new String("T???t c???"),
                new String("T??n s??ch"),
                new String("Ghi ch??")
        );
        searchField.setEditable(false);
        searchBox.setItems(searTypeList);
        searchBox.getSelectionModel().select(0);
        setIcon();
    }

    //t??m ki???m theo ng??y
    protected void Search2(){
        FilteredList<Book> filteredItems =  new FilteredList<>(List);
        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                    LocalDate minDate = dateSearchField1.getValue();
                    LocalDate maxDate = dateSearchField2.getValue();

                    // get final values != null
                    final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
                    final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

                    // values for openDate need to be in the interval [finalMin, finalMax]
                    return ti -> !finalMin.isAfter(ti.getNgayNhanSach()) && !finalMax.isBefore(ti.getNgayNhanSach());
                },
                dateSearchField1.valueProperty(),
                dateSearchField2.valueProperty()));
        bookList.setItems(filteredItems);
    }

    public void setIcon(){
        File iconFile = new File("src/image/bookIcon.png");
        Image iconTeam=new Image(iconFile.toURI().toString());
        titleIcon.setImage(iconTeam);
    }
}