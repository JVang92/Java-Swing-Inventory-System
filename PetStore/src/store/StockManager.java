package store;

import java.util.ArrayList;
import java.util.List;

public class StockManager {
    // STORAGE LISTS 
    private List<Animal> animals;
    private List<Product> products; 
    
    // CART LISTS 
    private List<Animal> cartAnimals;
    private List<Product> cartProducts;

    public StockManager() {
        this.animals = new ArrayList<>();
        this.products = new ArrayList<>();
        this.cartAnimals = new ArrayList<>();
        this.cartProducts = new ArrayList<>();
    }
    
  
    // INVENTORY MANAGEMENT

    
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }
    
    public void addProduct(Product newProduct) {
        boolean found = false;
        for (Product p : this.products) {
            if (p.getName().equalsIgnoreCase(newProduct.getName())) {
                p.adjustQuantity(1);
                found = true;
                break; 
            }
        }
        if (!found) {
            newProduct.setQuantity(1);
            this.products.add(newProduct);
        }
    }
    
    public void removeAnimal(String name) {
        Animal toRemove = null;
        for(Animal a : this.animals) {
            if(a.getName().equalsIgnoreCase(name)) {
                toRemove = a;
                break; 
            }
        }
        if(toRemove != null) this.animals.remove(toRemove);
    }
    
    public void removeProduct(String name) {
        Product toRemove = null;
        for(Product p : this.products) {
            if(p.getName().equalsIgnoreCase(name)) {
                p.adjustQuantity(-1);
                if(p.getQuantity() <= 0) toRemove = p;
                break; 
            }
        }
        if(toRemove != null) this.products.remove(toRemove);
    }

    // CART MANAGEMENT


    public void addToCartAnimal(String name) {
        Animal toMove = null;
        for(Animal a : this.animals) {
            if(a.getName().equalsIgnoreCase(name)) {
                toMove = a;
                break; 
            }
        }
        
        if(toMove != null) {
            this.animals.remove(toMove); // Remove from Shelf
            this.cartAnimals.add(toMove); // Add to Cart
        }
    }
    
    public void addToCartProduct(String name) {
        Product stockItem = null;
        
        // 1. Find in Stock
        for (Product p : this.products) {
            if (p.getName().equalsIgnoreCase(name)) {
                stockItem = p;
                break;
            }
        }
        
        if (stockItem != null && stockItem.getQuantity() > 0) {
            stockItem.adjustQuantity(-1);
            
            if(stockItem.getQuantity() == 0) {
                this.products.remove(stockItem);
            }

            // 2. Add to Cart
            boolean inCart = false;
            for(Product cp : this.cartProducts) {
                if(cp.getName().equalsIgnoreCase(name)) {
                    cp.adjustQuantity(1);
                    inCart = true;
                    break;
                }
            }
            
            if(!inCart) {
                Product cartItem = new Product(stockItem.getName(), stockItem.getManufacturer(), stockItem.getPrice());
                cartItem.setQuantity(1);
                this.cartProducts.add(cartItem);
            }
        }
    }

    public void clearCart() {
        this.cartAnimals.clear();
        this.cartProducts.clear();
    }
    
    public boolean isCartEmpty() {
        return cartAnimals.isEmpty() && cartProducts.isEmpty();
    }
    
 // getters
    public List<Animal> getAnimals() { return animals; }
    public List<Product> getProducts() { return products; }
    public List<Animal> getCartAnimals() { return cartAnimals; }
    public List<Product> getCartProducts() { return cartProducts; }
}