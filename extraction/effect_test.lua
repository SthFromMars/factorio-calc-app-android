/c
helpers.write_file(
    "effect_test.json",
    helpers.table_to_json(
        prototypes.entity["electromagnetic-plant"].effect_receiver
    ),
    false
)