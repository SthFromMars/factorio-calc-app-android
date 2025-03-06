/c
game.player.force.research_all_technologies();
local machines_table = {};
for name, machine in pairs(prototypes.get_entity_filtered{{filter="crafting-machine"}}) do
    machines_table[name] = {
        name = machine.name,
        crafting_speed = machine.get_crafting_speed(),
        crafting_categories = machine.crafting_categories
    };
end
helpers.write_file(
    "machines.json",
    helpers.table_to_json(machines_table),
    false
);
