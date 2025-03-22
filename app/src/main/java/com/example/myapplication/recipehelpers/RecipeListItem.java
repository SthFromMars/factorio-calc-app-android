package com.example.myapplication.recipehelpers;

import com.example.myapplication.machinehelpers.Machine;
import com.example.myapplication.machinehelpers.MachineUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RecipeListItem extends Recipe{
    private Machine machine;
    private double machineAmount;

    public RecipeListItem(
            String name,
            boolean enabled,
            String category,
            ArrayList<RecipeComponent> ingredients,
            ArrayList<Product> products,
            boolean hidden,
            double energy,
            double productivityBonus,
            String orderString
    ) {
        super(name, enabled, category, ingredients, products, hidden, energy, productivityBonus, orderString);
        machine = MachineUtils.getMachineForCategory(getCategory());
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
        calcMachineAmount();
    }

    public double getMachineAmount() {
        return machineAmount;
    }
    private void calcMachineAmount() {
        machineAmount = getEnergy() / machine.getCraftingSpeed();
    }

    public String getMachineFactoryString(){
        return machine.getName() + ": " + machineAmount;
    }
    public void calculateAmounts(HashMap<String, Double> productionAmounts){
        ArrayList<Double> ratios = new ArrayList<>();
        for(Product product: getProducts()){
            String productName = product.getName();
            ratios.add(
                    productionAmounts.containsKey(productName) ?
                            productionAmounts.get(productName)/product.getAmount() : 0
            );
        }
        // query for min, because productionAmounts of ingredients are negative
        double finalRatio = Collections.min(ratios)*-1;
        adjustAmounts(finalRatio);

        for(RecipeComponent product: getProducts())
            productionAmounts.merge(product.getName(), product.getAmount(), Double::sum);
        for(RecipeComponent ingredient: getIngredients())
            productionAmounts.merge(ingredient.getName(), (ingredient.getAmount()*-1), Double::sum);
        calcMachineAmount();
    }

    private void adjustAmounts(double ratio) {
        double productivityRatio = ratio / (1 + machine.getProductivity());
        for (RecipeComponent product: getProducts())
            product.adjustAmount(ratio);
        for (RecipeComponent ingredient: getIngredients())
            ingredient.adjustAmount(productivityRatio);
        setEnergy(productivityRatio);
    }
}
