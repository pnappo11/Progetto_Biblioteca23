package biblioteca.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainFrame {

    private final BorderPane root;
    private final TabPane tabPane;
    private final LibriPanel libriView;
    private final UtentiPanel utentiView;
    private final PrestitiPanel prestitiView;
    private final Button btnMenu;
    private final Button btnLogout;
    private final Tab tabPrestiti;

    public MainFrame() { this(0); }

    public MainFrame(int tabIndex) {
        root = new BorderPane();
        root.getStyleClass().add("app-wood");
        root.getStyleClass().add("app-root");

        tabPane = new TabPane();
        tabPane.getStyleClass().add("tabs-flat");

        libriView = new LibriPanel();
        utentiView = new UtentiPanel();
        prestitiView = new PrestitiPanel();

        Tab tabLibri = new Tab("Libri", libriView.getRoot());
        Tab tabUtenti = new Tab("Utenti", utentiView.getRoot());
        tabPrestiti = new Tab("Prestiti", prestitiView.getRoot());

        tabLibri.setClosable(false);
        tabUtenti.setClosable(false);
        tabPrestiti.setClosable(false);

        tabPane.getTabs().addAll(tabLibri, tabUtenti, tabPrestiti);
        tabPane.getSelectionModel().select(tabIndex);

        final int nTabs = tabPane.getTabs().size();
        tabPane.widthProperty().addListener((obs, oldW, newW) -> {
            double available = newW.doubleValue() - 60;
            double w = Math.max(120, available / nTabs);
            tabPane.setTabMinWidth(w);
            tabPane.setTabMaxWidth(w);
        });

        BorderPane topBar = new BorderPane();
        topBar.getStyleClass().add("topbar");
        topBar.setPadding(new Insets(6, 6, 6, 6));

        btnMenu = new Button("Menu");
        btnMenu.getStyleClass().add("btn-secondary");

        btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("btn-danger");

        Parent centro;
        try {
            Image img = new Image(getClass().getResourceAsStream("/biblioteca/view2/img/logo.png"), 96, 96, true, true);
            ImageView logo = new ImageView(img);
            logo.setPreserveRatio(true);
            logo.setFitWidth(36);
            logo.setFitHeight(36);
            logo.setSmooth(true);
            logo.setCache(true);
            logo.setCacheHint(CacheHint.QUALITY);

            StackPane logoWrap = new StackPane(logo);
            logoWrap.getStyleClass().add("brand-logo-wrap");

            Label lblTitolo = new Label("Gestione Biblioteca");
            lblTitolo.getStyleClass().add("brand-title");

            Label lblSub = new Label("Biblioteca Universitaria");
            lblSub.getStyleClass().add("brand-subtitle");

            VBox testo = new VBox(0, lblTitolo, lblSub);
            testo.setAlignment(Pos.CENTER_LEFT);

            HBox brand = new HBox(10, logoWrap, testo);
            brand.setAlignment(Pos.CENTER);
            brand.getStyleClass().add("brand");

            centro = brand;
        } catch (Exception e) {
            Label lblTitolo = new Label("Gestione Biblioteca");
            lblTitolo.getStyleClass().add("page-title");
            BorderPane.setAlignment(lblTitolo, Pos.CENTER);
            centro = lblTitolo;
        }

        topBar.setLeft(btnMenu);
        topBar.setCenter(centro);
        topBar.setRight(btnLogout);
        BorderPane.setAlignment(centro, Pos.CENTER);

        VBox shell = new VBox(10, topBar, tabPane);
        shell.getStyleClass().add("shell");
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        root.setCenter(shell);
        BorderPane.setMargin(shell, new Insets(12));
    }

    public Parent getRoot() { return root; }
    public LibriPanel getLibriView() { return libriView; }
    public UtentiPanel getUtentiView() { return utentiView; }
    public PrestitiPanel getPrestitiView() { return prestitiView; }
    public Button getBtnLogout() { return btnLogout; }
    public Button getBtnMenu() { return btnMenu; }

    public void setOnTabPrestitiSelected(Runnable action) {
        tabPane.getSelectionModel().selectedItemProperty().addListener((o, oldT, newT) -> {
            if (newT == tabPrestiti && action != null) action.run();
        });
    }
}
