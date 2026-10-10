import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaGUIFrame extends JFrame
{
    private JRadioButton thinRB;
    private JRadioButton regularRB;
    private JRadioButton deepDishRB;
    private ButtonGroup crustGroup;

    private JComboBox<String> sizeCombo;

    private JCheckBox pepperoniCB;
    private JCheckBox mushroomsCB;
    private JCheckBox olivesCB;
    private JCheckBox extraCheeseCB;
    private JCheckBox jalapenosCB;
    private JCheckBox pineappleCB;
    private JCheckBox anchoviesCB;
    private JCheckBox zombieMeatCB;

    private JTextArea receiptTA;

    private final double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};
    private final double TOPPING_PRICE = 1.00;
    private final double TAX_RATE = 0.07;
    private final DecimalFormat money = new DecimalFormat("$0.00");

    public PizzaGUIFrame()
    {
        super("Pizza Order Form");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 650);
        setLocationRelativeTo(null);

        JPanel mainPnl = new JPanel(new BorderLayout(10, 10));
        mainPnl.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel optionsPnl = new JPanel(new GridLayout(1, 3, 10, 10));

        optionsPnl.add(createCrustPanel());
        optionsPnl.add(createSizePanel());
        optionsPnl.add(createToppingsPanel());

        mainPnl.add(optionsPnl, BorderLayout.NORTH);
        mainPnl.add(createReceiptPanel(), BorderLayout.CENTER);
        mainPnl.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPnl);

        setVisible(true);
    }


    private JPanel createCrustPanel()
    {
        JPanel crustPnl = new JPanel();
        crustPnl.setLayout(new BoxLayout(crustPnl, BoxLayout.Y_AXIS));
        crustPnl.setBorder(new TitledBorder("Crust"));

        thinRB = new JRadioButton("Thin");
        regularRB = new JRadioButton("Regular");
        deepDishRB = new JRadioButton("Deep-dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinRB);
        crustGroup.add(regularRB);
        crustGroup.add(deepDishRB);

        crustPnl.add(thinRB);
        crustPnl.add(regularRB);
        crustPnl.add(deepDishRB);

        return crustPnl;
    }

    private JPanel createSizePanel()
    {
        JPanel sizePnl = new JPanel();
        sizePnl.setLayout(new BoxLayout(sizePnl, BoxLayout.Y_AXIS));
        sizePnl.setBorder(new TitledBorder("Pizza Size"));

        String[] sizes = {"Small", "Medium", "Large", "Super"};

        sizeCombo = new JComboBox<>(sizes);
        sizeCombo.setSelectedIndex(-1);

        JLabel pricesLbl = new JLabel(
                "<html>Small: $8.00<br>"
                        + "Medium: $12.00<br>"
                        + "Large: $16.00<br>"
                        + "Super: $20.00</html>"
        );

        sizePnl.add(new JLabel("Choose a size:"));
        sizePnl.add(sizeCombo);
        sizePnl.add(Box.createVerticalStrut(12));
        sizePnl.add(pricesLbl);

        return sizePnl;
    }

    private JPanel createToppingsPanel()
    {
        JPanel toppingsPnl = new JPanel(new GridLayout(4, 2));
        toppingsPnl.setBorder(new TitledBorder("Toppings ($1.00 each)"));

        pepperoniCB = new JCheckBox("Pepperoni");
        mushroomsCB = new JCheckBox("Mushrooms");
        olivesCB = new JCheckBox("Olives");
        extraCheeseCB = new JCheckBox("Extra cheese");
        jalapenosCB = new JCheckBox("Jalapenos");
        pineappleCB = new JCheckBox("Pineapple");
        anchoviesCB = new JCheckBox("Anchovies");
        zombieMeatCB = new JCheckBox("Zombie meat");

        toppingsPnl.add(pepperoniCB);
        toppingsPnl.add(mushroomsCB);
        toppingsPnl.add(olivesCB);
        toppingsPnl.add(extraCheeseCB);
        toppingsPnl.add(jalapenosCB);
        toppingsPnl.add(pineappleCB);
        toppingsPnl.add(anchoviesCB);
        toppingsPnl.add(zombieMeatCB);

        return toppingsPnl;
    }

    private JPanel createReceiptPanel()
    {
        JPanel receiptPnl = new JPanel(new BorderLayout());
        receiptPnl.setBorder(new TitledBorder("Order Receipt"));

        receiptTA = new JTextArea(12, 50);
        receiptTA.setEditable(false);
        receiptTA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        receiptTA.setText("Your receipt will appear here after you click Order.");

        JScrollPane receiptScroll = new JScrollPane(receiptTA);

        receiptPnl.add(receiptScroll, BorderLayout.CENTER);

        return receiptPnl;
    }

    private JPanel createButtonPanel()
    {
        JPanel buttonPnl = new JPanel(new FlowLayout());

        JButton orderBtn = new JButton("Order");
        JButton clearBtn = new JButton("Clear");
        JButton quitBtn = new JButton("Quit");

        orderBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                createReceipt();
            }
        });

        clearBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                clearForm();
            }
        });

        quitBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                confirmQuit();
            }
        });

        buttonPnl.add(orderBtn);
        buttonPnl.add(clearBtn);
        buttonPnl.add(quitBtn);

        return buttonPnl;
    }

    private void createReceipt()
    {
        if (crustGroup.getSelection() == null)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Please choose a crust.",
                    "Missing Crust",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (sizeCombo.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Please choose a pizza size.",
                    "Missing Size",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int toppingCount = 0;

        if (pepperoniCB.isSelected()) toppingCount++;
        if (mushroomsCB.isSelected()) toppingCount++;
        if (olivesCB.isSelected()) toppingCount++;
        if (extraCheeseCB.isSelected()) toppingCount++;
        if (jalapenosCB.isSelected()) toppingCount++;
        if (pineappleCB.isSelected()) toppingCount++;
        if (anchoviesCB.isSelected()) toppingCount++;
        if (zombieMeatCB.isSelected()) toppingCount++;

        if (toppingCount == 0)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Please choose at least one topping.",
                    "Missing Topping",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String crust = "";

        if (thinRB.isSelected()) crust = "Thin";
        else if (regularRB.isSelected()) crust = "Regular";
        else if (deepDishRB.isSelected()) crust = "Deep-dish";

        String size = (String) sizeCombo.getSelectedItem();

        double basePrice = SIZE_PRICES[sizeCombo.getSelectedIndex()];
        double subtotal = basePrice + toppingCount * TOPPING_PRICE;
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        DecimalFormat money = new DecimalFormat("$0.00");

        StringBuilder receipt = new StringBuilder();

        receipt.append("===============================================\n");
        receipt.append(String.format("%-32s %10s%n",
                "Type of Crust & Size", "Price"));
        receipt.append("-----------------------------------------------\n");
        receipt.append(String.format("%-32s %10s%n",
                crust + " crust, " + size, money.format(basePrice)));

        receipt.append("\n");
        receipt.append(String.format("%-32s %10s%n",
                "Ingredient", "Price"));
        receipt.append("-----------------------------------------------\n");

        if (pepperoniCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Pepperoni", money.format(1.00)));
        if (mushroomsCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Mushrooms", money.format(1.00)));
        if (olivesCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Olives", money.format(1.00)));
        if (extraCheeseCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Extra cheese", money.format(1.00)));
        if (jalapenosCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Jalapenos", money.format(1.00)));
        if (pineappleCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Pineapple", money.format(1.00)));
        if (anchoviesCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Anchovies", money.format(1.00)));
        if (zombieMeatCB.isSelected()) receipt.append(String.format("%-32s %10s%n", "Zombie meat", money.format(1.00)));

        receipt.append("-----------------------------------------------\n");
        receipt.append(String.format("%-32s %10s%n",
                "Sub-total:", money.format(subtotal)));
        receipt.append(String.format("%-32s %10s%n",
                "Tax (7%):", money.format(tax)));
        receipt.append("-----------------------------------------------\n");
        receipt.append(String.format("%-32s %10s%n",
                "Total:", money.format(total)));
        receipt.append("===============================================\n");

        receiptTA.setText(receipt.toString());
        receiptTA.setCaretPosition(0);
    }


    private void clearForm()
    {
        crustGroup.clearSelection();

        sizeCombo.setSelectedIndex(-1);

        pepperoniCB.setSelected(false);
        mushroomsCB.setSelected(false);
        olivesCB.setSelected(false);
        extraCheeseCB.setSelected(false);
        jalapenosCB.setSelected(false);
        pineappleCB.setSelected(false);
        anchoviesCB.setSelected(false);
        zombieMeatCB.setSelected(false);

        receiptTA.setText("Your receipt will appear here after you click Order.");
    }


    private void confirmQuit()
    {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (answer == JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

}