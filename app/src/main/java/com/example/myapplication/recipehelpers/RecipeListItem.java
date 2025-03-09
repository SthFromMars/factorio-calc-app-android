package com.example.myapplication.recipehelpers;

import com.example.myapplication.machinehelpers.Machine;
import com.example.myapplication.machinehelpers.MachineUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeListItem extends Recipe{
    private Machine machine;
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
        machine = MachineUtils.getMachineForCategory(getCategory());
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
        calcMachineAmount();
    }

    public float getMachineAmount() {
        return machineAmount;
    }
    private void calcMachineAmount() {
        machineAmount = getEnergy() / machine.getCraftingSpeed();
    }

    public String getMachineFactoryString(){
        return machine.getName() + ": " + machineAmount;
    }
// TODO: implement building productivity
    @Override
    public void calculateAmounts(HashMap<String, Float> productionAmounts){
        super.calculateAmounts(productionAmounts);
        calcMachineAmount();
    }
}
