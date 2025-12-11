package store;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PetStoreFrontEnd extends JFrame {

    private StockManager manager;
    
    // UI Components
    private JTable animalTable, productTable, cartTable;
    private DefaultTableModel animalModel, productModel, cartModel;

    private TableRowSorter<DefaultTableModel> animalSorter;
    private TableRowSorter<DefaultTableModel> productSorter;
    
    // Checkout Labels
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    
    // constants
    private double discountRate = 0.0; 
    private final double TAX_RATE = 0.0825; // 8.25% tax

    public PetStoreFrontEnd() {
        super("Pet Store Management System");
        setSize(1500, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        manager = new StockManager();
        initializeDummyData(); 

        // create tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Animals", createAnimalPanel());
        tabbedPane.addTab("Products", createProductPanel());
        tabbedPane.addTab("Checkout", createCheckoutPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    // load starter data
    private void initializeDummyData() {
        // DOGS
        manager.addAnimal(new Dog("Buddy", 3, 150.00, 'M', "Golden Retriever", true));
        manager.addAnimal(new Dog("Max", 5, 120.00, 'M', "German Shepherd", true));
        manager.addAnimal(new Dog("Bella", 1, 200.00, 'F', "Poodle", false)); 

        // CATS 
        manager.addAnimal(new Cat("Luna", 2, 80.00, 'F', "Siamese"));
        manager.addAnimal(new Cat("Oliver", 4, 60.00, 'M', "Tabby"));

        // BIRDS
        manager.addAnimal(new Bird("Tweety", 1, 25.00, 'M', "Yellow", BirdSize.SMALL));
        manager.addAnimal(new Bird("Polly", 10, 300.00, 'F', "Green/Red", BirdSize.LARGE));
        
        // FISH
        manager.addAnimal(new Fish("Nemo", 1, 12.50, 'M', "Striped", FishType.OTHER));
        manager.addAnimal(new Fish("Goldie", 1, 5.00, 'F', "Solid", FishType.GOLDFISH));
        
        // REPTILES
        manager.addAnimal(new Reptile("Pascal", 2, 45.00, 'M', "Chameleon"));

        // PRODUCTS
        Product kibble = new Food("Premium Kibble", "Pedigree", 29.99, "Dry Food");
        manager.addProduct(kibble);
        manager.addProduct(kibble); 
        
        manager.addProduct(new Toy("Squeaky Bone", "Kong", 9.99, "Rubber"));
        manager.addProduct(new Cloth("Winter Vest", "PetCo", 15.99, "Polyester"));
        manager.addProduct(new Shelter("Large Dog Crate", "SecureHome", 89.99, "Wire Cage", 36.0, 48.0));
    }


  
    // TAB 1: ANIMALS

    private JPanel createAnimalPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search Animals (Name Only): "));
        searchPanel.add(searchField);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Type", "Name", "Age", "Gender", "Price", "Info"};
        animalModel = new DefaultTableModel(columnNames, 0);
        animalTable = new JTable(animalModel);

        animalSorter = new TableRowSorter<>(animalModel);
        animalTable.setRowSorter(animalSorter);

        animalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshAnimalTable();
        mainPanel.add(new JScrollPane(animalTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cartButton = new JButton("ADD SELECTED TO CART");
        cartButton.setBackground(new Color(200, 230, 255)); 
        JButton removeButton = new JButton("REMOVE SELECTED");
        removeButton.setBackground(new Color(255, 150, 150)); 
        
        cartPanel.add(cartButton);
        cartPanel.add(removeButton);
        southPanel.add(cartPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());
        
        JTextField nameField = new JTextField(7);
        JTextField ageField = new JTextField(3);
        JTextField priceField = new JTextField(5);
        String[] genders = {"M", "F"};
        JComboBox<String> genderBox = new JComboBox<>(genders);
        String[] types = {"Dog", "Cat", "Bird", "Fish", "Reptile"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        
        JLabel detailLabel = new JLabel("Breed:");
        JTextField detailField = new JTextField(8); 
        JLabel extraLabel = new JLabel("Type:");
        JComboBox<FishType> fishBox = new JComboBox<>(FishType.values());
        JComboBox<BirdSize> birdBox = new JComboBox<>(BirdSize.values());
        JCheckBox vaxCheck = new JCheckBox("Vaccinated?");
        JButton addButton = new JButton("Add Animal");

        inputPanel.add(new JLabel("Type:"));    inputPanel.add(typeBox);
        inputPanel.add(new JLabel("Name:"));    inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));     inputPanel.add(ageField);
        inputPanel.add(new JLabel("Sex:"));     inputPanel.add(genderBox);
        inputPanel.add(new JLabel("Price:"));   inputPanel.add(priceField);
        inputPanel.add(detailLabel); inputPanel.add(detailField);
        inputPanel.add(extraLabel);  
        inputPanel.add(fishBox); 
        inputPanel.add(birdBox);
        inputPanel.add(vaxCheck);
        inputPanel.add(addButton);
        
        southPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // VISIBILITY LOGIC (ANIMALS)
        Runnable updateVisibility = () -> {
            String selected = (String) typeBox.getSelectedItem();
            extraLabel.setVisible(false);
            fishBox.setVisible(false);
            birdBox.setVisible(false);
            vaxCheck.setVisible(false);
            
            if (selected.equals("Dog")) {
                detailLabel.setText("Breed:");
                vaxCheck.setVisible(true);
            } else if (selected.equals("Cat")) {
                detailLabel.setText("Breed:");
            } else if (selected.equals("Bird")) {
                detailLabel.setText("Color:");
                extraLabel.setText("Size:");
                extraLabel.setVisible(true);
                birdBox.setVisible(true);
            } else if (selected.equals("Fish")) {
                detailLabel.setText("Pattern:");
                extraLabel.setText("Type:");
                extraLabel.setVisible(true);
                fishBox.setVisible(true);
            } else if (selected.equals("Reptile")) {
                detailLabel.setText("Species:");
            }
            inputPanel.revalidate();
            inputPanel.repaint();
        };

        updateVisibility.run();
        typeBox.addActionListener(e -> updateVisibility.run());

        // LISTENERS 
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) animalSorter.setRowFilter(null);
                else animalSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
            }
        });

        cartButton.addActionListener(e -> {
            int viewRow = animalTable.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = animalTable.convertRowIndexToModel(viewRow);
                String name = (String) animalModel.getValueAt(modelRow, 1);
                manager.addToCartAnimal(name);
                refreshAnimalTable(); 
                refreshCartTable();   
                JOptionPane.showMessageDialog(mainPanel, "Added " + name + " to cart.");
            }
        });
        
        removeButton.addActionListener(e -> {
            int viewRow = animalTable.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = animalTable.convertRowIndexToModel(viewRow);
                String name = (String) animalModel.getValueAt(modelRow, 1);
                manager.removeAnimal(name); 
                refreshAnimalTable(); 
                JOptionPane.showMessageDialog(mainPanel, "Removed " + name + " from stock.");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Please select an animal.");
            }
        });

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double price = Double.parseDouble(priceField.getText());
                char gender = ((String) genderBox.getSelectedItem()).charAt(0);
                String type = (String) typeBox.getSelectedItem();
                String detail = detailField.getText();
                if(detail.isEmpty()) detail = "Unknown";
                
                Animal newAnimal = null;
                switch (type) {
                    case "Dog": 
                        boolean isVax = vaxCheck.isSelected();
                        newAnimal = new Dog(name, age, price, gender, detail, isVax); 
                        break;
                    case "Cat": newAnimal = new Cat(name, age, price, gender, detail); break;
                    case "Bird": 
                        BirdSize size = (BirdSize) birdBox.getSelectedItem();
                        newAnimal = new Bird(name, age, price, gender, detail, size); 
                        break;
                    case "Fish": 
                        FishType fType = (FishType) fishBox.getSelectedItem();
                        newAnimal = new Fish(name, age, price, gender, detail, fType); 
                        break;
                    case "Reptile": newAnimal = new Reptile(name, age, price, gender, detail); break;
                }
                
                if(newAnimal != null) { 
                    manager.addAnimal(newAnimal); 
                    refreshAnimalTable(); 
                    nameField.setText(""); ageField.setText(""); priceField.setText(""); 
                    detailField.setText(""); vaxCheck.setSelected(false);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid Input. Check Numbers.");
            }
        });

        return mainPanel;
    }

  
    // TAB 2: PRODUCTS

    private JPanel createProductPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search Products (Name Only): "));
        searchPanel.add(searchField);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Type", "Name", "Mfr", "Price", "Stock Qty", "Info"};
        productModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productModel);

        productSorter = new TableRowSorter<>(productModel);
        productTable.setRowSorter(productSorter);

        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshProductTable();
        mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cartButton = new JButton("ADD 1 ITEM TO CART");
        cartButton.setBackground(new Color(200, 230, 255));
        JButton removeButton = new JButton("REMOVE 1 QTY");
        removeButton.setBackground(new Color(255, 150, 150));
        
        cartPanel.add(cartButton);
        cartPanel.add(removeButton);
        southPanel.add(cartPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());
        
        JTextField nameField = new JTextField(10);
        JTextField mfrField = new JTextField(10);
        JTextField priceField = new JTextField(5);
        String[] types = {"Food", "Toy", "Cloth", "Shelter"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        
        // DYNAMIC FIELDS FOR PRODUCTS 
        JLabel detailLabel = new JLabel("Type:");
        JTextField detailField = new JTextField(8); // Used for Type or Material
        
        // Only for Shelter
        JLabel widthLabel = new JLabel("Width:");
        JTextField widthField = new JTextField(4);
        JLabel heightLabel = new JLabel("Height:");
        JTextField heightField = new JTextField(4);
        
        JButton addButton = new JButton("Add Product");

        inputPanel.add(new JLabel("Type:")); inputPanel.add(typeBox);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Mfr:"));  inputPanel.add(mfrField);
        inputPanel.add(new JLabel("Price:"));inputPanel.add(priceField);
        
        inputPanel.add(detailLabel); inputPanel.add(detailField);
        inputPanel.add(widthLabel);  inputPanel.add(widthField);
        inputPanel.add(heightLabel); inputPanel.add(heightField);
        
        inputPanel.add(addButton);
        southPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // VISIBILITY LOGIC (PRODUCTS) 
        Runnable updateVisibility = () -> {
            String selected = (String) typeBox.getSelectedItem();
            
            // Default: Hide Shelter dimensions
            widthLabel.setVisible(false);
            widthField.setVisible(false);
            heightLabel.setVisible(false);
            heightField.setVisible(false);
            
            if (selected.equals("Food")) {
                detailLabel.setText("Food Type:");
            } else if (selected.equals("Toy")) {
                detailLabel.setText("Material:");
            } else if (selected.equals("Cloth")) {
                detailLabel.setText("Material:");
            } else if (selected.equals("Shelter")) {
                detailLabel.setText("Type:");
                // Show dimensions for Shelter
                widthLabel.setVisible(true);
                widthField.setVisible(true);
                heightLabel.setVisible(true);
                heightField.setVisible(true);
            }
            inputPanel.revalidate();
            inputPanel.repaint();
        };
        
        updateVisibility.run();
        typeBox.addActionListener(e -> updateVisibility.run());

        // Listeners 
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) productSorter.setRowFilter(null);
                else productSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
            }
        });

        cartButton.addActionListener(e -> {
            int viewRow = productTable.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = productTable.convertRowIndexToModel(viewRow);
                String name = (String) productModel.getValueAt(modelRow, 1);
                manager.addToCartProduct(name);
                refreshProductTable(); 
                refreshCartTable();    
            }
        });
        
        removeButton.addActionListener(e -> {
            int viewRow = productTable.getSelectedRow();
            if (viewRow != -1) {
                int modelRow = productTable.convertRowIndexToModel(viewRow);
                String name = (String) productModel.getValueAt(modelRow, 1);
                manager.removeProduct(name); 
                refreshProductTable(); 
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Please select a product.");
            }
        });

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String mfr = mfrField.getText();
                double price = Double.parseDouble(priceField.getText());
                String type = (String) typeBox.getSelectedItem();
                String detail = detailField.getText();
                if(detail.isEmpty()) detail = "Generic";

                Product p = null;
                switch(type) {
                    case "Food": p = new Food(name, mfr, price, detail); break;
                    case "Toy": p = new Toy(name, mfr, price, detail); break;
                    case "Cloth": p = new Cloth(name, mfr, price, detail); break;
                    case "Shelter": 
                        double w = 0, h = 0;
                        try {
                             w = Double.parseDouble(widthField.getText());
                             h = Double.parseDouble(heightField.getText());
                        } catch(Exception x) {} // Default to 0 if empty
                        p = new Shelter(name, mfr, price, detail, w, h); 
                        break;
                }
                if(p!=null) { 
                    manager.addProduct(p); 
                    refreshProductTable();
                    nameField.setText(""); mfrField.setText(""); priceField.setText(""); 
                    detailField.setText(""); widthField.setText(""); heightField.setText("");
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid Price");
            }
        });

        return mainPanel;
    }


    // TAB 3: CHECKOUT

    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Cart Table
        String[] columnNames = {"Item Name", "Type/Mfr", "Quantity", "Price (Each)", "Subtotal"};
        cartModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartModel);
        panel.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new GridLayout(5, 1)); 
        
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subtotalLabel = new JLabel("Subtotal: $0.00");
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subPanel.add(subtotalLabel);
        
        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField discountField = new JTextField(5);
        JButton applyDiscountBtn = new JButton("Apply Discount %");
        discountPanel.add(new JLabel("Discount (%): "));
        discountPanel.add(discountField);
        discountPanel.add(applyDiscountBtn);
        
        JPanel taxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        taxLabel = new JLabel("Tax (8.25%): $0.00");
        taxLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taxPanel.add(taxLabel);
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("TOTAL: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalLabel.setForeground(new Color(0, 100, 0)); 
        totalPanel.add(totalLabel);
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton payButton = new JButton("FINALIZE TRANSACTION");
        payButton.setBackground(new Color(150, 255, 150)); 
        actionPanel.add(payButton);
        
        bottomPanel.add(subPanel);
        bottomPanel.add(discountPanel);
        bottomPanel.add(taxPanel);
        bottomPanel.add(totalPanel);
        bottomPanel.add(actionPanel);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        // listeners 
        applyDiscountBtn.addActionListener(e -> {
            try {
                double val = Double.parseDouble(discountField.getText());
                if(val < 0 || val > 100) {
                    JOptionPane.showMessageDialog(panel, "Please enter 0-100");
                } else {
                    discountRate = val / 100.0;
                    refreshCartTable(); 
                    JOptionPane.showMessageDialog(panel, "Discount Applied: " + val + "%");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Number");
            }
        });
        
        payButton.addActionListener(e -> {
            if(manager.isCartEmpty()) {
                JOptionPane.showMessageDialog(panel, "Cart is empty!");
                return;
            }
            JOptionPane.showMessageDialog(panel, "Transaction Complete!\n" + totalLabel.getText());
            manager.clearCart();
            discountRate = 0.0;
            discountField.setText("");
            refreshCartTable();
        });
        
        return panel;
    }

 
    //    REFRESH / DATA HELPERS
   

    private String getSpecificDetails(Animal a) {
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            return "Breed: " + d.getBreed() + (d.getIsVaccinated() ? " (Vaccinated)" : ""); 
        } else if (a instanceof Cat) {
            Cat c = (Cat) a;
            return "Breed: " + c.getBreed();
        } else if (a instanceof Bird) {
            Bird b = (Bird) a;
            return "Color: " + b.getColor() + ", Size: " + b.getBirdSize();
        } else if (a instanceof Fish) {
            Fish f = (Fish) a;
            return "Type: " + f.getFishType() + ", Pattern: " + f.getPattern();
        } else if (a instanceof Reptile) {
            Reptile r = (Reptile) a;
            return "Species: " + r.getSpecies();
        }
        return "";
    }

    private String getProductDetails(Product p) {
        if (p instanceof Food) return "Type: " + ((Food)p).getType();
        if (p instanceof Toy) return "Mat: " + ((Toy)p).getMaterial();
        if (p instanceof Cloth) return "Type: " + ((Cloth)p).getType();
        if (p instanceof Shelter) return "Dims: " + ((Shelter)p).displayDimensions();
        return "";
    }

    private void refreshAnimalTable() {
        animalModel.setRowCount(0);
        for (Animal a : manager.getAnimals()) {
            animalModel.addRow(new Object[]{
                a.getClass().getSimpleName(), 
                a.getName(), 
                a.getAge(), 
                a.getGender(), 
                String.format("$%.2f", a.getPrice()),
                getSpecificDetails(a) 
            });
        }
    }

    private void refreshProductTable() {
        productModel.setRowCount(0);
        for (Product p : manager.getProducts()) {
            productModel.addRow(new Object[]{
                p.getClass().getSimpleName(), 
                p.getName(), 
                p.getManufacturer(), 
                String.format("$%.2f", p.getPrice()), 
                p.getQuantity(),
                getProductDetails(p) 
            });
        }
    }
    
    private void refreshCartTable() {
        cartModel.setRowCount(0);
        double rawSubtotal = 0.0;
        
        for (Animal a : manager.getCartAnimals()) {
            double price = a.getPrice();
            rawSubtotal += price;
            cartModel.addRow(new Object[]{
                a.getName(), "Animal (" + a.getClass().getSimpleName() + ")", 1, String.format("$%.2f", price), String.format("$%.2f", price)
            });
        }
        
        for (Product p : manager.getCartProducts()) {
            double price = p.getPrice();
            double subtotal = price * p.getQuantity();
            rawSubtotal += subtotal;
            cartModel.addRow(new Object[]{
                p.getName(), p.getManufacturer(), p.getQuantity(), String.format("$%.2f", price), String.format("$%.2f", subtotal)
            });
        }
        
        // totals and tax calulations
        double discountAmount = rawSubtotal * discountRate;
        double taxableAmount = rawSubtotal - discountAmount;
        double taxAmount = taxableAmount * TAX_RATE;
        double finalTotal = taxableAmount + taxAmount;
        
        subtotalLabel.setText(String.format("Subtotal: $%.2f", rawSubtotal));
        taxLabel.setText(String.format("Tax (8.25%%): $%.2f", taxAmount));
        totalLabel.setText(String.format("TOTAL: $%.2f", finalTotal));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PetStoreFrontEnd());
    }
}