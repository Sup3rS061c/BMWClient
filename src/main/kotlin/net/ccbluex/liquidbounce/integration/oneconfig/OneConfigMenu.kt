/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2015 - 2025 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */
package net.ccbluex.liquidbounce.integration.oneconfig

import net.ccbluex.liquidbounce.config.types.BindValue
import net.ccbluex.liquidbounce.config.types.ChooseListValue
import net.ccbluex.liquidbounce.config.types.MultiChooseListValue
import net.ccbluex.liquidbounce.config.types.NamedChoice
import net.ccbluex.liquidbounce.config.types.RangedValue
import net.ccbluex.liquidbounce.config.types.Value
import net.ccbluex.liquidbounce.config.types.ValueType
import net.ccbluex.liquidbounce.config.types.nesting.Choice
import net.ccbluex.liquidbounce.config.types.nesting.ChoiceConfigurable
import net.ccbluex.liquidbounce.config.types.nesting.Configurable
import net.ccbluex.liquidbounce.config.types.nesting.ToggleableConfigurable
import net.ccbluex.liquidbounce.features.module.ClientModule
import net.ccbluex.liquidbounce.features.module.ModuleManager
import net.ccbluex.liquidbounce.render.engine.type.Color4b
import net.ccbluex.liquidbounce.utils.client.logger
import net.ccbluex.liquidbounce.utils.client.mc
import net.ccbluex.liquidbounce.utils.input.inputByName
import net.ccbluex.liquidbounce.utils.input.reduceInputName
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.InputUtil
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.ConfigManager
import org.polyfrost.oneconfig.api.config.v1.Properties
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.oneconfig.api.ui.v1.OneConfigUI
import org.polyfrost.oneconfig.internal.ui.navigation.graph.ModConfigRoute

/**
 * Bridges the OneConfig menu GUI into BMWClient.
 *
 * Builds a OneConfig [Tree] from every registered [ClientModule] and its options, registers it with
 * OneConfig's [ConfigManager] and opens the OneConfig UI when the ClickGUI is activated.
 *
 * Every module is rendered as its own subcategory holding a module switch plus one row per option so
 * that no option can disappear or become unreachable. All options are bound live to the underlying
 * [Value] instances, so changes made in the menu immediately apply to the client.
 */
object OneConfigMenu {

    private const val TREE_ID = "southbmw"
    private const val TREE_TITLE = "BMWClient"
    private const val TREE_DESCRIPTION = "BMWClient modules and settings"

    @Volatile
    private var registered = false

    /**
     * Builds the option tree from all registered modules and registers it with OneConfig.
     * Safe to call multiple times.
     */
    fun register() {
        if (registered) {
            return
        }
        registered = true

        runCatching {
            val tree = Tree.tree(TREE_ID)
            tree.setTitle(TREE_TITLE)
            tree.description = TREE_DESCRIPTION

            ModuleManager.sortedWith(
                compareBy<ClientModule> { it.category.choiceName }.thenBy { it.name }
            ).forEach { module ->
                addModule(tree, module)
            }

            ConfigManager.active().register(tree)
        }.onFailure {
            logger.error("Failed to register the OneConfig menu", it)
        }
    }

    /**
     * Opens the OneConfig UI directly on the BMWClient settings page.
     */
    fun open() {
        runCatching {
            val screen = OneConfigUI.createScreen(ModConfigRoute(TREE_ID))
            if (screen is Screen) {
                mc.setScreen(screen)
            }
        }.onFailure {
            logger.error("Failed to open the OneConfig menu", it)
        }
    }

    // ------------------------------------------------------------------
    // Module tree building
    // ------------------------------------------------------------------

    private fun addModule(tree: Tree, module: ClientModule) {
        val category = module.category.choiceName
        val subcategory = module.name
        val moduleId = sanitizeId(module.name)

        tree.put(
            prop(
                id = "$moduleId.enabled",
                title = module.name,
                description = module.enabledValue.description.get(),
                type = Boolean::class.java,
                getter = { module.enabled },
                setter = { state -> module.enabled = state }
            ).applyVisualizer(Visualizer.SwitchVisualizer(), category, subcategory)
        )

        for (value in module.inner) {
            if (value === module.enabledValue) {
                continue
            }
            addValue(tree, value, moduleId, category, subcategory)
        }
    }

    private fun addValue(
        tree: Tree,
        value: Value<*>,
        idPrefix: String,
        category: String,
        subcategory: String,
        titlePrefix: String = ""
    ) {
        if (value.notAnOption) {
            return
        }

        when (value) {
            is ToggleableConfigurable -> addToggleable(tree, value, idPrefix, category, subcategory, titlePrefix)
            is ChoiceConfigurable<*> -> addChoice(tree, value, idPrefix, category, subcategory, titlePrefix)
            is Configurable -> {
                val childPrefix = "$idPrefix.${sanitizeId(value.name)}"
                for (child in value.inner) {
                    addValue(tree, child, childPrefix, category, subcategory, titlePrefix)
                }
            }
            else -> addLeaf(tree, value, idPrefix, category, subcategory, titlePrefix)
        }
    }

    private fun addToggleable(
        tree: Tree,
        config: ToggleableConfigurable,
        idPrefix: String,
        category: String,
        subcategory: String,
        titlePrefix: String
    ) {
        val configId = sanitizeId(config.name)
        val title = titled(titlePrefix, config.name)

        tree.put(
            prop(
                id = "$idPrefix.$configId.enabled",
                title = title,
                description = config.enabledValue.description.get(),
                type = Boolean::class.java,
                getter = { config.enabled },
                setter = { state -> config.enabled = state }
            ).applyVisualizer(Visualizer.SwitchVisualizer(), category, subcategory)
        )

        for (child in config.inner) {
            if (child === config.enabledValue) {
                continue
            }
            addValue(tree, child, "$idPrefix.$configId", category, subcategory, titlePrefix)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun addChoice(
        tree: Tree,
        choice: ChoiceConfigurable<*>,
        idPrefix: String,
        category: String,
        subcategory: String,
        titlePrefix: String
    ) {
        val choiceId = sanitizeId(choice.name)
        val title = titled(titlePrefix, choice.name)
        val modes = choice.choices as List<Choice>
        val options = modes.map { it.choiceName }

        tree.put(
            prop(
                id = "$idPrefix.$choiceId",
                title = title,
                description = choice.description.get(),
                type = Int::class.java,
                getter = {
                    val active = choice.activeChoice
                    modes.indexOfFirst { it === active }.coerceAtLeast(0)
                },
                setter = { index ->
                    if (index in options.indices) {
                        runCatching { choice.setByString(options[index]) }
                    }
                }
            ).applyVisualizer(Visualizer.DropdownVisualizer(), category, subcategory)
                .withMetadata("options", options)
        )

        // Flatten the options of every mode into the module so that switching modes never removes
        // settings from the menu. Each mode's options are prefixed with the mode name for clarity.
        for (mode in modes) {
            val modePrefix = "$idPrefix.$choiceId.${sanitizeId(mode.name)}"
            val modeTitlePrefix = titled(titlePrefix, mode.choiceName)
            for (child in mode.inner) {
                if (child.notAnOption) {
                    continue
                }
                addValue(tree, child, modePrefix, category, subcategory, modeTitlePrefix)
            }
        }
    }

    private fun addLeaf(
        tree: Tree,
        value: Value<*>,
        idPrefix: String,
        category: String,
        subcategory: String,
        titlePrefix: String
    ) {
        val id = "$idPrefix.${sanitizeId(value.name)}"
        val title = titled(titlePrefix, value.name)
        val description = value.description.get()

        when (value.valueType) {
            ValueType.BOOLEAN -> {
                val v = value as Value<Boolean>
                tree.put(
                    prop(id, title, description, Boolean::class.java, { v.get() }, { v.set(it) })
                        .applyVisualizer(Visualizer.SwitchVisualizer(), category, subcategory)
                )
            }

            ValueType.FLOAT -> {
                val v = value as RangedValue<Float>
                val range = v.range as ClosedFloatingPointRange<Float>
                tree.put(
                    prop(id, title, description, Float::class.java, { v.get() }, { v.set(it) })
                        .applyVisualizer(Visualizer.SliderVisualizer(), category, subcategory)
                        .withMetadata("min", range.start)
                        .withMetadata("max", range.endInclusive)
                        .withMetadata("step", 0.0f)
                        .withMetadata("unit", v.suffix)
                )
            }

            ValueType.INT -> {
                val v = value as RangedValue<Int>
                val range = v.range as IntRange
                tree.put(
                    prop(id, title, description, Int::class.java, { v.get() }, { v.set(it) })
                        .applyVisualizer(Visualizer.SliderVisualizer(), category, subcategory)
                        .withMetadata("min", range.first.toFloat())
                        .withMetadata("max", range.last.toFloat())
                        .withMetadata("step", 1.0f)
                        .withMetadata("unit", v.suffix)
                )
            }

            ValueType.TEXT -> {
                val v = value as Value<String>
                tree.put(
                    prop(id, title, description, String::class.java, { v.get() }, { v.set(it) })
                        .applyVisualizer(Visualizer.TextVisualizer(), category, subcategory)
                )
            }

            ValueType.COLOR -> {
                val v = value as Value<Color4b>
                tree.put(
                    prop(id, title, description, PolyColor::class.java, { v.get().toPolyColor() }, { v.set(it.toColor4b()) })
                        .applyVisualizer(Visualizer.ColorVisualizer(), category, subcategory)
                )
            }

            ValueType.CHOOSE -> {
                val v = value as ChooseListValue<NamedChoice>
                val options = v.choices.map { it.choiceName }
                tree.put(
                    prop(
                        id, title, description, Int::class.java,
                        getter = {
                            options.indexOfFirst { it == v.get().choiceName }.coerceAtLeast(0)
                        },
                        setter = { index ->
                            if (index in options.indices) {
                                runCatching { v.setByString(options[index]) }
                            }
                        }
                    ).applyVisualizer(Visualizer.DropdownVisualizer(), category, subcategory)
                        .withMetadata("options", options)
                )
            }

            ValueType.MULTI_CHOOSE -> {
                val v = value as MultiChooseListValue<NamedChoice>
                val options = v.choices.toList()
                tree.put(
                    prop(
                        id, title, description, BooleanArray::class.java,
                        getter = {
                            val current = v.get()
                            BooleanArray(options.size) { index ->
                                current.any { it.choiceName == options[index].choiceName }
                            }
                        },
                        setter = { flags ->
                            options.forEachIndexed { index, option ->
                                val selected = v.get().any { it.choiceName == option.choiceName }
                                if (flags[index] != selected) {
                                    runCatching { v.toggle(option) }
                                }
                            }
                        }
                    ).applyVisualizer(Visualizer.MultiSelectDropdownVisualizer(), category, subcategory)
                        .withMetadata("options", options.map { it.choiceName }.toTypedArray())
                )
            }

            ValueType.KEY -> {
                val v = value as Value<InputUtil.Key>
                tree.put(
                    prop(
                        id, title, description, String::class.java,
                        getter = { reduceInputName(v.get().translationKey) },
                        setter = { name -> runCatching { v.set(inputByName(name)) } }
                    ).applyVisualizer(Visualizer.TextVisualizer(), category, subcategory)
                )
            }

            ValueType.BIND -> {
                val v = value as BindValue
                tree.put(
                    prop(
                        id, title, description, String::class.java,
                        getter = { reduceInputName(v.get().boundKey.translationKey) },
                        setter = { name -> runCatching { v.setByString(name) } }
                    ).applyVisualizer(Visualizer.TextVisualizer(), category, subcategory)
                )
            }

            else -> {
                // Fallback for everything else (registries, vectors, lists, curves, ...):
                // always visible as text, editable when the value supports string deserialization.
                tree.put(
                    prop(
                        id, title, description, String::class.java,
                        getter = { displayString(value) },
                        setter = { name -> runCatching { value.setByString(name) } }
                    ).applyVisualizer(Visualizer.TextVisualizer(), category, subcategory)
                )
            }
        }
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private fun displayString(value: Value<*>): String = when (value.valueType) {
        ValueType.BLOCK -> (value.get() as? net.minecraft.block.Block)
            ?.let { Registries.BLOCK.getId(it)?.toString() } ?: "?"

        ValueType.ITEM -> (value.get() as? net.minecraft.item.Item)
            ?.let { Registries.ITEM.getId(it)?.toString() } ?: "?"

        ValueType.SOUND -> (value.get() as? net.minecraft.sound.SoundEvent)?.id?.toString() ?: "?"

        ValueType.STATUS_EFFECT -> (value.get() as? net.minecraft.entity.effect.StatusEffect)
            ?.let { Registries.STATUS_EFFECT.getId(it)?.toString() } ?: "?"

        ValueType.ENTITY_TYPE -> (value.get() as? net.minecraft.entity.EntityType<*>)
            ?.let { Registries.ENTITY_TYPE.getId(it)?.toString() } ?: "?"

        else -> runCatching { value.getValue().toString() }.getOrDefault("")
    }

    private fun sanitizeId(name: String): String = name.lowercase()
        .replace(Regex("[^a-z0-9_.-]"), "_")

    private fun titled(titlePrefix: String, name: String): String =
        if (titlePrefix.isEmpty()) name else "$titlePrefix $name"

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> prop(
        id: String,
        title: String,
        description: String,
        type: Class<T>,
        getter: () -> T,
        setter: (T) -> Unit
    ): Property<T> = Properties.functional(
        java.util.function.Supplier { getter() },
        java.util.function.Consumer { setter(it) },
        id,
        title,
        description,
        type
    )

    private fun <T : Any> Property<T>.applyVisualizer(
        visualizer: Visualizer,
        category: String,
        subcategory: String
    ): Property<T> = withMetadata("visualizer", visualizer)
        .withMetadata("category", category)
        .withMetadata("subcategory", subcategory)

    private fun <T : Any> Property<T>.withMetadata(key: String, value: Any?): Property<T> {
        if (value != null) {
            addMetadata(key, value)
        }
        return this
    }

    private fun Color4b.toPolyColor(): PolyColor = PolyColor((a shl 24) or (r shl 16) or (g shl 8) or b)

    private fun PolyColor.toColor4b(): Color4b = Color4b(red, green, blue, alpha)
}
