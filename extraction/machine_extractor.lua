/c
game.player.force.research_all_technologies();
local machines_table = {};
local i = 1;
for name, machine in pairs(prototypes.get_entity_filtered{{filter="crafting-machine"}}) do
    machines_table[i] = {
        name = machine.name,
        crafting_speed = machine.get_crafting_speed(),
        order = machine.order
    };
    local j=1;
    machines_table[i].crafting_categories = {};
    for category, x in pairs(machine.crafting_categories) do
        machines_table[i].crafting_categories[j] = category;
        j=j+1;
    end
    machines_table[i].productivity = prototypes.entity[machine.name].effect_receiver.base_effect.productivity
    i=i+1;
end
helpers.write_file(
    "machines.json",
    helpers.table_to_json(machines_table),
    false
);
