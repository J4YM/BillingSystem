import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
public class MainFrame extends JFrame {
    JTextField item;
    JTextField qty;
    JLabel intro, totalLabel, itemlistfield;

    Supplier<Stream<String>> availstream = () -> Stream.of("BfastWRice", "NormWRice", "Bfast/NormWORice", 
                                                            "BWater/Soda", "RegJuice", "NormDessert");

    final private Font mainfont = new Font("Arial", Font.BOLD, 16);
    public void initialize() {
        
        //Main Forms
        JLabel itemlabel = new JLabel("Item Name");
        itemlabel.setFont(mainfont);

        item = new JTextField();
        item.setFont(mainfont);
        item.setEditable(false);

        JLabel qtylabel = new JLabel("Quantity");
        qtylabel.setFont(mainfont);

        qty = new JTextField();
        qty.setFont(mainfont);

        intro = new JLabel();
        intro.setFont(mainfont);

        totalLabel = new JLabel();
        totalLabel.setFont(mainfont);
        totalLabel.setText("SUBTOTAL: P");

        itemlistfield = new JLabel();
        itemlistfield.setFont(mainfont);
        itemlistfield.setSize(20,50);
        itemlistfield.setText("");

        //Item List Panel
        JPanel itemlistpanel = new JPanel();
        itemlistpanel.setLayout(new GridLayout(5, 1));
        itemlistpanel.setBackground(Color.WHITE);
        itemlistpanel.setSize(20,50);
        itemlistpanel.add(itemlistfield);
        

        //Form Panel
        JPanel formP = new JPanel();
        formP.setLayout(new GridLayout(4,2,5,5));
        formP.setOpaque(false);
        formP.add(itemlabel);
        formP.add(item);
        formP.add(qtylabel);
        formP.add(qty);
        formP.add(itemlistpanel);

        //Menu Items
        JButton b1 = new JButton();
        b1.setText("Breakfast Menu w/Rice");
        b1.setBackground(Color.ORANGE);
        b1.setSize(getMinimumSize());
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("BfastWRice");
            }
            
        });


        JButton b2 = new JButton();
        b2.setText("Normal Menu w/Rice");
        b2.setBackground(Color.BLUE);
        b2.setSize(getMinimumSize());
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("NormWRice");
            }
            
        });


        JButton b3 = new JButton();
        b3.setText("Bfast/Normal Menu w/o Rice");
        b3.setBackground(Color.magenta);
        b3.setSize(getMinimumSize());
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("Bfast/NormWORice");
            }
            
        });

        JButton b4 = new JButton();
        b4.setText("Bottled Water / Soda");
        b4.setBackground(Color.cyan);
        b4.setSize(getMinimumSize());
        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("BWater/Soda");
            }
            
        });

        JButton b5 = new JButton();
        b5.setText("Regular Juice (Set Quantity 2 for Large)");
        b5.setBackground(Color.YELLOW);
        b5.setSize(getMinimumSize());
        b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("RegJuice");
            }
            
        });

        JButton b6 = new JButton();
        b6.setText("Normal Dessert");
        b6.setBackground(Color.pink);
        b6.setSize(getMinimumSize());
        b6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                item.setText("NormDessert");
            }
            
        });

        //Item Buttons Panel
        JPanel itemB = new JPanel();
        itemB.setSize(50, 50);
        itemB.setLayout(new GridLayout(3,3,5,5));
        itemB.add(b1);
        itemB.add(b2);
        itemB.add(b3);
        itemB.add(b4);
        itemB.add(b5);
        itemB.add(b6);
        itemB.setOpaque(false);

        //Add Item Button
        JButton btnLog = new JButton("Add item");
        btnLog.setFont(mainfont);

        btnLog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String iteminit = item.getText();
                String qtyinit = qty.getText();

                checkitem(iteminit, qtyinit);
            }
        });

        //Clear Items in Fields
        JButton btnclear = new JButton("Clear");
        btnLog.setFont(mainfont);

        btnclear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                intro.setText("");
                qty.setText("");
                item.setText("");
                itemlistfield.setText("");
                
            }
        });

        //Prints Receipt
        JButton btnprint = new JButton("Payable");
        btnprint.setFont(mainfont);
        btnprint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pass();
            }
            
        });
        
        //Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3,5,5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLog);
        buttonPanel.add(btnclear);
        buttonPanel.add(btnprint);

        //Main Panel
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.setBackground(new Color(128,200,255));
        jp.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        jp.add(formP, BorderLayout.NORTH);
        jp.add(intro, BorderLayout.CENTER);
        jp.add(itemB, BorderLayout.AFTER_LINE_ENDS);
        jp.add(buttonPanel, BorderLayout.SOUTH);
        jp.add(totalLabel, BorderLayout.BEFORE_LINE_BEGINS);

        add(jp);

        //Set Style
        setTitle("Store Billing App");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(900,900));
        setLocation(380, 150);        
    }

    //Checks if item exists in Menu (Double Check In case of Value Mismatch)
    public void checkitem(String a, String b) {
        String items = item.getText();
        String quan = qty.getText();

        int quanti = Integer.parseInt(quan);

        boolean answer = availstream.get().anyMatch(s -> s.equalsIgnoreCase(items)); //Method for Checking Value in Stream Array
        if (answer == true) {
            
            switch (items.toLowerCase()) {
                case "bfastwrice":
                    additem(75, quanti);
                    break;
                case "normwrice":
                    additem(85, quanti);
                    break;
                case "bfast/normworice":
                    additem(60, quanti);
                    break;
                case "bwater/soda":
                    additem(15, quanti);
                    break;
                case "regjuice":
                    additem(25, quanti);
                    break;
                case "normdessert":
                    additem(35, quanti);
                    break;
                case "":
                    noitem();
                    break;
                default:
                    noitem();
                    break;
            }
        } else {
            noitem();
        }
    }

    //Main Method for Adding Item in Subtotal
    public void additem(int x, int y) {
        String initlabel = intro.getText();
        String items = item.getText();
        String quan = qty.getText();
        int z = x;
        int q = y;

        int priceinit = z * q;


        if (intro.getText().equals("") && item.getText().isEmpty() && qty.getText().isEmpty()) {
            noitem();
        } else if (intro.getText().equals("")) {
            String priceString = Integer.toString(priceinit);

            intro.setText(priceString);
            item.setText("");
            qty.setText("");
        } else if (!(item.getText().isEmpty() && qty.getText().isEmpty())){
            int initlabelconvert = Integer.parseInt(initlabel) + priceinit;

            String price = Integer.toString(initlabelconvert);

            String finalabel = price;
            
            intro.setText(finalabel);
            item.setText("");
            qty.setText("");
            
        }
        //Add to Item List Panel 
        listitem(items, quan);
    }
    public void listitem(String a, String b) {
        String inititem = a;
        String quantiitem = b;

        if (itemlistfield.getText().equals("")) {
            itemlistfield.setText("Items: "+ a + "("+ b +")");
        } else {
            itemlistfield.setText(itemlistfield.getText() + ", " + inititem + "(" + quantiitem + ")");
        }
    }

    //Pop Up when No Item Found
    public void noitem() {
        // create a dialog Box
        JDialog d = new JDialog();
 
        // create a label
        JLabel l = new JLabel("Item Not Found!");
        JButton b = new JButton("Item Not Found");
        b.setFont(mainfont);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);//Close Popup after Button Click
            }
            
        });
        d.add(l);
        d.add(b);

        // setsize of dialog
        d.setSize(250, 100);
        d.setLocation(230,100);

        // set visibility of dialog
        d.setVisible(true);
    }

    //Clear Items and Transaction Complete
    public void clearall() {
        //Generates Unique Order ID
        Random rand = new Random();
        int orderid = rand.nextInt(12345);
        JFrame f;  
        f = new JFrame();
        JOptionPane.showConfirmDialog(f, "Transaction Complete!", "Order ID:" + orderid, JOptionPane.DEFAULT_OPTION);
        wreceipt(orderid, itemlistfield.getText(), intro.getText());

        intro.setText("");
    }

    //Write to File (Receipt)
    public void wreceipt(int x, String a, String b) {
        //Local Variables 
        int orderidrec = x;
        String totalprice = b;
        String itemlist = a;

        //Date and Time Format
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy KK:mm a");
        try {

            //Writer to Text File
            FileWriter receipt = new FileWriter("orders.txt", true);
            receipt.write(System.getProperty("line.separator"));
            receipt.write(System.getProperty("line.separator"));

            //Order ID and DateTime
            receipt.write("Order ID:"+Integer.toString(orderidrec) +", DATE AND TIME: "+ dateTime.format(formatter));
            receipt.write(System.getProperty("line.separator"));

            //List Items
            receipt.write(itemlist);
            receipt.write(System.getProperty("line.separator"));
            receipt.write("TOTAL: P"+totalprice);

            //Close Writer
            receipt.close();
        } 
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Failed to print receipt, please retry");
        }    

    }

    public void pass() {
        JFrame f;  
                f = new JFrame();
                String amount = JOptionPane.showInputDialog(f, "Enter Amount");

                //Initialize values
                int totamount = Integer.parseInt(intro.getText());
                int payamount = Integer.parseInt(amount);
                int finalamount = payamount - totamount;

                //Verify if Value Exists
                if (amount.equals("")) {
                    
                } else if (totamount > payamount) {
                    JOptionPane.showMessageDialog(f, "Not Enough Payable Cash");//Verify if Entered amount is Enough
                } else if (!(amount.equals(""))) {

                    //Verify Admin Password for Transaction
                    JPasswordField pwd = new JPasswordField(10);
                    int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Admin Password",JOptionPane.OK_CANCEL_OPTION);
                    if(action < 0) {
                        return;
                    } else {
                        String passString = new String(pwd.getPassword());

                        if (passString.equals("admin123")) {
                            JOptionPane.showMessageDialog(f, "Change: " + finalamount);
                            clearall();
                        } else {
                            JOptionPane.showConfirmDialog(f, "Initiate New Transaction", "Incorrect Password", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                            pass();
                        }
                    }
                    
                }
    }


    public static void main(String[] args) {
        //Initialize and Open Main JFrame
        MainFrame frame = new MainFrame();

        frame.initialize();
    }
}
