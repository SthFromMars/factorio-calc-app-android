/c
game.player.force.research_all_technologies();
local recipes_array = {};
local i=1;
for name, recipe in pairs(game.player.force.recipes) do
    recipes_array[i] = {
        name = recipe.name,
        enabled = recipe.enabled,
        category = recipe.category,
        ingredients = recipe.ingredients,
        products = recipe.products,
        hidden = recipe.hidden,
        energy = recipe.energy,
        productivity_bonus = recipe.productivity_bonus,
        order = recipe.order
    };
    i=i+1;
end
helpers.write_file(
    "recipes.json",
    helpers.table_to_json(recipes_array),
    false
);