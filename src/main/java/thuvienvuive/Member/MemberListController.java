package thuvienvuive.Member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class MemberListController implements Initializable {
    Stage primaryStage;
    MemberListDAO ListDAO = new MemberListDAO();
    static ObservableList<MemberDTO> List;
    {
        try {
            List = ListDAO.readListDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button closeButton;

    @FXML
    private TableView<MemberDTO> memberList;

    @FXML
    private TableColumn<MemberDTO, String> emailColumn;

    @FXML
    private TableColumn<MemberDTO, String> firstNameColumn;

    @FXML
    private TableColumn<MemberDTO, String> genderColumn;

    @FXML
    private TableColumn<MemberDTO, String> lastNameColumn;

    @FXML
    private TableColumn<MemberDTO, String> phoneNumberColumn;

    @FXML
    private TableColumn<MemberDTO, String> IDColumn;

    @FXML
    private ComboBox<String> searchBox;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane wrap;

    @FXML
    private ImageView memberAvavtar;

    @FXML
    private Button searchButton;

    @FXML
    private TreeView<String> informationView;

    @FXML
    ImageView titleIcon;
    @FXML
    ImageView closeIcon;
    @FXML
    ImageView searchIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefault();
        try {
            show(List);
        } catch (Exception e){
            e.printStackTrace();
        }
        handler();
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
    public void show(ObservableList<MemberDTO> List){
        //g??n gi?? tr??? v??o ?? trong b???ng
        IDColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("ID"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("ho"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("ten"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("soDienThoai"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("email"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("gioiTinh"));
        memberList.setItems(List);
    }

    //ch???n ki???u t??m ki???m trogng ph???n ti???m ki???m
    private String searchType(){
        return searchBox.getSelectionModel().getSelectedItem();
    }

    //l???y chi ti???t th??ng tin th??nh vi??n
    private void memberDetails(MemberDTO member){
        //kh??ng ch???n b???t k?? h??ng n??o
        if(member == null) {

        }
        //ch???n 1 h??ng trong b???ng
        else{
            //l???y avatar c???a th??nh vi??n
//            Image avatar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../../image/avatar/non_avatar.png")));
//            memberAvavtar.setImage(avatar);
            File iconFile = new File("src/image/avatar/" + member.getHinhAnh());
            Image image = new Image(iconFile.toURI().toString());
            memberAvavtar.setImage(image);

            //t???o treeView ????? hi???n th??? chi ti???t th??ng tin th??nh vi??n
            TreeItem<String> root = new TreeItem<>("Th??ng tin:");

            TreeItem<String> nodeHoVaTen = new TreeItem<>("H??? v?? t??n:");
            TreeItem<String> hoVaTen = new TreeItem<>(member.getHo() + " " + member.getTen());

            TreeItem<String> nodeGioiTinh = new TreeItem<>("Gi???i t??nh:");
            TreeItem<String> gioiTinh = new TreeItem<>(member.getGioiTinh());

            TreeItem<String> nodeID = new TreeItem<>("ID:");
            TreeItem<String> ID = new TreeItem<>(member.getID());

            TreeItem<String> nodeSdt = new TreeItem<>("S??? ??i???n tho???i:");
            TreeItem<String> sdt = new TreeItem<>(member.getSoDienThoai());

            TreeItem<String> nodeEmail = new TreeItem<>("Email:");
            TreeItem<String> email = new TreeItem<>(member.getEmail());

            //m??? r???ng khi ch???y
            root.setExpanded(true);
            //th??m node v??o treeView
            root.getChildren().addAll(nodeHoVaTen, nodeGioiTinh, nodeID, nodeSdt, nodeEmail);
            //t???o c??c node con
            nodeHoVaTen.getChildren().add(hoVaTen);
            nodeHoVaTen.setExpanded(true);

            nodeGioiTinh.getChildren().add(gioiTinh);
            nodeGioiTinh.setExpanded(true);

            nodeID.getChildren().add(ID);
            nodeID.setExpanded(true);

            nodeSdt.getChildren().add(sdt);
            nodeSdt.setExpanded(true);

            nodeEmail.getChildren().add(email);
            nodeEmail.setExpanded(true);
            informationView.setRoot(root);
        }

    }

    //????ng c???a s???
    private void close(){
        primaryStage = (Stage) wrap.getScene().getWindow();
        primaryStage.close();
    }

    //x??? l?? c??c s??? ki???n
    private void handler(){
        //????ng c???a s???
        closeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        });
        //l???y chi ti???t th??ng tin th??nh vi??n
        memberList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                memberDetails(selectRow());
            }
        });
        //thi???t l???p cho lo???i t??m ki???m "T???t c???"
        searchBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String searchType = searchType();
                if (searchType.equals("T???t c???")){
                    searchField.setText("");
                    searchField.setEditable(false);
                    try {
                        show(List);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
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
                if (searchType == null){
                    alert("Th??ng b??o", "H??y ch???n lo???i t??m ki???m!");
                }
                //n???u gi?? tr??? t??m ki???m tr???ng
                else if (searchValue.equals("") && !searchType.equals("T???t c???")){
                    alert("Th??ng b??o", "H??y nh???p th??ng tin ????? t??m ki???m!");
                }
                //n???u gi?? tr??? t??m ki???m l?? t???t c???
                else if(searchType.equals("T???t c???")){
                    alert("Th??ng b??o", "???? hi???n t???t c??? danh s??ch!");
                }
                else {
                    //t???o danh s??ch ????? l??u k???t qu??? t??m ki???m
                    ObservableList<MemberDTO> searchList = FXCollections.observableArrayList();
                    //ch???n ki???u t??m ki???m
                    switch (searchType) {
                        case "H???" -> {
                            //?????c danh v?? so s??nh
                            for (MemberDTO member : List) {
                                if (normalizeString(member.getHo()).contains(normalizeString(searchValue))){
                                    //th??m v??o danh s??ch t??m ki???m
                                    searchList.add(member);
                                }

                            }
                            //?????c l???i danh s??ch
                            try {
                                show(searchList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        case "T??n" -> {
                            for (MemberDTO member : List) {
                                if (normalizeString(member.getTen()).contains(normalizeString(searchValue)))
                                    searchList.add(member);
                            }
                            try {
                                show(searchList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        case "S??? ??i???n tho???i" -> {
                            for (MemberDTO member : List) {
                                if (normalizeString(member.getSoDienThoai()).contains(normalizeString(searchValue)))
                                    searchList.add(member);
                            }
                            try {
                                show(searchList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        case "Email" -> {
                            for (MemberDTO member : List) {
                                if (normalizeString(member.getEmail()).contains(normalizeString(searchValue)))
                                    searchList.add(member);
                            }
                            try {
                                show(searchList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        case "ID" -> {
                            for (MemberDTO member : List) {
                                if (normalizeString(member.getID()).contains(normalizeString(searchValue)))
                                    searchList.add(member);
                            }
                            try {
                                show(searchList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        default -> {
                            try {
                                show(List);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    //chu???n ho?? chu???i th??nh in th?????ng v?? b??? kho???ng tr???ng
    private String normalizeString(String string){
        return string.trim().toLowerCase();
    }

    //ch???n 1 d??ng trong b???ng
    private MemberDTO selectRow(){
        //n???u nh?? b???m ngo??i b???ng
        if(memberList.getSelectionModel().getSelectedIndex() < 0){
            return null;
        }
        return memberList.getSelectionModel().getSelectedItem();
    }

    //thi???t l???p c??c gi?? tr??? m???c ?????nh
    private void setDefault(){
        //thi???t l???p c??c ki???u t??m ki???m
        ObservableList<String> searTypeList = FXCollections.observableArrayList(
                new String("T???t c???"),
                new String("H???"),
                new String("T??n"),
                new String("Email"),
                new String("S??? ??i???n tho???i"),
                new String("ID")
        );
        searchField.setEditable(false);
        searchBox.setItems(searTypeList);
        searchBox.getSelectionModel().select(0);
        setIcon();
    }
    public  void setIcon(){
        File iconFile = new File("src/image/member_icon.png");
        Image iconTitle=new Image(iconFile.toURI().toString());
        titleIcon.setImage(iconTitle);
        File iconButton = new File("src/image/exit_icon.png");
        Image ButtonIcon=new Image(iconButton.toURI().toString());
        closeIcon.setImage(ButtonIcon);
        File searchFile = new File("src/image/search_icon.png");
        Image iconSearch=new Image(searchFile.toURI().toString());
        searchIcon.setImage(iconSearch);
    }
}
