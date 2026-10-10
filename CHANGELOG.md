[1.0.4]

**Changed**
* Migrated to Foundation; the Fireplace Cornice now uses Foundation's shared mimic block rendering.
* Sink and Washbasin reworked: right-click with an empty hand opens or closes the tap, which slowly fills the basin over three water levels with an animated water surface. Buckets and bottles fill or drain it level by level.
* Snow Globe now has its own snowflake particles that settle gently when idle and swirl into a proper storm when shaken hard.
* Bathtub water now pours from the faucet, steams (more so in snowy biomes) and drips after filling.
* Snow fog in snowy biomes reworked: a light mountain haze, thicker mist in the morning, at night, during snowfall and blizzards and in valleys, and denser fog in Groves.
* Winter Hat recipe now yields 1 instead of 4.
* Fabric now requires Cloth Config.

**Fixed**
* Garland corners not connecting properly and hitboxes not matching the rendered model.
* Garland blocking light and culling neighboring block faces.
* Tree Baubles placement sound not being heard by the placing player.

**Added**
* Star Topper on top of a decorated tree sends waves of sparkles through its Fairy Lights, Tree Baubles and Garlands at night, with a soft chime. A redstone signal lights it up during the day as well.
* Info overlay when looking at a Privy, showing its contents and grinding progress.
* NeoForge & Fabric config (Mod Menu on Fabric) for snow fog, Grove weather (drifting snow, avalanches, ambience), Bathtub (fill time, washing off effects, zombies turning into Drowned, steam), Star Topper and the info overlay.
* German (`de_de`) translations for all new entries.

***

[1.0.3]

**Fixed**
* Fixed the shear_alpine_sheep achievement (thanks to Tefnya).
* Mossy Alpine Gneiss Bricks dropping regular Alpine Gneiss Bricks instead of themselves.
* Stripped Arolla Pine Log dropping Stripped Arolla Pine Wood instead of itself.
* Mossy Layered Alpine Gneiss Bricks recipes (moss block & vine) using regular bricks instead of Layered Alpine Gneiss Bricks.
* Arolla Pine Log and Arolla Pine Wood not being strippable with an axe on Fabric.
* Arolla Pine Window Pane sharing its recipe with the Arolla Pine Trapdoor; it now crafts from the Arolla Pine Window.
* Arolla Pine Wall Cabinet and Arolla Pine Cooking Aisle sharing the same recipe.
* Icicles playing dirt sounds instead of glass/ice sounds when placed or broken.
* Winter Hat stacking up to 64 instead of 1.
* Breaking the top half of a Wardrobe deleting the armor stored inside instead of dropping it.
* Items absorbed by a Privy being lost permanently if the Privy was broken before they finished processing.
* Sofa and Privy occasionally ejecting the sitting player and blocking further use due to unrelated neighboring block updates.
* Sink and Washbasin spawning tinted blue break particles regardless of whether they were filled.
* Bathtub emitting a constant stream of splash/bubble particles for as long as it stayed filled, tanking framerate when in view.
* Storage containers (Wardrobe, Privy) not implementing `Clearable`, allowing item duplication via Create's Sable physics contraptions.
* Winter Hat rendering as a jumbled, broken texture on Fabric due to a missing armor renderer (NeoForge only had this wired up).

**Added**
* Japanese (`ja_jp`) translation (thanks to Anpan715).
* Italian (`it_it`) translation (thanks to Serenautilus).
* Russian (`ru_ru`) translation (thanks to Tefnya).

***

[1.0.2]

**Fixed**
* Placing a Fireplace Cornice causing a crash when using Indium / Sodium. 

***

[1.0.1]

**Fixed**
* Fixed a client-side crash when rendering the Fireplace Cornice 

**Added**
* NeoForge & Fabric config to remove the Fog Effect in SnowyBiomes.

***

[1.0.0]

**Initial Release**

***
