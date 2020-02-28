/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.item.storage;

import com.github.klikli_dev.occultism.Occultism;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class StableWormholeBlockItem extends BlockItem {
    //region Initialization
    public StableWormholeBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.addPropertyOverride(new ResourceLocation(Occultism.MODID, "linked"),
                (stack, world, entity) -> stack.getOrCreateTag().getCompound("BlockEntityTag")
                                                  .contains("linkedStorageControllerPosition") ? 1.0f : 0.0f);
    }
    //endregion Initialization

//region Overrides
    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getOrCreateTag().getCompound("BlockEntityTag")
                       .contains("linkedStorageControllerPosition") ? Rarity.RARE : Rarity.COMMON;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        if (!world.isRemote) {
            if (player.isShiftKeyDown()) {
                TileEntity tileEntity = world.getTileEntity(pos);
                //TODO: once storage controller is set up, enable this to allow linking
                //                if (tileEntity instanceof IStorageController) {
                //                    //if this is a storage controller, write the position into the block entity tag that will be used to spawn the tile entity.
                //                    CompoundNBT itemTag =
                //                            stack.getTag() != null ? stack.getTag() : new CompoundNBT();
                //
                //                    stack.getOrCreateChildTag("BlockEntityTag").put("linkedStorageControllerPosition", GlobalBlockPos.fromTileEntity(tileEntity).writeToNBT(new CompoundNBT()));
                //                    player.sendStatusMessage(
                //                            new TranslationTextComponent(TRANSLATION_KEY_BASE + ".message.set_storage_controller"),
                //                            true);
                //                    return ActionResultType.SUCCESS;
                //                }
            }
        }
        return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //        if (stack.getOrCreateTag().getCompound("BlockEntityTag")
        //                    .contains("linkedStorageControllerPosition")) {
        //            GlobalBlockPos globalPos = GlobalBlockPos.fromNbt(stack.getChildTag("BlockEntityTag")
        //                                                                      .getCompound(
        //                                                                              "linkedStorageControllerPosition"));
        //            tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip.linked",
        //                    TextFormatting.GOLD.toString() + TextFormatting.BOLD.toString() + globalPos.getPos().toString() +
        //                    TextFormatting.RESET.toString()));
        //        }
        //        else {
        //            tooltip.add(I18n.format(TRANSLATION_KEY_BASE + ".tooltip.unlinked"));
        //        }
        tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip.unlinked"));
    }
//endregion Overrides
}
