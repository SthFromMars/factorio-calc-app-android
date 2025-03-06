/c
game.player.force.research_all_technologies();
local recipes_table = {};
for name, recipe in pairs(game.player.force.recipes) do
    recipes_table[name] = {
        name = recipe.name,
        enabled = recipe.enabled,
        category = recipe.category,
        ingredients = recipe.ingredients,
        products = recipe.products,
        hidden = recipe.hidden,
        energy = recipe.energy,
        productivity_bonus = recipe.productivity_bonus
    };
end
helpers.write_file(
    "recipes.json",
    helpers.table_to_json(recipes_table),
    false
);