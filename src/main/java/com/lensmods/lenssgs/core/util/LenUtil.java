package com.lensmods.lenssgs.core.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class LenUtil {

    public static int randBetween(int min, int max) {
        double rand = Math.random();
        int result = (int) Math.round(rand*(max-min) + min);
        return result;
    }

    //Item Utils
    public static boolean getPlayerItemStack(LivingEntity entity, ItemStack stack, boolean consumeItem, int amount) {

        if (!(entity instanceof Player player)) {return false;}
        player = (Player) entity;

        if (stack.isEmpty())
            return false;

        if (amount <= 0 || player.isCreative())
            return true;

        if (amount == 1) {
            ItemStack checkStack;

            if (isStackSame((checkStack = player.getItemInHand(InteractionHand.MAIN_HAND)), stack) && !checkStack.isEmpty()) {
                if (consumeItem)
                    checkStack.shrink(1);

                return true;
            }
            else if (isStackSame((checkStack = player.getItemInHand(InteractionHand.OFF_HAND)), stack) && !checkStack.isEmpty()) {
                if (consumeItem)
                    checkStack.shrink(1);

                return true;
            }
            else {
                for (ItemStack inventoryStack : player.getInventory().items) {
                    if (!inventoryStack.isEmpty() && isStackSame(stack, inventoryStack)) {
                        if (consumeItem)
                            inventoryStack.shrink(1);

                        return true;
                    }
                }

                for (ItemStack armorStack : player.getInventory().armor) {
                    if (!armorStack.isEmpty() && isStackSame(stack, armorStack)) {
                        if (consumeItem)
                            armorStack.shrink(1);

                        return true;
                    }
                }
            }

            return false;
        }
        else {
            ArrayList<ItemStack> matchedStacks = new ArrayList<ItemStack>();
            int totalStack = 0;
            ItemStack checkStack;

            if (isStackSame((checkStack = player.getItemInHand(InteractionHand.MAIN_HAND)), stack) && !checkStack.isEmpty()) {
                matchedStacks.add(checkStack);
                totalStack += checkStack.getCount();
            }

            if (totalStack < amount && isStackSame((checkStack = player.getItemInHand(InteractionHand.OFF_HAND)), stack) && !checkStack.isEmpty()) {
                matchedStacks.add(checkStack);
                totalStack += checkStack.getCount();
            }

            if (totalStack < amount) {
                for (ItemStack mainStack : player.getInventory().items) {
                    if (!mainStack.isEmpty() && isStackSame(stack, mainStack)) {
                        matchedStacks.add(mainStack);
                        totalStack += mainStack.getCount();

                        if (totalStack >= amount)
                            break;
                    }
                }
            }

            if (totalStack < amount) {
                for (ItemStack armorStack : player.getInventory().armor) {
                    if (!armorStack.isEmpty() && isStackSame(stack, armorStack)) {
                        matchedStacks.add(armorStack);
                        totalStack += armorStack.getCount();

                        if (totalStack >= amount)
                            break;
                    }
                }
            }

            if (totalStack < amount)
                return false;

            if (!consumeItem)
                return true;

            for (ItemStack matchedStack : matchedStacks) {
                int consumeAmount = Math.min(matchedStack.getCount(), Math.min(amount, totalStack));

                matchedStack.shrink(consumeAmount);
                totalStack -= consumeAmount;
            }

            return true;
        }
    }

    public static boolean findPlayerItem(LivingEntity entity, Item item, int amount) {

        if (!(entity instanceof Player player)) {return false;}
        player = (Player) entity;

        if (amount <= 0 || player.isCreative())
            return true;

        if (amount == 1) {
            @SuppressWarnings("unused")
            Item checkStack;

            if (isItemSame((checkStack = player.getItemInHand(InteractionHand.MAIN_HAND).getItem()), item)) {
                return true;
            }
            else if (isItemSame((checkStack = player.getItemInHand(InteractionHand.OFF_HAND).getItem()), item)) {
                return true;
            }
            else {
                for (ItemStack inventoryStack : player.getInventory().items) {
                    if (!inventoryStack.isEmpty() && isItemSame(item, inventoryStack.getItem())) {
                        return true;
                    }
                }

                for (ItemStack armorStack : player.getInventory().armor) {
                    if (!armorStack.isEmpty() && isItemSame(item, armorStack.getItem())) {
                        return true;
                    }
                }
            }

            return false;
        }
        else {
            ArrayList<Item> matchedStacks = new ArrayList<Item>();
            int totalStack = 0;
            Item checkStack;

            if (isItemSame((checkStack = player.getItemInHand(InteractionHand.MAIN_HAND).getItem()), item)) {
                matchedStacks.add(checkStack);
                totalStack ++;
            }

            if (totalStack < amount && isItemSame((checkStack = player.getItemInHand(InteractionHand.OFF_HAND).getItem()), item)) {
                matchedStacks.add(checkStack);
                totalStack ++;
            }

            if (totalStack < amount) {
                for (ItemStack mainStack : player.getInventory().items) {
                    if (!mainStack.isEmpty() && isItemSame(item, mainStack.getItem())) {
                        matchedStacks.add(mainStack.getItem());
                        totalStack += mainStack.getCount();

                        if (totalStack >= amount)
                            break;
                    }
                }
            }

            if (totalStack < amount) {
                for (ItemStack armorStack : player.getInventory().armor) {
                    if (!armorStack.isEmpty() && isItemSame(item, armorStack.getItem())) {
                        matchedStacks.add(armorStack.getItem());
                        totalStack += armorStack.getCount();

                        if (totalStack >= amount)
                            break;
                    }
                }
            }

            return totalStack >= amount;
        }
    }

    /*
    public static boolean lookInPouches(LivingEntity entity,ItemStack item,boolean consume,int amount) {
        if (!(entity instanceof Player player)) {return false;}
        player = (Player) entity;

        if (item.isEmpty())
            return false;

        if (amount <= 0 || player.isCreative())
            return true;
        int slotNum = 0;
        var stacks = CuriosAPI.getCuriosHelper().getCuriosHandler(player).orElse(null).getCurios().get("belt").getStacks();
        for (int i = 0 ; i < stacks.getSlots();i++) {
            ItemStack beltCurioItem = stacks.getStackInSlot(i);
            if (beltCurioItem.getItem() == LensItems.TACTICAL_POUCHES.get()) {
                LazyOptional<IItemHandler> beltCap = beltCurioItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                for (int y = 0; y < 9;y++) {
                    ItemStack bulletStack = beltCap.orElse(null).getStackInSlot(y);
                    if(isStackSame(bulletStack,item)) {
                        if (consume) bulletStack.shrink(amount);
                        return true;
                    }
                }
            }
        }
        for(ItemStack inventoryStack : player.getInventory().items) {
            if (inventoryStack.getItem() == LensItems.TACTICAL_POUCHES.get()) {
                LazyOptional<IItemHandler> beltCap = player.getInventory().getItem(slotNum).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                    for (int x = 0; x < 9; x++) {
                        ItemStack bulletStack = beltCap.orElse(null).getStackInSlot(x);
                        if (isStackSame(bulletStack,item) && consume) {
                            if (consume) bulletStack.shrink(amount);
                            return true;
                            }
                        }
                    }
                slotNum++;
                }

        return false;
        }

     */
    public static int findItemStackContainer(Player player, ItemStack find)
    {
        int count = 0;
        for(ItemStack stack : player.getInventory().items)
        {
            if(!stack.isEmpty() && isStackSame(stack, find))
            {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static List<EntityHitResult> rayTraceEntityList(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        List<EntityHitResult> entityRayTracelist = new ArrayList<>();

        for(Entity entity1 : worldIn.getEntities(projectile, boundingBox, filter)) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(0.3F);
            Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
            if (optional.isPresent()) {
                entityRayTracelist.add(new EntityHitResult(entity1));
            }
        }

        return entityRayTracelist;
    }

    public static boolean consumeItemStackContainer(Player player, ItemStack find)
    {
        int amount = find.getCount();
        for(int i = 0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = player.getInventory().getItem(i);
            if(!stack.isEmpty() && isStackSame(stack, find))
            {
                if(amount - stack.getCount() < 0)
                {
                    stack.shrink(amount);
                    return true;
                }
                else
                {
                    amount -= stack.getCount();
                    player.getInventory().items.set(i, ItemStack.EMPTY);
                    if(amount == 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isItemSame(Item a,Item b) {
        return a == b;
    }

    public static boolean isStackSame(ItemStack source, ItemStack target)
    {
        if(source.getItem() != target.getItem())
        {
            return false;
        }
        else if(source.getDamageValue() != target.getDamageValue())
        {
            return false;
        }
        else if(source.getTags() == null && target.getTags() != null)
        {
            return false;
        }
        else
        {
            return (source.getTags() == null || source.getTags().equals(target.getTags()));
        }
    }
    /*
    private static final Method updateRedstoneOutputMethod = ObfuscationReflectionHelper.findMethod(TargetBlock.class, "m_5581_", LevelAccessor.class, BlockState.class, BlockHitResult.class, Projectile.class);

    public static int updateTargetBlock(TargetBlock block, LevelAccessor accessor, BlockState state, BlockHitResult result, Entity entity)
    {
        try
        {
            return (int) updateRedstoneOutputMethod.invoke(block, accessor, state, result, entity);
        }
        catch(IllegalAccessException | InvocationTargetException ignored)
        {
            return 0;
        }
    }
     */
    public static float RandBetween(Random randy,float min,float max)
    {
        return randy.nextFloat(min,max);
    }
}
