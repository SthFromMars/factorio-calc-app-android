package com.example.myapplication.recipehelpers;

import com.example.myapplication.machinehelpers.Machine;
import com.example.myapplication.machinehelpers.MachineUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            float productivityBonus
    ) {
        super(name, enabled, category, ingredients, products, hidden, energy, productivityBonus);
    }

    public Machine getMachine() {
        return machine;
    }

    public float getMachineAmount() {
        return machineAmount;
    }

    @Override
    public void calculateAmounts(HashMap<String, Float> productionAmounts){
        super.calculateAmounts(productionAmounts);
        if(machine == null)
            machine = MachineUtils.getMachineForCategory(getCategory());
        machineAmount = getEnergy() / machine.getCraftingSpeed();
    }
}
