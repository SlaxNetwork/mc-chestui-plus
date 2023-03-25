/**
 * @author hazae41
 * This GUI library is used from https://github.com/hazae41/mc-chestui
 * and has been slightly recoded to better suite what I needed from it.
 * Thanks for originally creating it!
 */
package me.tech.mcchestui

import com.destroystokyo.paper.profile.PlayerProfile
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

fun item(type: Material = Material.AIR, builder: GUIItem.() -> Unit = {}) =
	GUIItem(type).apply(builder)

class GUIItem(
	type: Material
) {
	val stack = ItemStack(type, 1)

	val itemMeta get() = stack.itemMeta

	fun stack(builder: ItemStack.() -> Unit) =
		stack.apply(builder)

	fun meta(builder: ItemMeta.() -> Unit) =
		itemMeta?.apply(builder)?.also { stack.itemMeta = it }

	var name: Component?
		get() = itemMeta.displayName()
		set(value) {
			stack.itemMeta.apply { displayName(value) }.also { stack.itemMeta = it }
		}

	var lore: MutableList<Component>?
		get() = itemMeta.lore()
		set(value) {
			stack.itemMeta.apply { lore(value) }.also { stack.itemMeta = it }
		}

	var amount: Int
		get() = stack.amount
		set(value) { stack.amount = value }

	var skullOwner: OfflinePlayer?
		get() = (itemMeta as? SkullMeta)?.owningPlayer
		set(value) {
			(itemMeta as? SkullMeta)
				?.apply { owningPlayer = value }
				?.also { stack.itemMeta = it }
		}

	var playerProfile: PlayerProfile?
		get() = (itemMeta as? SkullMeta)?.playerProfile
		set(value) {
			(itemMeta as? SkullMeta)
				?.apply { playerProfile = value }
				?.also { stack.itemMeta = it }
		}

	var glowing: Boolean = false
		set(value) {
			// Add glow.
			if(value) {
				stack.itemMeta.apply {
					addEnchant(Enchantment.ARROW_INFINITE, 0, true)
					addItemFlags(ItemFlag.HIDE_ENCHANTS)
				}.also { stack.itemMeta = it }
			// Remove glow.
			} else {
				stack.itemMeta.apply {
					removeEnchant(Enchantment.ARROW_INFINITE)
					removeItemFlags(ItemFlag.HIDE_ENCHANTS)
				}.also { stack.itemMeta = it }
			}

			field = value
		}

	var customModelData: Int
		get() = itemMeta.customModelData
		set(value) {
			stack.itemMeta.apply {
				setCustomModelData(value)
			}.also { stack.itemMeta = it }
		}
}