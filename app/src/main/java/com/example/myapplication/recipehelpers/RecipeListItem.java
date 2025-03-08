package com.example.myapplication.recipehelpers;

import com.example.myapplication.machinehelpers.Machine;
import com.example.myapplication.machinehelpers.MachineUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeListItem extends Recipe{
    private Machine machine = null;
    private float machineAmount;
    public RecipeListItem(
            String name,
            boolean enabled,
            String category,
            ArrayList<RecipeComponent> ingredients,
            ArrayList<Product> products,
            boolean hidden,
            float energy,
            float productivityBonus,
            String orderString
    ) {
        super(name, enabled, category, ingredients, products, hidden, energy, productivityBonus, orderString);
    }

    public Machine getMachine() {
        return machine;
    }

    public float getMachineAmount() {
        return machineAmount;
    }

    public String getMachineFactoryString(){
        return machine.getName() + ": " + machineAmount;
    }

    @Override
    public void calculateAmounts(HashMap<String, Float> productionAmounts){
        super.calculateAmounts(productionAmounts);
        if(machine == null)
            machine = MachineUtils.getMachineForCategory(getCategory());
        machineAmount = getEnergy() / machine.getCraftingSpeed();
    }
}
