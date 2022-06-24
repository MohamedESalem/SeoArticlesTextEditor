
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.UndoManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;

/**
 *
 * @author Mohamed Essam Salem
 */
public class Home extends javax.swing.JFrame {

    static int mouseWeel = 20; // mouse weel Rotation
    static String cursorT;
    static int start; // the start index of the selectText
    static int end; // the end index of the selectText

    private static Highlighter.HighlightPainter redPainter; // highlight color
    private static Highlighter.HighlightPainter orangePainter; // highlight color
    private static Highlighter.HighlightPainter yellowPainter; // highlight color
    private static Highlighter.HighlightPainter greenPainter; // highlight color
    private static Highlighter.HighlightPainter purplePainter; // highlight color
    private static Highlighter.HighlightPainter WhitePainter; // highlight color

    //don't forget if you add any other symbols to add this symbol to this method "remove_BActionPerformed" to make the app can remove any tag
    static final String title_symb = "#_#"; //variable to save the title symbol to use it befor and after the selected text and replace it when write in file with <Title> tag in html
    static final String title_symbEnd = "#_#/"; //variable to save the title symbol to use it befor and after the selected text and replace it when write in file with <Title> tag in html

    static final String h1_symb = "#-#"; //variable to save the h1 symbol to use it befor and after the selected text and replace it when write in file with <h1> tag tag in html
    static final String h1_symbEnd = "#-#/"; //variable to save the h1 symbol to use it befor and after the selected text and replace it when write in file with <h1> tag tag in html

    static final String h2_symb = "#=#"; //variable to save the h2 symbol to use it befor and after the selected text and replace it when write in file with <h2> tag tag in html
    static final String h2_symbEnd = "#=#/"; //variable to save the h2 symbol to use it befor and after the selected text and replace it when write in file with <h2> tag tag in html

    static final String paragraph_symb = "#&#"; //variable to save the paragraph symbol to use it befor and after the selected text and replace it when write in file with <p> tag tag in html
    static final String paragraph_symbEnd = "#&#/"; //variable to save the paragraph symbol to use it befor and after the selected text and replace it when write in file with <p> tag tag in html

    static final String link_symb = "#/#"; //variable to save the link symbol to use it befor and after the selected text and replace it when write in file with <link> tag tag in html
    static final String link_symbEnd = "#/#/"; //variable to save the link symbol to use it befor and after the selected text and replace it when write in file with <link> tag tag in html

    static final String video_symb = "#,#"; //variable to save the video symbol to use it befor and after the selected text and replace it when write in file with <link> tag tag in html
    static final String video_symbEnd = "#,#/"; //variable to save the video symbol to use it befor and after the selected text and replace it when write in file with <link> tag tag in html
    //static final String bold_symb = "#b#"; //variable to save the bold symbol to use it befor and after the selected text and replace it when write in file with <b> tag tag in html

    //Start variables of the symbol loop checker
    static String symb = "";
    static int st;
    static int en = 1;

    static String beginOfText; //to store the last 3 characters of the selected text to use it in the remove button to see if the beginOfText == endOfText
    static String endOfText; //to store the last 3 characters of the selected text to use it in the remove button to see if the beginOfText == endOfText

    static int end_of_text;
    //End variables of the symbol loop checker

    static boolean emptyCursorState; //used to store a state to tite the rule
    static char selectedCharCheck; //used to store the selected char in the if statment to check if the selected char is empty of not
    static boolean stateOne;
    static boolean stateTwo;
    static String selectedStringCheck;
    static boolean charCheckState;

    //Start to check if the text contains any of these tags;
    static boolean charCheckStateTitle;
    static boolean charCheckStateHeader;
    static boolean charCheckStateSubHeader;
    static boolean charCheckStateParag;
    static boolean charCheckStateLink;
    //End to check if the text contains any of these tags;

    String path; // variable to store path that get from the file chooser
    String savePath = null; // variable to store path that get from the file chooser in save

    String beforeCode = ""; // variable to store the code after the article to use it in the build
    //String titleProp = ""; // variable to store the style or properties of the text the will be tagged with Title tag   edit: the title tag does not accept syeling so i deleted it
    String h1Prop = ""; // variable to store the style or properties of the text the will be tagged with h1 tag
    String h2Prop = ""; // variable to store the style or properties of the text the will be tagged with h2 tag
    String pProp = ""; // variable to store the style of or properties the text the will be tagged with p tag
    String linkProp = ""; // variable to store the style or properties of the text the will be tagged with link tag
    String afterCode = ""; // variable to store the code before the article to use it in the build

    //declaring colors
    public static Color myRed;
    public static Color myOrange;
    public static Color myYellow;
    public static Color myGreen;
    public static Color myPurple;

    boolean jTextPaneOriantation; // variable to store if the oriantation of the jTextPane is RTL will be true if LTR will be false

    public Home() {
        URL iconURL = getClass().getResource("img/WATE Logo.png");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        Home.this.setIconImage(icon.getImage());
        initComponents();
        this.setExtendedState(Home.MAXIMIZED_BOTH);

        /////////////////////////
        jTextPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jTextPaneOriantation = false; // change this value if you changed the oriantation of the jTextPane
        ////////////////////////
        jTextPane.setSelectionColor(Color.black);

        myRed = new Color(153, 0, 0); // Color white
        myOrange = new Color(255, 131, 0); // Color white
        myYellow = new Color(243, 194, 0); // Color white
        myGreen = new Color(45, 162, 74); // Color white
        myPurple = new Color(102, 46, 145); // Color white

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane = new javax.swing.JTextPane();
        txtSize_L = new javax.swing.JLabel();
        textSize = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        title_B = new javax.swing.JButton();
        header_B = new javax.swing.JButton();
        subHeader_B = new javax.swing.JButton();
        paragraph_B = new javax.swing.JButton();
        addLink_B = new javax.swing.JButton();
        addVideo_B = new javax.swing.JButton();
        remove_B = new javax.swing.JButton();
        saved_L = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        file_button_menu_bar = new javax.swing.JMenu();
        new_B = new javax.swing.JMenuItem();
        open_B = new javax.swing.JMenuItem();
        save_B = new javax.swing.JMenuItem();
        saveAs_B = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        build_B = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        exit_B = new javax.swing.JMenuItem();
        edit_button_menu_bar = new javax.swing.JMenu();
        undo_B = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        cut_B = new javax.swing.JMenuItem();
        copy_B = new javax.swing.JMenuItem();
        paste_B = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        selectAll_B = new javax.swing.JMenuItem();
        text_button_menu_bar = new javax.swing.JMenu();
        timeDate_B = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        RTL_B = new javax.swing.JMenuItem();
        LTR_B = new javax.swing.JMenuItem();
        code_button_menu_bar = new javax.swing.JMenu();
        beforeCode_B = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        h1Menu_B = new javax.swing.JMenuItem();
        h2Menu_B = new javax.swing.JMenuItem();
        pMenu_B = new javax.swing.JMenuItem();
        linkMenu_B = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        afterCode_B = new javax.swing.JMenuItem();
        code_button_menu_bar2 = new javax.swing.JMenu();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("WebPage Article Text Editor (WATE) Tool");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("home"); // NOI18N
        setSize(new java.awt.Dimension(700, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(233, 233, 233), new java.awt.Color(233, 233, 233), null, null));
        jTextPane.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jTextPaneMouseWheelMoved(evt);
            }
        });
        jScrollPane2.setViewportView(jTextPane);

        txtSize_L.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        txtSize_L.setText("Size:");

        textSize.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        textSize.setText("20");

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(1502, 30));
        jToolBar1.setMinimumSize(new java.awt.Dimension(717, 30));

        title_B.setBackground(new java.awt.Color(153, 0, 0));
        title_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        title_B.setForeground(new java.awt.Color(255, 255, 255));
        title_B.setText("Title");
        title_B.setToolTipText("<html>  <body>   <h5>Select text, Then add Title tag <br><br> NOTE: \"This is your webpage's Title\"</h5>  </body> </html>");
        title_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        title_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        title_B.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        title_B.setFocusable(false);
        title_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        title_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        title_B.setMaximumSize(new java.awt.Dimension(250, 28));
        title_B.setMinimumSize(new java.awt.Dimension(143, 28));
        title_B.setPreferredSize(new java.awt.Dimension(55, 24));
        title_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                title_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                title_BMouseExited(evt);
            }
        });
        title_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                title_BActionPerformed(evt);
            }
        });
        jToolBar1.add(title_B);

        header_B.setBackground(new java.awt.Color(234, 136, 0));
        header_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        header_B.setForeground(new java.awt.Color(255, 255, 255));
        header_B.setText("Header");
        header_B.setToolTipText("<html>  <body>   <h5>Select text, Then add Header tag <br><br> NOTE: \"This is your webpage's Header\"</h5>  </body> </html>");
        header_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        header_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        header_B.setFocusPainted(false);
        header_B.setFocusable(false);
        header_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        header_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        header_B.setMaximumSize(new java.awt.Dimension(250, 28));
        header_B.setMinimumSize(new java.awt.Dimension(143, 28));
        header_B.setPreferredSize(new java.awt.Dimension(55, 24));
        header_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                header_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                header_BMouseExited(evt);
            }
        });
        header_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                header_BActionPerformed(evt);
            }
        });
        jToolBar1.add(header_B);

        subHeader_B.setBackground(new java.awt.Color(243, 194, 0));
        subHeader_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        subHeader_B.setForeground(new java.awt.Color(255, 255, 255));
        subHeader_B.setText("Sub Header");
        subHeader_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        subHeader_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        subHeader_B.setFocusPainted(false);
        subHeader_B.setFocusable(false);
        subHeader_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        subHeader_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        subHeader_B.setMaximumSize(new java.awt.Dimension(250, 28));
        subHeader_B.setPreferredSize(new java.awt.Dimension(55, 28));
        subHeader_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                subHeader_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subHeader_BMouseExited(evt);
            }
        });
        subHeader_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subHeader_BActionPerformed(evt);
            }
        });
        jToolBar1.add(subHeader_B);

        paragraph_B.setBackground(new java.awt.Color(45, 162, 74));
        paragraph_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paragraph_B.setForeground(new java.awt.Color(255, 255, 255));
        paragraph_B.setText("paragraph");
        paragraph_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        paragraph_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        paragraph_B.setFocusPainted(false);
        paragraph_B.setFocusable(false);
        paragraph_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        paragraph_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        paragraph_B.setMaximumSize(new java.awt.Dimension(250, 28));
        paragraph_B.setMinimumSize(new java.awt.Dimension(143, 28));
        paragraph_B.setPreferredSize(new java.awt.Dimension(55, 24));
        paragraph_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                paragraph_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                paragraph_BMouseExited(evt);
            }
        });
        paragraph_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraph_BActionPerformed(evt);
            }
        });
        jToolBar1.add(paragraph_B);

        addLink_B.setBackground(new java.awt.Color(102, 46, 145));
        addLink_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addLink_B.setForeground(new java.awt.Color(255, 255, 255));
        addLink_B.setText("Add Link");
        addLink_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        addLink_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addLink_B.setFocusPainted(false);
        addLink_B.setFocusable(false);
        addLink_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addLink_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        addLink_B.setMaximumSize(new java.awt.Dimension(250, 28));
        addLink_B.setMinimumSize(new java.awt.Dimension(143, 28));
        addLink_B.setPreferredSize(new java.awt.Dimension(55, 24));
        addLink_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addLink_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addLink_BMouseExited(evt);
            }
        });
        addLink_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLink_BActionPerformed(evt);
            }
        });
        jToolBar1.add(addLink_B);

        addVideo_B.setBackground(new java.awt.Color(102, 46, 145));
        addVideo_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addVideo_B.setForeground(new java.awt.Color(255, 255, 255));
        addVideo_B.setText("Add Video");
        addVideo_B.setToolTipText("<html>  <body>   <h4>Add a youtube video</h4>  </body> </html>");
        addVideo_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        addVideo_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addVideo_B.setFocusPainted(false);
        addVideo_B.setFocusable(false);
        addVideo_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addVideo_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        addVideo_B.setMaximumSize(new java.awt.Dimension(250, 28));
        addVideo_B.setMinimumSize(new java.awt.Dimension(143, 28));
        addVideo_B.setPreferredSize(new java.awt.Dimension(55, 24));
        addVideo_B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addVideo_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addVideo_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addVideo_BMouseExited(evt);
            }
        });
        addVideo_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVideo_BActionPerformed(evt);
            }
        });
        jToolBar1.add(addVideo_B);

        remove_B.setBackground(new java.awt.Color(255, 255, 255));
        remove_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        remove_B.setText("Remove");
        remove_B.setToolTipText("<html>  <body>   <h4>Remove a tag, Select the tag then remove</h4>  </body> </html>");
        remove_B.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(229, 229, 229), 2, true));
        remove_B.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        remove_B.setFocusPainted(false);
        remove_B.setFocusable(false);
        remove_B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        remove_B.setMargin(new java.awt.Insets(2, 30, 2, 30));
        remove_B.setMaximumSize(new java.awt.Dimension(250, 28));
        remove_B.setMinimumSize(new java.awt.Dimension(143, 28));
        remove_B.setPreferredSize(new java.awt.Dimension(55, 24));
        remove_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                remove_BMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                remove_BMouseExited(evt);
            }
        });
        remove_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_BActionPerformed(evt);
            }
        });
        jToolBar1.add(remove_B);

        saved_L.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saved_L.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_save_all_16px_1.png"))); // NOI18N
        saved_L.setText("Saved");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saved_L)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize_L)
                .addGap(4, 4, 4)
                .addComponent(textSize)
                .addContainerGap(96, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSize_L)
                            .addComponent(textSize)
                            .addComponent(saved_L))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        );

        jMenuBar.setBackground(new java.awt.Color(207, 207, 207));
        jMenuBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        jMenuBar.setForeground(new java.awt.Color(204, 0, 0));
        jMenuBar.setBorderPainted(false);
        jMenuBar.setPreferredSize(new java.awt.Dimension(56, 35));

        file_button_menu_bar.setBackground(new java.awt.Color(243, 243, 243));
        file_button_menu_bar.setText("File");
        file_button_menu_bar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        file_button_menu_bar.setMargin(new java.awt.Insets(0, 5, 0, 5));
        file_button_menu_bar.setOpaque(true);
        file_button_menu_bar.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                file_button_menu_barMenuSelected(evt);
            }
        });
        file_button_menu_bar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file_button_menu_barMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                file_button_menu_barMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                file_button_menu_barMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                file_button_menu_barMousePressed(evt);
            }
        });

        new_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        new_B.setText("New");
        new_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(new_B);

        open_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        open_B.setText("Open");
        open_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(open_B);

        save_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        save_B.setText("Save");
        save_B.setToolTipText("<html>  <body>   <h5>Save text file return and edit it <br> NOTE \"Save text will not build your article\"</h5>  </body> </html>");
        save_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(save_B);

        saveAs_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAs_B.setText("Save as");
        saveAs_B.setToolTipText("<html>  <body>   <h5>Save as text file return and edit it <br> NOTE \"Save as text will not build your article\"</h5>  </body> </html>");
        saveAs_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAs_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(saveAs_B);
        file_button_menu_bar.add(jSeparator1);

        build_B.setBackground(new java.awt.Color(153, 51, 0));
        build_B.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        build_B.setForeground(new java.awt.Color(255, 255, 255));
        build_B.setText("Build");
        build_B.setOpaque(true);
        build_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                build_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(build_B);
        file_button_menu_bar.add(jSeparator7);

        exit_B.setText("Exit");
        exit_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_BActionPerformed(evt);
            }
        });
        file_button_menu_bar.add(exit_B);

        jMenuBar.add(file_button_menu_bar);

        edit_button_menu_bar.setBackground(new java.awt.Color(243, 243, 243));
        edit_button_menu_bar.setText("Edit");
        edit_button_menu_bar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        edit_button_menu_bar.setMargin(new java.awt.Insets(0, 5, 0, 5));
        edit_button_menu_bar.setOpaque(true);

        undo_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undo_B.setText("Undo");
        undo_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undo_BActionPerformed(evt);
            }
        });
        edit_button_menu_bar.add(undo_B);
        edit_button_menu_bar.add(jSeparator2);

        cut_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cut_B.setText("Cut");
        cut_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cut_BActionPerformed(evt);
            }
        });
        edit_button_menu_bar.add(cut_B);

        copy_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copy_B.setText("Copy");
        copy_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copy_BActionPerformed(evt);
            }
        });
        edit_button_menu_bar.add(copy_B);

        paste_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        paste_B.setText("Paste");
        paste_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paste_BActionPerformed(evt);
            }
        });
        edit_button_menu_bar.add(paste_B);
        edit_button_menu_bar.add(jSeparator9);

        selectAll_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        selectAll_B.setText("Select all");
        selectAll_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAll_BActionPerformed(evt);
            }
        });
        edit_button_menu_bar.add(selectAll_B);

        jMenuBar.add(edit_button_menu_bar);

        text_button_menu_bar.setBackground(new java.awt.Color(243, 243, 243));
        text_button_menu_bar.setText("Text");
        text_button_menu_bar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        text_button_menu_bar.setMargin(new java.awt.Insets(0, 5, 0, 5));
        text_button_menu_bar.setOpaque(true);

        timeDate_B.setText("Time/Date");
        timeDate_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeDate_BActionPerformed(evt);
            }
        });
        text_button_menu_bar.add(timeDate_B);
        text_button_menu_bar.add(jSeparator3);

        RTL_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        RTL_B.setText("Text Direction RTL");
        RTL_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RTL_BActionPerformed(evt);
            }
        });
        text_button_menu_bar.add(RTL_B);

        LTR_B.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        LTR_B.setText("Text Direction LTR");
        LTR_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LTR_BActionPerformed(evt);
            }
        });
        text_button_menu_bar.add(LTR_B);

        jMenuBar.add(text_button_menu_bar);

        code_button_menu_bar.setBackground(new java.awt.Color(243, 243, 243));
        code_button_menu_bar.setText("Edit Code");
        code_button_menu_bar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        code_button_menu_bar.setMargin(new java.awt.Insets(0, 5, 0, 5));
        code_button_menu_bar.setOpaque(true);

        beforeCode_B.setText("Before ( Code ) *");
        beforeCode_B.setToolTipText("<html>  <body>   <h5>Required</h5>  </body> </html>");
        beforeCode_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beforeCode_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(beforeCode_B);
        code_button_menu_bar.add(jSeparator8);

        h1Menu_B.setText("<h1>   tag properties or style ( Code )");
        h1Menu_B.setToolTipText("<html>  <body>   <h5>Optional</h5>  </body> </html>");
        h1Menu_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1Menu_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(h1Menu_B);

        h2Menu_B.setText("<h2>   tag properties or style ( Code )");
        h2Menu_B.setToolTipText("<html>  <body>   <h5>Optional</h5>  </body> </html>");
        h2Menu_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2Menu_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(h2Menu_B);

        pMenu_B.setText("<p>     tag properties or style ( Code )");
        pMenu_B.setToolTipText("<html>  <body>   <h5>Optional</h5>  </body> </html>");
        pMenu_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMenu_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(pMenu_B);

        linkMenu_B.setText("<link> tag properties or style ( Code )");
        linkMenu_B.setToolTipText("<html>  <body>   <h5>Optional</h5>  </body> </html>");
        linkMenu_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linkMenu_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(linkMenu_B);
        code_button_menu_bar.add(jSeparator4);

        afterCode_B.setText("After ( Code ) *");
        afterCode_B.setToolTipText("<html>  <body>   <h5>Required</h5>  </body> </html>");
        afterCode_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afterCode_BActionPerformed(evt);
            }
        });
        code_button_menu_bar.add(afterCode_B);

        jMenuBar.add(code_button_menu_bar);

        code_button_menu_bar2.setBackground(new java.awt.Color(243, 243, 243));
        code_button_menu_bar2.setText("Info");
        code_button_menu_bar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        code_button_menu_bar2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        code_button_menu_bar2.setMargin(new java.awt.Insets(0, 5, 0, 5));
        code_button_menu_bar2.setOpaque(true);
        code_button_menu_bar2.setRolloverEnabled(false);
        code_button_menu_bar2.add(jSeparator6);

        jMenuItem6.setText("Help");
        code_button_menu_bar2.add(jMenuItem6);
        code_button_menu_bar2.add(jSeparator5);

        jMenuItem5.setText("About");
        code_button_menu_bar2.add(jMenuItem5);

        jMenuBar.add(code_button_menu_bar2);

        setJMenuBar(jMenuBar);
        jMenuBar.getAccessibleContext().setAccessibleParent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("WebPage Article Text Editor Tool or (WATE) tool, makes the writer's life better");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void new_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_BActionPerformed

        Home h = new Home();
        h.saved_L.setVisible(false);
        h.setVisible(true);

    }//GEN-LAST:event_new_BActionPerformed

    private void beforeCode_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeCode_BActionPerformed

        beforeCode = JOptionPane.showInputDialog(this, "Insert the code before the article code  <!-- Enter all the HTML code before the first h1 tag -->");

        File f = new File("data//runCode//beforeCode.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(beforeCode);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving before code is Done");

    }//GEN-LAST:event_beforeCode_BActionPerformed

    private void file_button_menu_barMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file_button_menu_barMouseEntered

    }//GEN-LAST:event_file_button_menu_barMouseEntered

    private void file_button_menu_barMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_file_button_menu_barMenuSelected

    }//GEN-LAST:event_file_button_menu_barMenuSelected

    private void RTL_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RTL_BActionPerformed

        jTextPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jTextPaneOriantation = true;

    }//GEN-LAST:event_RTL_BActionPerformed

    private void LTR_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LTR_BActionPerformed
        jTextPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jTextPaneOriantation = false;

    }//GEN-LAST:event_LTR_BActionPerformed

    private void jTextPaneMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jTextPaneMouseWheelMoved

        if (evt.getWheelRotation() < 0 && evt.isControlDown()) {// up is negative
            mouseWeel += 2;
            if (mouseWeel <= 300) {
                Font f = new Font("Segoe ui", Font.PLAIN, mouseWeel);
                jTextPane.setFont(f);

                textSize.setText(Integer.toString(mouseWeel));

            } else if (mouseWeel > 300) {

                mouseWeel -= 2;
            }
        } else {

            if (evt.getWheelRotation() > 0 && evt.isControlDown()) {// down in positive
                mouseWeel -= 2;
                if (mouseWeel >= 5) {
                    Font f = new Font("Segoe ui", Font.PLAIN, mouseWeel);
                    jTextPane.setFont(f);

                    textSize.setText(Integer.toString(mouseWeel));

                } else if (mouseWeel < 5) {

                    mouseWeel += 2;
                }

            }
        }


    }//GEN-LAST:event_jTextPaneMouseWheelMoved

    private void file_button_menu_barMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file_button_menu_barMouseExited

    }//GEN-LAST:event_file_button_menu_barMouseExited

    private void file_button_menu_barMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file_button_menu_barMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_file_button_menu_barMouseClicked

    private void file_button_menu_barMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file_button_menu_barMousePressed

    }//GEN-LAST:event_file_button_menu_barMousePressed

    private void paragraph_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraph_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        greenPainter = new DefaultHighlighter.DefaultHighlightPainter(myGreen); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Start checking if the there is already a symbols in the text to abstain from puting tag// Mohamed edit the symbols in the .contains() method when you copy it to the other buttons
        ///////////////////////////////////////////////////////////////////////////////////////////
        cursorT = jTextPane.getSelectedText();

        charCheckStateTitle = cursorT.contains(title_symb);
        charCheckStateHeader = cursorT.contains(h1_symb);
        charCheckStateSubHeader = cursorT.contains(h2_symb);
        charCheckStateParag = cursorT.contains(paragraph_symb);

        if (charCheckStateTitle == true || charCheckStateHeader == true || charCheckStateSubHeader == true || charCheckStateParag == true) {
            JOptionPane.showMessageDialog(this, "You can't add a tag to a text that already contains a tag");
        } else if (charCheckStateHeader == false || charCheckStateSubHeader == false || charCheckStateParag == false || charCheckStateLink == false) {

            /////////////////////////////////////////////////////////////////////////////////////////
            //End checking if the there is already a symbols in the text to abstain from puting tag//
            /////////////////////////////////////////////////////////////////////////////////////////
            //Start checking the last character to know if ther is an empty char' ' after the text
            if (jTextPane.getSelectedText() != null) {
                cursorT = jTextPane.getSelectedText();

                selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

                if (selectedCharCheck == ' ') {
                    emptyCursorState = true;
                } else if (selectedCharCheck != ' ') {
                    emptyCursorState = false;
                }

                //End checking the last character to know if ther is an empty char' ' after the text
                if (emptyCursorState == true) {
                    JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");

                } else {

                    //Start checking the first character to know if ther is an empty char' ' befor the text
                    selectedCharCheck = cursorT.charAt(0);

                    if (selectedCharCheck == ' ') {
                        emptyCursorState = true;

                    } else if (selectedCharCheck != ' ') {
                        emptyCursorState = false;
                    }

                    //End checking the first character to know if ther is an empty char' ' befor the text
                    if (emptyCursorState == true) {
                        JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                    } else if (emptyCursorState == false) {

                        jTextPane.replaceSelection(paragraph_symb + cursorT + paragraph_symbEnd); // replacing the selected text with the same text plus the symbol

                        try {

                            highlight.addHighlight(start, end + 7, greenPainter); // making highlight to the text (from begining index, to end index, with this color) note "the +6 after the end variable is to add 6 characters to the highlighter"

                        } catch (BadLocationException ex) {
                            JOptionPane.showMessageDialog(this, "Please Contact the developer and Give hime this Error " + ex);
                        }

                    }

                }

            }
        }

    }//GEN-LAST:event_paragraph_BActionPerformed

    private void title_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_title_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        redPainter = new DefaultHighlighter.DefaultHighlightPainter(myRed); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        //Start check if all the jText pane contains a title_symb the user will not add tag
        boolean CheckTitleContainsAll = false;

        CheckTitleContainsAll = jTextPane.getText().contains(title_symb);

        if (CheckTitleContainsAll == true) {
            JOptionPane.showMessageDialog(this, "You cannot add more than one Title tag per article");
        } else if (CheckTitleContainsAll == false) {
            //Start check if all the jText pane contains a title_symb the user will not add tag

            ///////////////////////////////////////////////////////////////////////////////////////////
            //Start checking if the there is already a symbols in the text to abstain from puting tag// Mohamed edit the symbols in the .contains() method when you copy it to the other buttons
            ///////////////////////////////////////////////////////////////////////////////////////////
            cursorT = jTextPane.getSelectedText();

            charCheckStateTitle = cursorT.contains(title_symb);
            charCheckStateHeader = cursorT.contains(h1_symb);
            charCheckStateSubHeader = cursorT.contains(h2_symb);
            charCheckStateParag = cursorT.contains(paragraph_symb);
            charCheckStateLink = cursorT.contains(link_symb);

            if (charCheckStateTitle == true || charCheckStateHeader == true || charCheckStateSubHeader == true || charCheckStateParag == true || charCheckStateLink == true) {
                JOptionPane.showMessageDialog(this, "You can't add a tag to a text that already contains a tag");
            } else if (charCheckStateTitle == false || charCheckStateHeader == false || charCheckStateSubHeader == false || charCheckStateParag == false || charCheckStateLink == false) {

                /////////////////////////////////////////////////////////////////////////////////////////
                //End checking if the there is already a symbols in the text to abstain from puting tag//
                /////////////////////////////////////////////////////////////////////////////////////////
                //Start checking the last character to know if ther is an empty char' ' after the text
                if (jTextPane.getSelectedText() != null) {
                    cursorT = jTextPane.getSelectedText();

                    selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

                    if (selectedCharCheck == ' ') {
                        emptyCursorState = true;
                    } else if (selectedCharCheck != ' ') {
                        emptyCursorState = false;
                    }

                    //End checking the last character to know if ther is an empty char' ' after the text
                    if (emptyCursorState == true) {
                        JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");

                    } else {

                        //Start checking the first character to know if ther is an empty char' ' befor the text
                        selectedCharCheck = cursorT.charAt(0);

                        if (selectedCharCheck == ' ') {
                            emptyCursorState = true;

                        } else if (selectedCharCheck != ' ') {
                            emptyCursorState = false;
                        }

                        //End checking the first character to know if ther is an empty char' ' befor the text
                        if (emptyCursorState == true) {
                            JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                        } else if (emptyCursorState == false) {

                            jTextPane.replaceSelection(title_symb + cursorT + title_symbEnd); // replacing the selected text with the same text plus the symbol

                            try {

                                highlight.addHighlight(start, end + 7, redPainter); // making highlight to the text (from begining index, to end index, with this color) note "the +6 after the end variable is to add 6 characters to the highlighter"

                            } catch (BadLocationException ex) {
                                JOptionPane.showMessageDialog(null, "Please Contact the developer and Give hime this Error " + ex);
                            }

                        }

                    }

                }
            }
        }
    }//GEN-LAST:event_title_BActionPerformed

    private void header_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_header_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        orangePainter = new DefaultHighlighter.DefaultHighlightPainter(myOrange); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        //Start check if all the jText pane contains a title_symb the user will not add tag
        boolean CheckHeaderContainsAll = false;

        CheckHeaderContainsAll = jTextPane.getText().contains(h1_symb);

        if (CheckHeaderContainsAll == true) {
            JOptionPane.showMessageDialog(this, "You cannot add more than one Header tag per article");
        } else if (CheckHeaderContainsAll == false) {
            //Start check if all the jText pane contains a title_symb the user will not add tag

            ///////////////////////////////////////////////////////////////////////////////////////////
            //Start checking if the there is already a symbols in the text to abstain from puting tag// Mohamed edit the symbols in the .contains() method when you copy it to the other buttons
            ///////////////////////////////////////////////////////////////////////////////////////////
            cursorT = jTextPane.getSelectedText();

            charCheckStateTitle = cursorT.contains(title_symb);
            charCheckStateHeader = cursorT.contains(h1_symb);
            charCheckStateSubHeader = cursorT.contains(h2_symb);
            charCheckStateParag = cursorT.contains(paragraph_symb);
            charCheckStateLink = cursorT.contains(link_symb);

            if (charCheckStateTitle == true || charCheckStateHeader == true || charCheckStateSubHeader == true || charCheckStateParag == true || charCheckStateLink == true) {
                JOptionPane.showMessageDialog(this, "You can't add a tag to a text that already contains a tag");
            } else if (charCheckStateTitle == false || charCheckStateHeader == false || charCheckStateSubHeader == false || charCheckStateParag == false || charCheckStateLink == false) {

                /////////////////////////////////////////////////////////////////////////////////////////
                //End checking if the there is already a symbols in the text to abstain from puting tag//
                /////////////////////////////////////////////////////////////////////////////////////////
                //Start checking the last character to know if ther is an empty char' ' after the text
                if (jTextPane.getSelectedText() != null) {
                    cursorT = jTextPane.getSelectedText();

                    selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

                    if (selectedCharCheck == ' ') {
                        emptyCursorState = true;
                    } else if (selectedCharCheck != ' ') {
                        emptyCursorState = false;
                    }

                    //End checking the last character to know if ther is an empty char' ' after the text
                    if (emptyCursorState == true) {
                        JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");

                    } else {

                        //Start checking the first character to know if ther is an empty char' ' befor the text
                        selectedCharCheck = cursorT.charAt(0);

                        if (selectedCharCheck == ' ') {
                            emptyCursorState = true;

                        } else if (selectedCharCheck != ' ') {
                            emptyCursorState = false;
                        }

                        //End checking the first character to know if ther is an empty char' ' befor the text
                        if (emptyCursorState == true) {
                            JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                        } else if (emptyCursorState == false) {

                            jTextPane.replaceSelection(h1_symb + cursorT + h1_symbEnd); // replacing the selected text with the same text plus the symbol

                            try {

                                highlight.addHighlight(start, end + 7, orangePainter); // making highlight to the text (from begining index, to end index, with this color) note "the +6 after the end variable is to add 6 characters to the highlighter"

                            } catch (BadLocationException ex) {
                                JOptionPane.showMessageDialog(null, "Please Contact the developer and Give hime this Error " + ex);
                            }

                        }

                    }

                }
            }
        }
    }//GEN-LAST:event_header_BActionPerformed

    private void addLink_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLink_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        purplePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Start checking if the there is already a symbols in the text to abstain from puting tag// Mohamed edit the symbols in the .contains() method when you copy it to the other buttons
        ///////////////////////////////////////////////////////////////////////////////////////////
        cursorT = jTextPane.getSelectedText();

        charCheckStateTitle = cursorT.contains(title_symb);
        charCheckStateHeader = cursorT.contains(h1_symb);
        charCheckStateSubHeader = cursorT.contains(h2_symb);
        charCheckStateParag = cursorT.contains(paragraph_symb);
        charCheckStateLink = cursorT.contains(link_symb);

        if (charCheckStateTitle == true || charCheckStateHeader == true || charCheckStateSubHeader == true || charCheckStateParag == true || charCheckStateLink == true) {
            JOptionPane.showMessageDialog(this, "You can't add a tag to a text that already contains a tag");
        } else if (charCheckStateTitle == false || charCheckStateHeader == false || charCheckStateSubHeader == false || charCheckStateParag == false || charCheckStateLink == false) {

            /////////////////////////////////////////////////////////////////////////////////////////
            //End checking if the there is already a symbols in the text to abstain from puting tag//
            /////////////////////////////////////////////////////////////////////////////////////////
            //Start checking the last character to know if ther is an empty char' ' after the text
            if (jTextPane.getSelectedText() != null) {
                cursorT = jTextPane.getSelectedText();

                selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

                if (selectedCharCheck == ' ') {
                    emptyCursorState = true;
                } else if (selectedCharCheck != ' ') {
                    emptyCursorState = false;
                }

                //End checking the last character to know if ther is an empty char' ' after the text
                if (emptyCursorState == true) {
                    JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");

                } else {

                    //Start checking the first character to know if ther is an empty char' ' befor the text
                    selectedCharCheck = cursorT.charAt(0);

                    if (selectedCharCheck == ' ') {
                        emptyCursorState = true;

                    } else if (selectedCharCheck != ' ') {
                        emptyCursorState = false;
                    }

                    //End checking the first character to know if ther is an empty char' ' befor the text
                    if (emptyCursorState == true) {
                        JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                    } else if (emptyCursorState == false) {

                        String url;
                        url = JOptionPane.showInputDialog(this, "Insert Link URL", "https://");

                        if (url.equals(null)) {

                        } else {

                            try {

                                jTextPane.replaceSelection(link_symb + '`' + url + "``" + cursorT + link_symbEnd); // replacing the selected text with the same text plus the symbol

                                highlight.addHighlight(start, end + 10 + url.length(), purplePainter); // making highlight to the text (from begining index, to end index, with this color) note "the +6 after the end variable is to add 6 characters to the highlighter"

                            } catch (BadLocationException ex) {
                                JOptionPane.showMessageDialog(this, "Please Contact the developer and Give hime this Error " + ex);
                            }

                        }

                    }

                }

            }
        }

    }//GEN-LAST:event_addLink_BActionPerformed

    private void title_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title_BMouseEntered
        title_B.setBackground(Color.white);
        title_B.setForeground(Color.red);
    }//GEN-LAST:event_title_BMouseEntered

    private void addLink_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLink_BMouseEntered
        addLink_B.setBackground(Color.white);
        addLink_B.setForeground(myPurple);
    }//GEN-LAST:event_addLink_BMouseEntered

    private void remove_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remove_BMouseEntered
        remove_B.setBackground(Color.white);
        remove_B.setForeground(Color.black);
    }//GEN-LAST:event_remove_BMouseEntered

    private void remove_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        WhitePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.black); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        cursorT = jTextPane.getSelectedText();

        //Start checking the last character to know if there is an empty char' ' after the text
        if (jTextPane.getSelectedText() != null) {
            cursorT = jTextPane.getSelectedText();

            selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

            if (selectedCharCheck == ' ') {
                emptyCursorState = true;
            } else if (selectedCharCheck != ' ') {
                emptyCursorState = false;
            }

            //End checking the last character to know if ther is an empty char' ' after the text
            if (emptyCursorState == true) {
                JOptionPane.showMessageDialog(this, "You can't remove tags with empty characters, Please select your text without EMPTY characters from the right and left of your text");

            } else {

                //Start checking the first character to know if there is an empty char' ' befor the text
                selectedCharCheck = cursorT.charAt(0);

                if (selectedCharCheck == ' ') {
                    emptyCursorState = true;

                } else if (selectedCharCheck != ' ') {
                    emptyCursorState = false;
                }

                //End checking the first character to know if there is an empty char' ' befor the text
                if (emptyCursorState == true) {
                    JOptionPane.showMessageDialog(this, "You can't remove tags with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                } else if (emptyCursorState == false) {
                    stateOne = false;

                    {
                        //start a loop to check if the first 3 characters of the selected text is a true symbol to be removed
                        for (int i = 0; i < 3; i++) {

                            selectedCharCheck = cursorT.charAt(i);

                            if (i == 0 && selectedCharCheck == '#') {
                                stateOne = true;

                            } else if (i == 1 && selectedCharCheck == '_' || selectedCharCheck == '&' || selectedCharCheck == '-' || selectedCharCheck == '/' || selectedCharCheck == '|' || selectedCharCheck == '=') {
                                stateOne = true;

                            } else if (i == 2 && selectedCharCheck == '#') {
                                stateOne = true;

                            } else {
                                stateOne = false;
                                break;
                            }

                        }
                        //End a loop to check if the first 3 characters of the selected text is a true symbol to be removed

                        //start a loop to check if the last 4 characters of the selected text is a true symbol to be removed -- edited i added the / to know the end of the tag
                        int m = cursorT.length();

                        for (int i = cursorT.length(); i > cursorT.length() - 4; i--) {

                            //System.out.println(cursorT.charAt(i - 1));
                            selectedCharCheck = cursorT.charAt(i - 1);

                            if (i == cursorT.length() && selectedCharCheck == '/') {
                                stateTwo = true;

                            } else if (i == cursorT.length() - 1 && selectedCharCheck == '#') {
                                stateTwo = true;

                            } else if (i == cursorT.length() - 2 && selectedCharCheck == '_' || selectedCharCheck == '&' || selectedCharCheck == '-' || selectedCharCheck == '/' || selectedCharCheck == '=') {
                                stateTwo = true;

                            } else if (i == cursorT.length() - 3 && selectedCharCheck == '#') {
                                stateTwo = true;

                            } else {
                                stateTwo = false;
                                break;
                            }
                        }

                        //End a loop to check if the last 4 characters of the selected text is a true symbol to be removed
                        if (stateOne == true && stateTwo == true) {

                            //Start get the last 4 characters of the selected text to see if the beginOfText == endOfText
                            beginOfText = cursorT.substring(0, 3);
                            endOfText = cursorT.substring(cursorT.length() - 4, cursorT.length());

                            if (!beginOfText.equals(endOfText.substring(0, endOfText.length() - 1))) {
                                JOptionPane.showMessageDialog(this, "You can't remove multiple tags together, Please reselect a single correct tag");
                            } else {
                                //End get the last 4 characters of the selected text to see if the beginOfText == endOfText

                                String t = cursorT.substring(3, cursorT.length() - 4); //triming the forst 3 characters and the last 4 characters
                                jTextPane.replaceSelection(t); // replacing the selected text with the same text without the symbol
                            }
                            //highlight.removeHighlight(highlight.getHighlights());
                        } else if (stateOne == false || stateTwo == false) {
                            JOptionPane.showMessageDialog(this, "You are selecting a wrong tag, Please reselect another correct tag");

                        }

                    }
                }

            }

        }


    }//GEN-LAST:event_remove_BActionPerformed

    private void undo_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undo_BActionPerformed
        UndoManager u = new UndoManager();

    }//GEN-LAST:event_undo_BActionPerformed

    private void copy_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copy_BActionPerformed
        jTextPane.copy();
    }//GEN-LAST:event_copy_BActionPerformed

    private void cut_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cut_BActionPerformed
        jTextPane.cut();

    }//GEN-LAST:event_cut_BActionPerformed

    private void paste_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paste_BActionPerformed
        jTextPane.paste();
    }//GEN-LAST:event_paste_BActionPerformed

    private void subHeader_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subHeader_BActionPerformed

        Highlighter highlight = jTextPane.getHighlighter(); //creating object from Highlighter class
        yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(myYellow); //making color

        start = jTextPane.getSelectionStart(); //the start selected text index to the highlighter
        end = jTextPane.getSelectionEnd(); //the end selected text index to the highlighter

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Start checking if the there is already a symbols in the text to abstain from puting tag// Mohamed edit the symbols in the .contains() method when you copy it to the other buttons
        ///////////////////////////////////////////////////////////////////////////////////////////
        cursorT = jTextPane.getSelectedText();

        charCheckStateTitle = cursorT.contains(title_symb);
        charCheckStateHeader = cursorT.contains(h1_symb);
        charCheckStateSubHeader = cursorT.contains(h2_symb);
        charCheckStateParag = cursorT.contains(paragraph_symb);
        charCheckStateLink = cursorT.contains(link_symb);

        if (charCheckStateTitle == true || charCheckStateHeader == true || charCheckStateSubHeader == true || charCheckStateParag == true || charCheckStateLink == true) {
            JOptionPane.showMessageDialog(this, "You can't add a tag to a text that already contains a tag");
        } else if (charCheckStateTitle == false || charCheckStateHeader == false || charCheckStateSubHeader == false || charCheckStateParag == false || charCheckStateLink == false) {

            /////////////////////////////////////////////////////////////////////////////////////////
            //End checking if the there is already a symbols in the text to abstain from puting tag//
            /////////////////////////////////////////////////////////////////////////////////////////
            //Start checking the last character to know if ther is an empty char' ' after the text
            if (jTextPane.getSelectedText() != null) {
                cursorT = jTextPane.getSelectedText();

                selectedCharCheck = cursorT.charAt(cursorT.length() - 1);

                if (selectedCharCheck == ' ') {
                    emptyCursorState = true;
                } else if (selectedCharCheck != ' ') {
                    emptyCursorState = false;
                }

                //End checking the last character to know if ther is an empty char' ' after the text
                if (emptyCursorState == true) {
                    JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");

                } else {

                    //Start checking the first character to know if ther is an empty char' ' befor the text
                    selectedCharCheck = cursorT.charAt(0);

                    if (selectedCharCheck == ' ') {
                        emptyCursorState = true;

                    } else if (selectedCharCheck != ' ') {
                        emptyCursorState = false;
                    }

                    //End checking the first character to know if ther is an empty char' ' befor the text
                    if (emptyCursorState == true) {
                        JOptionPane.showMessageDialog(this, "You can't add title tag with empty characters, Please select your text without EMPTY characters from the right and left of your text");
                    } else if (emptyCursorState == false) {

                        jTextPane.replaceSelection(h2_symb + cursorT + h2_symbEnd); // replacing the selected text with the same text plus the symbol

                        try {

                            highlight.addHighlight(start, end + 7, yellowPainter); // making highlight to the text (from begining index, to end index, with this color) note "the +6 after the end variable is to add 6 characters to the highlighter"

                        } catch (BadLocationException ex) {
                            JOptionPane.showMessageDialog(this, "Please Contact the developer and Give hime this Error " + ex);
                        }

                    }

                }

            }
        }

    }//GEN-LAST:event_subHeader_BActionPerformed

    private void open_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_BActionPerformed

        JFileChooser jfc = new JFileChooser(); //making a JFileChooser object to show a file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt File", "TXT"); //making a file name extension filter to filter the files extentions from the user
        jfc.setFileFilter(filter); // filter the files in the file chooser

        int x = jfc.showSaveDialog(Home.this); // showing file chooser

        if (x == jfc.APPROVE_OPTION) {
            path = jfc.getSelectedFile().toString();
            if (!path.substring(path.length() - 4, path.length()).equals(".txt")) { //check if the extention of the file is not txt

                System.out.println(path.substring(path.length() - 3, path.length()));
                JOptionPane.showMessageDialog(Home.this, "You can't open a non txt file");

            } else if (path.substring(path.length() - 4, path.length()).equals(".txt")) {

                if (jTextPane.getText().isEmpty()) {

///////////reading from file and showing the text in the jtextPane///////////////////////////////////////////////////////////////////////////////////////////////////////
                    File f = null; // file variable
                    FileReader fr = null; // file reader variable

                    try {
                        //the action when the jtextpane is empty
                        f = new File(path); // making object to file
                        fr = new FileReader(f); // making object to file reader

                        try {

                            while (fr.read() != -1) {
                                jTextPane.read(fr, f);
                            }

                        } catch (IOException ex) {

                            JOptionPane.showMessageDialog(Home.this, "Error while reading file, please contact the developer " + ex);

                        }

                    } catch (FileNotFoundException ex) {

                        JOptionPane.showMessageDialog(Home.this, "File not found Error please contact the developer " + ex);

                    } finally {
                        try {

                            fr.close();

                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(Home.this, "Error while closing the stream please contact the developer " + ex);
                        }
                    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                } else {
                    Home h = new Home();
                    h.setVisible(true);
                    h.saved_L.setVisible(false);

                    File f = null; // file variable
                    FileReader fr = null; // file reader variable

                    try {
                        //the action when the jtextpane is empty
                        f = new File(path); // making object to file
                        fr = new FileReader(f); // making object to file reader

                        try {

                            while (fr.read() != -1) {
                                jTextPane.read(fr, f);
                            }

                        } catch (IOException ex) {

                            JOptionPane.showMessageDialog(Home.this, "Error while reading file, please contact the developer " + ex);

                        }

                    } catch (FileNotFoundException ex) {

                        JOptionPane.showMessageDialog(Home.this, "File not found Error please contact the developer " + ex);

                    } finally {
                        try {

                            fr.close();

                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(Home.this, "Error while closing the stream please contact the developer " + ex);
                        }
                    }

                }
            }
        }
    }//GEN-LAST:event_open_BActionPerformed

    private void linkMenu_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linkMenu_BActionPerformed

        linkProp = JOptionPane.showInputDialog(this, "Insert the <link> tag style or property   <!-- Example style=\"color:red\" -->");

        File f = new File("data//runCode//linkProp.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(linkProp);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving <link> property is Done");

    }//GEN-LAST:event_linkMenu_BActionPerformed

    private void save_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_BActionPerformed

        if (!jTextPane.getText().isEmpty()) {
            if (savePath == null) {

                JFileChooser jfc = new JFileChooser(); //making a JFileChooser object to show a file chooser
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt File", "TXT"); //making a file name extension filter to filter the files extentions from the user
                jfc.setFileFilter(filter); // filter the files in the file chooser

                //making object from simple date format to get the timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                Date date = new Date();
                String dt = sdf.format(date).toString();

                jfc.setSelectedFile(new File("(WATE) " + dt + ".txt")); //putting a defult file nameto the filechooser

                int x = jfc.showSaveDialog(Home.this); // showing file chooser

                if (x == jfc.APPROVE_OPTION) {
                    savePath = jfc.getSelectedFile().toString();

                    String process = savePath.substring(savePath.length() - 4, savePath.length());
                    if (!process.equals(".txt")) {
                        savePath = savePath + ".txt";

                    } else if (savePath.substring(savePath.length() - 4, savePath.length()).equals(".txt")) {

                        if (jTextPane.getText().isEmpty()) {

                        } else if (!jTextPane.getText().isEmpty()) {

                            File f = new File(savePath);
                            FileWriter fw = null;

                            try {

                                fw = new FileWriter(f);

                                fw.write(jTextPane.getText());

                                Multithreading threading = new Multithreading(); //making object of Multithreading class to make savedLabel appeare when the user save a file and not freez the application so use multitheading
                                threading.start(); // method calling to start

                            } catch (IOException ex) {
                                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    fw.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                } else {

                }
            } else {

                File f = new File(savePath);
                FileWriter fw = null;

                try {

                    fw = new FileWriter(f);

                    fw.write(jTextPane.getText());

                    Multithreading threading = new Multithreading(); //making object of Multithreading class to make savedLabel appeare when the user save a file and not freez the application so use multitheading
                    threading.start(); // method calling to start

                } catch (IOException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

    }//GEN-LAST:event_save_BActionPerformed

    private void exit_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_BActionPerformed

    }//GEN-LAST:event_exit_BActionPerformed

    public class Multithreading extends Thread { // to make savedLabel appeare when the user save a file and not freez the application so use multitheading

        @Override
        public void run() {

            Home.this.saved_L.setVisible(true);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {

                JOptionPane.showMessageDialog(copy_B, "Error while trying to make saved label visable please contact the developer" + ex);

            } finally {

                Home.this.saved_L.setVisible(false);

            }

        }

    }


    private void afterCode_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afterCode_BActionPerformed

        afterCode = JOptionPane.showInputDialog(this, "Insert the code after the article code  <!-- Enter all the HTML code from the first footer tag till the end-->");

        File f = new File("data//runCode//afterCode.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(afterCode);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving after code is Done");

    }//GEN-LAST:event_afterCode_BActionPerformed

    private void pMenu_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMenu_BActionPerformed

        pProp = JOptionPane.showInputDialog(this, "Insert the <p> tag style or property   <!-- Example style=\"color:red\" -->");

        File f = new File("data//runCode//pProp.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(pProp);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving <p> property is Done");

    }//GEN-LAST:event_pMenu_BActionPerformed

    private void h2Menu_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h2Menu_BActionPerformed

        h2Prop = JOptionPane.showInputDialog(this, "Insert the <h2> tag style or property   <!-- Example style=\"color:red\" -->");

        File f = new File("data//runCode//h2Prop.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(h2Prop);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving <h2> property is Done");

    }//GEN-LAST:event_h2Menu_BActionPerformed

    private void h1Menu_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h1Menu_BActionPerformed

        h1Prop = JOptionPane.showInputDialog(this, "Insert the <h1> tag style or property   <!-- Example style=\"color:red\" -->");

        File f = new File("data//runCode//h1Prop.properties");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            fw.write(h1Prop);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Home.this, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(Home.this, "Saving <h1> property is Done");

    }//GEN-LAST:event_h1Menu_BActionPerformed

    private void selectAll_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAll_BActionPerformed
        jTextPane.selectAll();
    }//GEN-LAST:event_selectAll_BActionPerformed

    private void timeDate_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeDate_BActionPerformed

        if (jTextPaneOriantation == false) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
                Date date = new Date();
                String dt = sdf.format(date).toString();

                jTextPane.getDocument().insertString(jTextPane.getCaretPosition(), dt, null);

            } catch (BadLocationException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (jTextPaneOriantation == true) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mmaa dd/MM/yyyy");
                Date date = new Date();
                String dt = sdf.format(date).toString();

                jTextPane.getDocument().insertString(jTextPane.getCaretPosition(), dt, null);

            } catch (BadLocationException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_timeDate_BActionPerformed

    private void saveAs_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAs_BActionPerformed

        if (!jTextPane.getText().isEmpty()) {

            JFileChooser jfc = new JFileChooser(); //making a JFileChooser object to show a file chooser
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt File", "TXT"); //making a file name extension filter to filter the files extentions from the user
            jfc.setFileFilter(filter); // filter the files in the file chooser

            //making object from simple date format to get the timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            Date date = new Date();
            String dt = sdf.format(date).toString();

            jfc.setSelectedFile(new File("(WATE) " + dt + ".txt")); //putting a defult file nameto the filechooser

            int x = jfc.showSaveDialog(Home.this); // showing file chooser

            if (x == jfc.APPROVE_OPTION) {
                savePath = jfc.getSelectedFile().toString();

                String process = savePath.substring(savePath.length() - 4, savePath.length());
                if (!process.equals(".txt")) {
                    savePath = savePath + ".txt";

                } else {

                }
                if (savePath.substring(savePath.length() - 4, savePath.length()).equals(".txt")) {

                    if (jTextPane.getText().isEmpty()) {

                    } else if (!jTextPane.getText().isEmpty()) {

                        File f = new File(savePath);
                        FileWriter fw = null;

                        try {

                            fw = new FileWriter(f);

                            fw.write(jTextPane.getText());

                            Multithreading threading = new Multithreading(); //making object of Multithreading class to make savedLabel appeare when the user save a file and not freez the application so use multitheading
                            threading.start(); // method calling to start

                        } catch (IOException ex) {
                            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                fw.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        } else {

        }
    }//GEN-LAST:event_saveAs_BActionPerformed

    private void title_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title_BMouseExited
        title_B.setBackground(myRed);
        title_B.setForeground(Color.white);
    }//GEN-LAST:event_title_BMouseExited

    private void header_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_header_BMouseEntered
        header_B.setBackground(Color.white);
        header_B.setForeground(myOrange);
    }//GEN-LAST:event_header_BMouseEntered

    private void header_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_header_BMouseExited
        header_B.setBackground(myOrange);
        header_B.setForeground(Color.white);
    }//GEN-LAST:event_header_BMouseExited

    private void subHeader_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subHeader_BMouseEntered
        subHeader_B.setBackground(Color.white);
        subHeader_B.setForeground(myYellow);
    }//GEN-LAST:event_subHeader_BMouseEntered

    private void subHeader_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subHeader_BMouseExited
        subHeader_B.setBackground(myYellow);
        subHeader_B.setForeground(Color.white);
    }//GEN-LAST:event_subHeader_BMouseExited

    private void paragraph_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paragraph_BMouseEntered
        paragraph_B.setBackground(Color.white);
        paragraph_B.setForeground(myGreen);
    }//GEN-LAST:event_paragraph_BMouseEntered

    private void paragraph_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paragraph_BMouseExited
        paragraph_B.setBackground(myGreen);
        paragraph_B.setForeground(Color.white);
    }//GEN-LAST:event_paragraph_BMouseExited

    private void addLink_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLink_BMouseExited
        addLink_B.setBackground(myPurple);
        addLink_B.setForeground(Color.white);
    }//GEN-LAST:event_addLink_BMouseExited

    private void remove_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remove_BMouseExited
        remove_B.setBackground(Color.white);
        remove_B.setForeground(Color.BLACK);
    }//GEN-LAST:event_remove_BMouseExited

    private void build_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_build_BActionPerformed
        // this method converts all symbols in the article to html tags

        if (jTextPane.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Home.this, "You can't build an empty article");
        } else {
            //start displaying Jfilechooser to the user to choose the path of the excuted file
            JFileChooser jfc = new JFileChooser(); //making a JFileChooser object to show a file chooser
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Web File (.html)", "html"); //making a file name extension filter to filter the files extentions from the user
            jfc.setFileFilter(filter); // filter the files in the file chooser

            //making object from simple date format to get the timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dt = sdf.format(date).toString();

            String titleName = "fileName";

            Pattern pattern = Pattern.compile("#_#(.*?)#_#/", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(jTextPane.getText());
            while (matcher.find()) {
                titleName = matcher.group(1);
            }

            jfc.setSelectedFile(new File(dt + "_" + titleName + ".html")); //putting a defult file nameto the filechooser

            int x = jfc.showSaveDialog(Home.this); // showing file chooser
            //end displaying Jfilechooser to the user to choose the path of the excuted file

            savePath = jfc.getSelectedFile().toString();

            if (x == jfc.APPROVE_OPTION) {

                String process = savePath.substring(savePath.length() - 5, savePath.length());
                if (!process.equals(".html")) {
                    savePath = savePath + ".html";
                    System.out.println(savePath);
                } else {

                }

                jTextPane.getHighlighter().removeAllHighlights();// removing all highlights in the JtextPane because of the replaceall method down is crushing of the highlights

                File fReadFrom = new File("data//log//buildProcessOne.properties"); //read from
                File fWriteTo = new File(savePath); // write to

                FileWriter fwOne = null; // write to the first file

                Reader fr = null; // read from the first file
                BufferedReader br = null; // read from the first file

                FileWriter fwTwo = null; // write to the second file

                //Start files declariation for the properteis tags code
                FileReader fPathBeforeCode = null;
                FileReader fPathTitleProp = null;
                FileReader fPathH1Prop = null;
                FileReader fPathH2Prop = null;
                FileReader fPathPProp = null;
                FileReader fPathLinkProp = null;
                FileReader fPathAfterCode = null;

                try {
                    fPathBeforeCode = new FileReader("data//runCode//beforeCode.properties"); //file path for beforeCode.properties
                    fPathTitleProp = new FileReader("data//runCode//titleProp.properties"); //file path for titleProp.properties
                    fPathH1Prop = new FileReader("data//runCode//h1Prop.properties"); //file path for h1Prop.properties
                    fPathH2Prop = new FileReader("data//runCode//h2Prop.properties"); //file path for h2Prop.properties
                    fPathPProp = new FileReader("data//runCode//pProp.properties"); //file path for pProp.properties
                    fPathLinkProp = new FileReader("data//runCode//linkProp.properties"); //file path for linkProp.properties
                    fPathAfterCode = new FileReader("data//runCode//afterCode.properties"); //file path for afterCode.properties
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error while building the article, Please contact the developer " + e);

                }
                //End files declariation for the properteis tags code

                //Start write declaration for the properteis tags code
                BufferedReader fwReaderBeforeCode = null;
                BufferedReader fwReaderTitleProp = null;
                BufferedReader fwReaderH1Prop = null;
                BufferedReader fwReaderH2Prop = null;
                BufferedReader fwReaderPProp = null;
                BufferedReader fwReaderLinkProp = null;
                BufferedReader fwReaderAfterCode = null;
                //End write declaration for the properteis tags code

                //start write the the jTextPane in the first file
                try {

                    fwOne = new FileWriter(fReadFrom);

                    fwOne.write(jTextPane.getText());

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error while building the article, Please contact the developer " + ex);
                } finally {
                    try {
                        fwOne.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error while building the article, Please contact the developer " + ex);
                    }
                }
                //End write the the jTextPane in the first file

                //Start reading from the properites files that stored the code that the user entered to impelement it to the final file
                try {

                    //Start write declaration for the properteis tags code
                    fwReaderBeforeCode = new BufferedReader(fPathBeforeCode);
                    fwReaderTitleProp = new BufferedReader(fPathTitleProp);
                    fwReaderH1Prop = new BufferedReader(fPathH1Prop);
                    fwReaderH2Prop = new BufferedReader(fPathH2Prop);
                    fwReaderPProp = new BufferedReader(fPathPProp);
                    fwReaderLinkProp = new BufferedReader(fPathLinkProp);
                    fwReaderAfterCode = new BufferedReader(fPathAfterCode);
                    //End write declaration for the properteis tags code

                    //Start initializing variables with code from the user
                    String line;
                    //Start first initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    line = fwReaderBeforeCode.readLine();
                    beforeCode = "";

                    while (line != null) {
                        beforeCode += line;
                        line = fwReaderBeforeCode.readLine();
                    }
                    //End first initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //Start h1 initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered
                    line = fwReaderH1Prop.readLine();
                    h1Prop = "";

                    while (line != null) {
                        h1Prop += line;
                        line = fwReaderH1Prop.readLine();
                    }
                    //End h1 initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //Start h2 initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered
                    line = fwReaderH2Prop.readLine();
                    h2Prop = "";

                    while (line != null) {
                        h2Prop += line;
                        line = fwReaderH2Prop.readLine();
                    }
                    //End h2 initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //Start p initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered
                    line = fwReaderPProp.readLine();
                    pProp = "";

                    while (line != null) {
                        pProp += line;
                        line = fwReaderPProp.readLine();
                    }
                    //End p initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //Start link initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered
                    line = fwReaderLinkProp.readLine();
                    linkProp = "";

                    while (line != null) {
                        linkProp += line;
                        line = fwReaderLinkProp.readLine();
                    }
                    //End link initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //Start last initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered
                    line = fwReaderAfterCode.readLine();
                    afterCode = "";

                    while (line != null) {
                        afterCode += line;
                        line = fwReaderAfterCode.readLine();
                    }
                    //End last initializing the variable beforeCode with the text that in the text file beforeCode.properties that the user has entered

                    //End initializing variables with code from the user
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error while reading the implemented code to the build, Please contact the developer " + ex);
                } finally {
                    try {
                        fwReaderBeforeCode.close();
                        fwReaderTitleProp.close();
                        fwReaderH1Prop.close();
                        fwReaderH2Prop.close();
                        fwReaderPProp.close();
                        fwReaderLinkProp.close();
                        fwReaderAfterCode.close();
                    } //End reading from the properites files that stored the code that the user entered to impelement it to the final file
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error while closing the reading stream, Please contact the developer " + ex);
                    }
                }

                {// start writing in the second file
                    try {
                        fr = new FileReader(fReadFrom);

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, "Error while building the article Cant find the building process file , Please contact the developer " + ex);
                    }

                    //Start final exceute code //writing the code in the final file
                    try {

                        fwTwo = new FileWriter(fWriteTo);
                        br = new BufferedReader(fr);

                        fwTwo.write(beforeCode + "\n\n");
                        while (br.ready()) { // reading from the first file then write to the second file and replace all symbols link #-# to html tags

                            fwTwo.append(br.readLine().replaceAll(title_symbEnd, "</title>").replaceAll(title_symb, "<title>")
                                    .replaceAll(h1_symbEnd, "</h1>").replaceAll(h1_symb, "<h1 " + h1Prop + ">")
                                    .replaceAll(h2_symbEnd, "</h2>").replaceAll(h2_symb, "<h2 " + h2Prop + ">")
                                    .replaceAll(paragraph_symbEnd, "</p>").replaceAll(paragraph_symb, "<p " + pProp + ">")
                                    .replaceAll(link_symbEnd, "</a>").replaceAll(link_symb, "<a " + "href=")
                                    .replaceAll("``", "\" " + linkProp + ">").replaceAll("`", "\"")
                                    .replaceAll(video_symbEnd, "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>")
                                    .replaceAll(video_symb, "<iframe width=\"853\" height=\"480\" src=\"") + "\n"); // reading from the first file then write to the second file and replace all symbol #-#/ to </h1>

                        }
                        fwTwo.append("\n\n" + afterCode);

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error while building the article, Please contact the developer " + ex);
                    } finally {
                        try {
                            fwOne.close();
                            fwTwo.close();
                            br.close();
                            fr.close();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error while building the article, Please contact the developer " + ex);
                        }

                    }
                    //End final exceute code //writing the code in the final file

                }// end writing in the second file

                jTextPane.setText("");
                JOptionPane.showMessageDialog(Home.this, "Your Article has been built.");

            } else {

            }
        }

    }//GEN-LAST:event_build_BActionPerformed

    private void addVideo_BMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addVideo_BMouseEntered
        addVideo_B.setBackground(Color.white);
        addVideo_B.setForeground(myPurple);
    }//GEN-LAST:event_addVideo_BMouseEntered

    private void addVideo_BMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addVideo_BMouseExited
        addVideo_B.setBackground(myPurple);
        addVideo_B.setForeground(Color.white);
    }//GEN-LAST:event_addVideo_BMouseExited

    private void addVideo_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVideo_BActionPerformed
        ///////////////////////////////////////////////////////////////////////////////////////////
        //Start checking if the there is already a symbols in the text to abstain from puting tag
        ///////////////////////////////////////////////////////////////////////////////////////////

        cursorT = jTextPane.getSelectedText();

        if (cursorT == null) {
            String url;
            url = JOptionPane.showInputDialog(this, "Insert a youtube video URL", "https://");
            url = url.replace("watch?v=", "embed/"); //replacing watch?v= with embed because of the video does not work if the url not embed
            
            if (url.contains("&")) { // if the url contains '&' will crop it with all the characters after it
                int i = url.indexOf("&");
                url = url.substring(0, i); // not croping all the characters after '&' also to make the video work
            }
            
            if (url.equals(null)) {

            } else {
                jTextPane.replaceSelection(video_symb + url + video_symbEnd); // replacing the selected text with the same text plus the symbol
            }
        } else {
            JOptionPane.showMessageDialog(this, "You can't add a Youtube video to a selected text");

        }


    }//GEN-LAST:event_addVideo_BActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Home h = new Home();
                h.setTitle("WebPage Article Text Editor (WATE) tool");
                h.setVisible(true);
                saved_L.setVisible(false);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem LTR_B;
    private javax.swing.JMenuItem RTL_B;
    private javax.swing.JButton addLink_B;
    private javax.swing.JButton addVideo_B;
    private javax.swing.JMenuItem afterCode_B;
    private javax.swing.JMenuItem beforeCode_B;
    private javax.swing.JMenuItem build_B;
    private javax.swing.JMenu code_button_menu_bar;
    private javax.swing.JMenu code_button_menu_bar2;
    private javax.swing.JMenuItem copy_B;
    private javax.swing.JMenuItem cut_B;
    private javax.swing.JMenu edit_button_menu_bar;
    private javax.swing.JMenuItem exit_B;
    private javax.swing.JMenu file_button_menu_bar;
    private javax.swing.JMenuItem h1Menu_B;
    private javax.swing.JMenuItem h2Menu_B;
    private javax.swing.JButton header_B;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private static javax.swing.JTextPane jTextPane;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem linkMenu_B;
    private javax.swing.JMenuItem new_B;
    private javax.swing.JMenuItem open_B;
    private javax.swing.JMenuItem pMenu_B;
    private javax.swing.JButton paragraph_B;
    private javax.swing.JMenuItem paste_B;
    private javax.swing.JButton remove_B;
    private javax.swing.JMenuItem saveAs_B;
    private javax.swing.JMenuItem save_B;
    public static javax.swing.JLabel saved_L;
    private javax.swing.JMenuItem selectAll_B;
    private javax.swing.JButton subHeader_B;
    private javax.swing.JLabel textSize;
    private javax.swing.JMenu text_button_menu_bar;
    private javax.swing.JMenuItem timeDate_B;
    private javax.swing.JButton title_B;
    private javax.swing.JLabel txtSize_L;
    private javax.swing.JMenuItem undo_B;
    // End of variables declaration//GEN-END:variables
}
