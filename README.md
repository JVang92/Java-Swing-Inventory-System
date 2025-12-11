# 🐾 Pet Store Management System

A Java Swing application designed to manage the inventory and sales of a pet store. This system allows for tracking live animals and products, managing stock quantities, and processing customer checkouts with tax and discount logic.

##  Features

### 1. Inventory Management
* **Two Categories:** Separate tabs for **Animals**  and **Products**.
* **Polymorphic Design:** Handles specific attributes for different classes:
    *  **Dogs:** Tracks Breed & Vaccination status.
    *  **Birds:** Tracks Color & Size (Small, Medium, Large).
    *  **Fish:** Tracks Pattern & Type (Goldfish vs. Other).
    * **Shelters:** Tracks Dimensions (Width/Height).
* **Dynamic Input Forms:** The "Add Item" form changes dynamically based on the dropdown selection (e.g., the "Vaccinated" checkbox only appears for Dogs).

### 2. Search & Filtering
* **Real-time Search:** Filter lists instantly by **Name** using the search bar on both inventory tabs.
* **Sorting:** Click column headers to sort by Price, Name, or Quantity.

### 3. Shopping Cart & Checkout
* **Add/Remove:** Add items to a cart (decrements stock) or remove them (returns to stock).
* **Financial Calculations:**
    * Calculates Subtotal.
    * Applies percentage-based **Discounts**.
    * Calculates **Sales Tax** (Set to Texas rate: 8.25%).
    * Generates a final Grand Total.


##  Project Structure

The project uses a clean inheritance hierarchy to manage data:

```text
src/
└── store/
    ├── PetStoreFrontEnd.java  // Main UI (JFrame, Tabs, Listeners)
    ├── StockManager.java      // Backend Logic (Lists, Cart Movement)
    ├── Animal.java            // Base Class
    │   ├── Dog.java
    │   ├── Cat.java
    │   ├── Bird.java
    │   ├── Fish.java
    │   └── Reptile.java
    └── Product.java           // Base Class
        ├── Food.java
        ├── Toy.java
        ├── Cloth.java
        └── Shelter.java
